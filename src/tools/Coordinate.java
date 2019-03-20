package tools;

public class Coordinate {

    private double x ;
    private double y ;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate sum(double x , double y ){ // add x to the first coordinate and y to the second one
        return new Coordinate(this.getX()+x , this.getY()+y) ;
    }

    public void add(double x, double y) {

        this.x += x;
        this.y += y;
    }

    public double length(Coordinate auther ){
        return  (Math.pow((this.getX() - auther.getX()),2) + Math.pow((this.getY()-auther.getY()),2)) ;
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
    public boolean isValid(Coordinate orrigine, int height , int width ){ //return true if the coorinate is the rec betwin origine and orignie + height , width
        return ( this.getX()>orrigine.getX() && this.getX() < orrigine.getX() + width  && this.getY()>orrigine.getY() && this.getY() < orrigine.getY()+ height );
    }
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
