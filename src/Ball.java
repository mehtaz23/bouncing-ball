// Muntaqim Mehtaz (mehta216)

// importing java built-in classes for Math and Color
import java.awt.*;
import static java.lang.Math.sqrt;

// creating Ball class which inherits the Circle clss
public class Ball extends Circle {

    // setting data members for the Ball class
    private double SpeedX;
    private double SpeedY;

    /* constructor for the Ball class
     * takes in @params xPos,yPos and radius of type double and color of type Color
     */
    public Ball(double xPOs, double yPos, double radius, Color color) {
        super(xPOs, yPos, radius); // calling super class Circle
        this.setColor(color);
    }

    // setter methods

    /* method for setting the speed in X-direction
     * takes in param speedX of type double
     */
    public void setSpeedX(double speedX) {
        this.SpeedX = speedX;
    }

    /* method for setting the speed in Y-direction
     * takes in @param speedY of type double
     */
    public void setSpeedY(double speedY) {
        this.SpeedY = speedY;
    }

    // getter methods

    /* method for returning the speed in the X-direction
     * @returns SpeedX
     */

    public double getSpeedX() {
        return SpeedX;
    }

    /* method for returning the speed in the Y-direction
     * @returns SpeedY
     */
    public double getSpeedY() {
        return SpeedY;
    }
    /* method for updating the position of the ball
     * takes in @param timeUnit
     */
    public void updatePosition(double timeUnit) {
        setPos(getSpeedX() * timeUnit + getXPos(), getSpeedY() * timeUnit + getYPos());
    }

    /* method for checking intersection of two balls
     * takes in @param other of type Ball
     */
    public boolean intersect(Ball other) {
        double differenceX = getXPos() - other.getXPos();
        double differenceY = getYPos() - other.getYPos();
        double distance = sqrt((differenceX * differenceX) + (differenceY * differenceY));
        if ((distance) <= (getRadius() + other.getRadius())) {
            return true;
        } else {
            return false;
        }

    }

    /* method for collision of two balls
     * takes in @param other of type Ball
     */
    public void collide(Ball other) {
        if (this.intersect(other)) { // checks for intersection first
            setSpeedY(other.getSpeedY());
            setSpeedX(other.getSpeedX());
            if (other.getColor() == Color.RED) {
                setColor(Color.RED);
            }
            setPos(getXPos() + getSpeedX(), getYPos() + getSpeedY());
        }
    }
}



