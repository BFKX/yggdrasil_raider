package tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Hitbox {

    private Coordinate origine;
    private double radius;

    public Hitbox(Coordinate origine, double radius) {
        this.origine = origine;
        this.radius = radius;
    }

    public boolean collide(Hitbox other) {
        return this.origine.distance(other.getOrigine()) < this.radius - other.getRadius();
    }

    public void draw(GraphicsContext gc) {
        Paint stroke = gc.getStroke();
        gc.setStroke(Color.BLUE);
        gc.strokeOval(origine.getX() - radius / 2, origine.getY() - radius / 2, radius, radius);
        gc.setStroke(stroke);
    }

    private Coordinate getOrigine() {
        return origine;
    }

    private double getRadius() {
        return radius;
    }
}