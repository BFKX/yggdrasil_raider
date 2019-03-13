package tools;

public class Coordinate {

    private double x ;
    private double y ;

    public Coordinate(double x, double y) {

        this.x = x;
        this.y = y;
    }
    public Coordinate sum(int x , int y ){
        return new Coordinate(this.getX()+x , this.getY()+y) ;
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

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
