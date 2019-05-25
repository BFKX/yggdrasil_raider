package characters;

import javafx.scene.canvas.GraphicsContext;
import tools.Coordinate;
import tools.Hitbox;

public abstract class Monster extends Character {
    Coordinate mainCharactersPosition;

    Monster(Coordinate position, Coordinate mainCharacterPosition) {
        super(position);
        this.mainCharactersPosition = mainCharacterPosition;
    }

    public abstract void updateDisplacement();

    public abstract void display(GraphicsContext gc, Coordinate mainCharactersPosition);

    void update() {
        updateDisplacement();
        this.position.add(speedX/SIDE, speedY/SIDE);
    }

    void setMainCharactersPosition(Coordinate position){
        this.mainCharactersPosition=position;
    }

    boolean collideHitbox(Hitbox hitbox){
        return this.hitbox.collide(hitbox);
    }

    void isAttacked(){
        healthPoint -= 10;
        speedX = -speedX;
        speedY = -speedY;
    }

}
