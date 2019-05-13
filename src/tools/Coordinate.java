package tools;


public class Coordinate {

	private double x;
	private double y;

	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Coordinate sum(double x, double y) {
		return new Coordinate(this.getX() + x, this.getY() + y);
	}

	public void add(double x, double y) {
		this.x += x;
		this.y += y;
	}

	public Coordinate copy(){
		return new Coordinate(x, y);
	}

	public double distance(Coordinate other) {
		return Math.sqrt(Math.pow((this.getX() - other.getX()), 2) + Math.pow((this.getY() - other.getY()), 2));
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public boolean isValid(Coordinate origin, int height, int width) {
		return this.getX() > origin.getX() && this.getX() < origin.getX() + width && this.getY() > origin.getY()
				&& this.getY() < origin.getY() + height;
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
