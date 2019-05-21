package characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mapping.Map;
import tools.Coordinate;
import tools.Hitbox;

public abstract class Monster extends Character {
    Coordinate positionInt;
    Coordinate mainCharactersPosition ;
    int directionX ;
    int directionY ;

    Monster(Coordinate positionInt,Coordinate mainCharactersPosition){
        super(positionInt);
        this.positionInt = positionInt ;
        this.mainCharactersPosition =  mainCharactersPosition ;
        this.position= new Coordinate(positionInt.getX()*SIDE, positionInt.getY()*SIDE ) ;
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
        this.position.add( speedX, speedY);
        this.positionInt = new Coordinate(position.getX() / SIDE , position.getY() / SIDE );
    }

    public boolean collideHitbox(Hitbox hitbox){
        return this.hitbox.collide(hitbox);
    }

    public void isAttacked(){
        System.out.println("Je suis attaqué !!!!!!"+this.positionInt + "radius : " + this.getRADIUS());
    }

}
