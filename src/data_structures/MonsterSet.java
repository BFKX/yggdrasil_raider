package data_structures;

import characters.BasicMonster;
import characters.MainCharacter;
import characters.Monster;
import javafx.scene.canvas.GraphicsContext;
import mapping.Room;
import tools.Coordinate;
import tools.Hitbox;

import java.util.ArrayList;
import java.util.Iterator;

public class MonsterSet {
    private ArrayList<Monster> monsters= new ArrayList<>();

    public MonsterSet(int n, MainCharacter mainCharacter, int[][] map) {
        for (int i = 0; i < n; i++) {
            monsters.add(new BasicMonster(new Coordinate( map.length * Math.random(),
                     map[0].length * Math.random()), mainCharacter.getPosition(), map));
        }
    }

    public void setStartMonster(Room room, Coordinate mainCposition ){
        Iterator<Monster> it = monsters.iterator();
        Monster curr;
        while (it.hasNext()) {
            curr = it.next();
            curr.startPosition(room,mainCposition);
        }
    }

    public void setMainCharacterPosition(Coordinate mainCharacterPosition){
        Iterator<Monster> it = monsters.iterator();
        Monster curr;
        while (it.hasNext()) {
            curr = it.next();
            curr.setMainCharactersPosition(mainCharacterPosition);
        }
    }

    public void update(MainCharacter mainCharacter) {
        Iterator<Monster> it = monsters.iterator();
        Monster curr;
        while (it.hasNext()) {
            curr = it.next();
            if (curr.getHealthPoint() <= 0) {
                monsters.remove(curr);
                mainCharacter.addHealth(5);
            } else {
                curr.update();
            }
        }
    }

    public void display(GraphicsContext gc, MainCharacter mainCharacter){
        Iterator<Monster> it = monsters.iterator();
        Monster curr;
        while (it.hasNext()) {
            curr = it.next();
            curr.display(gc, mainCharacter.getPosition());
        }
    }

    public boolean hit(Hitbox hitbox) {
        Iterator<Monster> it = monsters.iterator();
        Monster curr;
        while (it.hasNext()) {
            curr = it.next();
            if ((curr.collideHitbox(hitbox))) {
                return true;
            }
        }
        return false;
    }

    public void isHit(Hitbox hitbox,Coordinate speed ){
        Iterator<Monster> it = monsters.iterator();
        Monster curr;
        while (it.hasNext()) {
            curr = it.next();
            if (curr.collideHitbox(hitbox)) {
                curr.isAttacked( speed);
            }
        }
    }
}
