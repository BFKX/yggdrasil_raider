package characters;

import javafx.scene.canvas.GraphicsContext;
import mapping.Room;
import tools.Coordinate;
import tools.Hitbox;
import tools.Node;
import mapping.Map;

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

    public void setMainCharacterPosition( Coordinate position ){
        for(Node<Monster> curr = root; curr.getNext() != null; curr = curr.getNext()) {
            curr.getPayload().setMainCharactersPosition(position);
        }
    }
    public void setMap(Map map){
        for(Node<Monster> curr = root; curr.getNext() != null; curr = curr.getNext()) {
            curr.getPayload().setMap(map);
        }
    }
    public void update(GraphicsContext gc) {
        for(Node<Monster> curr = root; curr.getNext() != null; curr = curr.getNext()) {
            if(curr.getPayload().healthPoint <= 0){
                curr = curr.getNext() !=null ? curr.getNext() : null;
            }else{
                curr.getPayload().update(gc);}
        }
    }

    public void display(GraphicsContext gc, MainCharacter mainCharacter){
        for(Node<Monster> curr = root; curr.getNext() != null; curr = curr.getNext()) {
            curr.getPayload().display(gc, mainCharacter.getPosition());
        }
    }
    public int hit(Hitbox hitbox){//retun the nombur of monster that hit hibox
        int k=0;
        for(Node<Monster> curr = root; curr.getNext() != null; curr = curr.getNext()) {
            if( hitbox.collide(curr.getPayload().getHitbox())){
                System.out.println(curr.getPayload().getPosition() +", " + hitbox.getOrigin());
                k++;
            }
        }
        return k;
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
