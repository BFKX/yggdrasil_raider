package characters;

import javafx.scene.canvas.GraphicsContext;
import tools.Coordinate;
import tools.Hitbox;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Monster extends Character {
    Coordinate mainCharactersPosition;

    Monster(Coordinate position, Coordinate mainCharactersPosition){
        super(position);
        this.mainCharactersPosition = mainCharactersPosition;
    }

    public abstract void updateDisplacement();

    public abstract void display(GraphicsContext gc, Coordinate mainCharactersPosition);

    public void update() {
        updateDisplacement();
        this.position.add(speedX / SIDE, speedY / SIDE);
    }

    public boolean collideHitbox(Hitbox hitbox){
        return this.hitbox.collide(hitbox);
    }

    private void knockBack(){
        this.speedX = - this.speedX * 5 ;
        this.speedY = - this.speedY * 5 ;
    }

    public void isAttacked(){
        this.healthPoint -= (ThreadLocalRandom.current().nextInt(0, 10) > 2) ? 10 : 20;
        knockBack();
    }

    public void setMainCharactersPosition(Coordinate position){
        this.mainCharactersPosition = position;
    }
}
