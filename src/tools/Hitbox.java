package tools;

import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public class Hitbox {
	private final Coordinate origin;
	private double radius;
	final double SIDE = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;

	public Hitbox(Coordinate origin, double radius) {
		this.origin = origin;
		this.radius = radius;
	}

	public boolean collide(Hitbox other) {
		return this.origin.distance(other.getOrigin()) <= this.radius/SIDE + other.getRadius()/SIDE;
	}

	public void draw(GraphicsContext gc) {
		gc.save();
		gc.strokeOval(origin.getX() - radius / 2, origin.getY() - radius / 2, radius, radius);
		gc.restore();
	}

	public Coordinate getOrigin() {
		return origin;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) { this.radius = radius; }
}