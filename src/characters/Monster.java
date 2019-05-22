package characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import tools.Coordinate;
import tools.Hitbox;

public abstract class Monster extends Character {
    Coordinate mainCharactersPosition ;

    Monster(Coordinate position,Coordinate mainCharactersPosition){
        super(position);
        this.mainCharactersPosition =  mainCharactersPosition;
        spriteSet.put("movingNorth", new Image("resources/images/movingNorthCharacter.png"));
        spriteSet.put("movingSouth", new Image("resources/images/movingSouthCharacter.png"));
        spriteSet.put("movingWest", new Image("resources/images/movingWestCharacter.png"));
        spriteSet.put("movingEast", new Image("resources/images/movingEastCharacter.png"));
        spriteSet.put("movingNorthEast", new Image("resources/images/movingNorthEastCharacter.png"));
        spriteSet.put("movingSouthEast", new Image("resources/images/movingSouthEastCharacter.png"));
        spriteSet.put("movingNorthWest", new Image("resources/images/movingNorthWestCharacter.png"));
        spriteSet.put("movingSouthWest", new Image("resources/images/movingSouthWestCharacter.png"));
        spriteSet.put("waiting", new Image("resources/images/waitingCharacter.png"));
    }

    public abstract void updateDisplacement();

    public abstract void display(GraphicsContext gc, Coordinate mainCharactersPosition);

    void update(GraphicsContext gc) {
        updateDisplacement();
        this.position.add( speedX/SIDE, speedY/SIDE);
    }

    public boolean collideHitbox(Hitbox hitbox){
        return this.hitbox.collide(hitbox);
    }

    public void isAttacked(){
        System.out.println("\t\t\tCOUCOU\t\t\t");
    }

}
