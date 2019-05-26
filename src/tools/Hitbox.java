package tools;

import java.awt.*;

public class Hitbox {
	private  Coordinate origin;
	private double radius;
	private final double SIDE = Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 60;

	public Hitbox(Coordinate origin, double radius) {
		this.origin = origin;
		this.radius = radius;
	}

	public boolean collide(Hitbox other) {
		return this.origin.distance(other.origin) * 2 < this.radius / SIDE + other.radius / SIDE;
	}

	public void setOrigin(Coordinate origin) {
		this.origin= origin;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
}