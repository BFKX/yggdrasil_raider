package mapping;
import tools.Hitbox ;
import javafx.scene.image.Image;

public class Case {
    private int type ;
    private Image image ;
    private Hitbox hitbox ;

    public Case(int type, Image image, Hitbox hitbox) {
        this.type = type;
        this.image = image;
        this.hitbox = hitbox;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setHitbox(Hitbox hitbox) {
        this.hitbox = hitbox;
    }

    public int getType() {
        return type;
    }

    public Image getImage() {
        return image;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }
}
