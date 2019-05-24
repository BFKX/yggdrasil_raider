package characters;

import javafx.scene.canvas.GraphicsContext;
import tools.Coordinate;
import tools.Hitbox;

public abstract class Monster extends Character {
    Coordinate mainCharactersPosition ;

    Monster(Coordinate position,Coordinate mainCharactersPosition){
        super(position);
        this.mainCharactersPosition =  mainCharactersPosition;
    }

    public abstract void updateDisplacement();

    public abstract void display(GraphicsContext gc, Coordinate mainCharactersPosition);

    void update(GraphicsContext gc) {
        updateDisplacement();
        this.position.add( speedX/SIDE, speedY/SIDE);
    }
    public void setMainCharactersPosition(Coordinate position){
        this.mainCharactersPosition=position;
    }

    public boolean collideHitbox(Hitbox hitbox){
        return this.hitbox.collide(hitbox);
    }

    public void isAttacked(){
        System.out.println("\t\t\tCOUCOU\t\t\t");
        this.healthPoint -= 200;
        this.speedX = - this.speedX;
        this.speedY = - this.speedY ;
    }

}
