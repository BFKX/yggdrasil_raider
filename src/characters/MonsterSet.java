package characters;

import javafx.scene.canvas.GraphicsContext;
import tools.Coordinate;
import tools.Hitbox;
import tools.Node;

public class MonsterSet {
    private Node<Monster> root = null;

    public MonsterSet(int n, MainCharacter mainCharacter, int[][] map) {
        System.out.print(mainCharacter);
        for (int i = 0; i < n; i++) {
            this.add(new BasicMonster(new Coordinate(Math.random()*150,Math.random()*150), mainCharacter.getPosition(), map));
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
            }
        }
    }
}
