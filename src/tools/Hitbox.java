package tools;

import javafx.scene.canvas.GraphicsContext;
import org.jetbrains.annotations.NotNull;

public class Hitbox {
	private final Coordinate origin;
	private final double radius;

	public Hitbox(Coordinate origin, double radius) {
		this.origin = origin;
		this.radius = radius;
	}

	public boolean collide(@NotNull Hitbox other) {
		return this.origin.distance(other.getOrigin()) < this.radius - other.getRadius();
	}

	public void draw(@NotNull GraphicsContext gc) {
		gc.save();
		gc.strokeOval(origin.getX() - radius / 2, origin.getY() - radius / 2, radius, radius);
		gc.restore();
	}

	private Coordinate getOrigin() {
		return origin;
	}

	private double getRadius() {
		return radius;
	}
}