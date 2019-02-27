package mapping;

import tools.Hitbox ;
import javafx.scene.image.Image;

public class Case {

    private int type ;
    private Image image ;
    private Hitbox hitbox ;

    Case(int type, Image image, Hitbox hitbox) {
        this.type = type;
        this.image = image;
        this.hitbox = hitbox;
    }

    void setType(int type) {
        this.type = type;
    }

    void setImage(Image image) {

        this.image = image;
    }

    void setHitbox(Hitbox hitbox) {

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
