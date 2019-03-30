package tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Hitbox {

    private Coordinate origine;
    private double width;
    private double height;

    public Hitbox(Coordinate origine, double width, double height) {

        this.origine = origine;
        this.width = width;
        this.height = height;
    }

    public boolean collide(Hitbox hitbox) {

        return this.origine.getX() - hitbox.origine.getX() < hitbox.width
                && this.origine.getY() - hitbox.origine.getY() < hitbox.height
                && hitbox.origine.getX() - this.origine.getX() < this.width
                && hitbox.origine.getY() - this.origine.getY() < this.height;
    }

    public void draw(GraphicsContext gc) {
        Paint stroke = gc.getStroke();
        gc.setStroke(Color.BLUE);
        gc.strokeRect(origine.getX(), origine.getY(), width, height);
        gc.setStroke(stroke);
    }
}