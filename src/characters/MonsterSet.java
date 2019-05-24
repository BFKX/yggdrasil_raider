package characters;

import javafx.scene.canvas.GraphicsContext;
import mapping.Room;
import tools.Coordinate;
import tools.Hitbox;
import mapping.Map;

import java.util.ArrayList;
import java.util.Iterator;

public class MonsterSet {
    private ArrayList<Monster> monsters= new ArrayList<>();

    public MonsterSet(int n, MainCharacter mainCharacter, int[][] map) {
        for (int i = 0; i < n; i++) {
            monsters.add(new BasicMonster(new Coordinate( map.length*Math.random(),
                     map[0] .length * Math.random()), mainCharacter.getPosition(), map));
        }
    }

    public void setStartMonster(Room room ){
        Iterator<Monster> it = monsters.iterator();
        Monster curr;
        while (it.hasNext()) {
            curr = it.next();
            curr.startposition(room);
        }
    }

    public void setMainCharacterPosition( Coordinate position ){
        Iterator<Monster> it = monsters.iterator();
        Monster curr;
        while (it.hasNext()) {
            curr = it.next();
            curr.setMainCharactersPosition(position);
        }
    }
    public void setMap(Map map){
        Iterator<Monster> it = monsters.iterator();
        Monster curr;
        while (it.hasNext()) {
            curr = it.next();
            curr.setMap(map);
        }
    }
    public void update(GraphicsContext gc) {
        Iterator<Monster> it = monsters.iterator();
        Monster curr;
        while (it.hasNext()) {
            curr = it.next();
            if (curr.getHealthPoint() <= 0) {
                monsters.remove(curr);
            } else {
                curr.update(gc);
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

    public int hit(Hitbox hitbox){//retun the number of monster that hit hitbox
        int k=0;
        Iterator<Monster> it = monsters.iterator();
        Monster curr;
        while (it.hasNext()) {
            curr = it.next();
            if (curr.collideHitbox(hitbox)) {
                k++;
            }
        }
        return k;
    }

    public void isHit(Hitbox hitbox){
        Iterator<Monster> it = monsters.iterator();
        Monster curr;
        while (it.hasNext()) {
            curr = it.next();
            if (curr.collideHitbox(hitbox)) {
                curr.isAttacked();
            } else {
                System.out.print("resultat " + curr.collideHitbox(hitbox));
                System.out.print(" non : " + curr.getRADIUS());
                System.out.print(" distance" + hitbox.getOrigin().distance(curr.position));
                System.out.print(" radius " + curr.getRADIUS());
                System.out.println(" Monster poss : " + curr.position  );
            }
        }
    }
}
