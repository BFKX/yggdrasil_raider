package tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

public class Hitbox {
	private  Coordinate origin;
	private double radius;
	final double SIDE = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;
	private final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	public Hitbox(Coordinate origin, double radius) {
		this.origin = origin;
		this.radius = radius;
	}

	public boolean collide(Hitbox other) {
		return this.origin.distance(other.getOrigin()) <= this.radius/SIDE + other.getRadius();
	}

	public void draw(GraphicsContext gc, Coordinate characterPosition) {
		gc.save();
		double xOffset = characterPosition.getX() - WIDTH / (2 * SIDE);
		double yOffset = characterPosition.getY() - HEIGHT / (2 * SIDE);
		gc.setStroke(Color.GREENYELLOW);
		gc.strokeOval((this.origin.getX() - xOffset) * SIDE - radius / 2, (this.origin.getY() - yOffset) * SIDE - radius / 2, radius, radius);
		gc.restore();
	}

	public Coordinate getOrigin() {
		return origin;
	}

	public void setOrigin(Coordinate origin){
		this.origin= origin ;
	}
	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) { this.radius = radius; }

}