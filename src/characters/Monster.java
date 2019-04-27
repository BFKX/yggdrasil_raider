package characters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import mapping.Map;
import org.jetbrains.annotations.NotNull;
import tools.Coordinate;
import tools.Hitbox;

import java.awt.*;

public abstract class Monster extends Characters{
    Coordinate positionInt;
    int directionX ;
    int directionY ;

    public Monster(@NotNull Coordinate positionInt, Map map){
        super(positionInt, map);
        this.positionInt = positionInt ;
        this.position= new Coordinate(positionInt.getX()*SIDE, positionInt.getY()*SIDE ) ;
        imageSet.put("movingNorth", new Image("resources/images/movingNorthCharacter.png"));
        imageSet.put("movingSouth", new Image("resources/images/movingSouthCharacter.png"));
        imageSet.put("movingWest", new Image("resources/images/movingWestCharacter.png"));
        imageSet.put("movingEast", new Image("resources/images/movingEastCharacter.png"));
        imageSet.put("movingNorthEast", new Image("resources/images/movingNorthEastCharacter.png"));
        imageSet.put("movingSouthEast", new Image("resources/images/movingSouthEastCharacter.png"));
        imageSet.put("movingNorthWest", new Image("resources/images/movingNorthWestCharacter.png"));
        imageSet.put("movingSouthWest", new Image("resources/images/movingSouthWestCharacter.png"));
        imageSet.put("waiting", new Image("resources/images/waitingCharacter.png"));
    }

    public abstract void updateDeplacement();

    protected void update(){
        this.position.add( speedX ,  speedY);
        this.positionInt = new Coordinate(position.getX() / SIDE , position.getY() / SIDE );
        try{
            map.getMap()[(int) positionInt.getX()][(int) positionInt.getY()] = -900;
        }catch (IndexOutOfBoundsException e){
            System.out.println((int) positionInt.getX() + "a,a" +(int) positionInt.getY() );
            System.out.println((map.getMap().length +";" + map.getMap()[0].length));
            System.out.println("non");
        }
    }

    public Coordinate getPosition() {
        return position;
    }

    public Coordinate getPositionInt() {
        return positionInt;
    }

    protected int sign(double x) {
        if ( x > 0 ){
            return  1 ;
        }else if(x< 0){
            return -1;
        }
        return 0;
    }
}
