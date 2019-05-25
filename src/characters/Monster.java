package characters;

import javafx.scene.canvas.GraphicsContext;
import tools.Coordinate;
import tools.Hitbox;

public abstract class Monster extends Character {
    Coordinate mainCharactersPosition;

    Monster(Coordinate position, Coordinate mainCharactersPosition){
        super(position);
        this.mainCharactersPosition = mainCharactersPosition;
    }

    public abstract void updateDisplacement();

    public abstract void display(GraphicsContext gc, Coordinate mainCharactersPosition);

    void update() {
        updateDisplacement();
        this.position.add(speedX / SIDE, speedY / SIDE);
    }

    boolean collideHitbox(Hitbox hitbox){
        return this.hitbox.collide(hitbox);
    }

    private void knockBack(){
        this.speedX = - this.speedX * 5 ;
        this.speedY = - this.speedY * 5 ;
    }

    void isAttacked(){
        this.healthPoint -= 10;
        knockBack();
    }

    void setMainCharactersPosition(Coordinate position){
        this.mainCharactersPosition = position;
    }
}
