package characters;

import javafx.scene.canvas.GraphicsContext;
import mapping.Room;
import tools.Coordinate;
import tools.Hitbox;
import tools.Node;

import java.util.concurrent.ThreadLocalRandom;

public class MonsterSet {
    private Node<Monster> root = null;

    public MonsterSet(int n, MainCharacter mainCharacter, int[][] map) {
        for (int i = 0; i < n; i++) {
            this.add(new BasicMonster(new Coordinate( map.length*Math.random(),
                     map[0] .length * Math.random()), mainCharacter.getPosition(), map));
        }

    }

    public void add(Monster monster) {
        root = new Node<>(monster, root);
    }

    public void del(Monster monster) {
        if (root != null) {
            if (root.getPayload() == monster) {
                root = root.getNext();
            } else {
                for(Node<Monster> curr = root.getNext(); curr.getNext() != null; curr = curr.getNext()) {
                    if (curr.getPayload() == monster) {
                        curr = curr.getNext();
                    }
                }
            }
        }
    }

    public void setStartMonster(Room room ){
        for(Node<Monster> curr = root; curr.getNext() != null; curr = curr.getNext()) {
            curr.getPayload().startposition(room);
        }
    }
    public void update(GraphicsContext gc) {
        for(Node<Monster> curr = root; curr.getNext() != null; curr = curr.getNext()) {
            curr.getPayload().update(gc);
        }
    }

    public void display(GraphicsContext gc, MainCharacter mainCharacter){
        for(Node<Monster> curr = root; curr.getNext() != null; curr = curr.getNext()) {
            curr.getPayload().display(gc, mainCharacter.getPosition());
        }
    }

    public void isHit(Hitbox hitbox){
        for(Node<Monster> curr = root; curr.getNext() != null; curr = curr.getNext()){
            if (curr.getPayload().collideHitbox(hitbox)){
                curr.getPayload().isAttacked();
            }else {
                System.out.print("resultat " + curr.getPayload().collideHitbox(hitbox));
                System.out.print(" non : " + curr.getPayload().getRADIUS());
                System.out.print(" distance" + hitbox.getOrigin().distance(curr.getPayload().position));
                System.out.print(" radius " + curr.getPayload().getRADIUS());
                System.out.println(" Monster poss : " + curr.getPayload().position  );
            }
        }
    }
}
