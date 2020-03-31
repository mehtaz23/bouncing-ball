// Muntaqim Mehtaz (mehta216)

// importing java built-in classes
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

// creating BallScreenSaver class which inherits the AnimationFrame class
public class BallScreenSaver extends  AnimationFrame {

    // setting data members for the BallScreenSaver class
    private int numberOfballs;
    public final int BORDER=30;
    private java.util.List<Ball> ballList = new ArrayList<Ball>(); // using a list to keep track of balls
    private CollisionLogger collisionLogger;
    private static final int COLLISION_BUCKET_WIDTH = 10;
    private int saveCounter=0;

    /* creating constructor for the BallScreenSaver class
     * takes in @params frameWidth,frameHeight and numberOfballs of type int and windowName of type String
     */
    public BallScreenSaver (int frameWidth, int frameHeight, String windowName, int numberOfballs){
        super(frameWidth, frameHeight, windowName); // calling super class AnimationFrame
        this.numberOfballs = numberOfballs;
        int redBallIndex = randInt(0,numberOfballs);
        setFPS(20);
        for(int i = 0; i < numberOfballs; i++) {
            Random rand = new Random();
            int randXposition = randInt(BORDER,400); // randomizing the x-position
            int randYposition = randInt(BORDER,400); // randomizing the y-position
            Ball ball = new Ball(  randXposition , randYposition , 25, Color.GREEN);
            double randSpeedX = randdouble(0.0,1.0); // randomizing the x-direction speed
            double randSpeedY = randdouble(0.0,1.0); // randomizing the y-direction speed
            ball.setSpeedX(randSpeedX); // setting random x-speed
            ball.setSpeedY(randSpeedY); // setting random y-speed
            if ( i == redBallIndex){
                ball.setColor(Color.RED);
            }
            ballList.add(ball); // adding ball to the list
        }
        // instantiating collisionLogger
        collisionLogger = new CollisionLogger(this.getWidth(),this.getHeight(), COLLISION_BUCKET_WIDTH);
    }

    // creating main method
    public static void main(String[] args){
        BallScreenSaver bb= new BallScreenSaver(600, 600, "Ball Screen Saver",20);
        bb.start();
    }

    // overriding the action method in AnimationFrame class
    @Override
    public void action() {
        for(int i = 0; i < numberOfballs; i++) { // iterating through list to find first ball
            Ball ball = ballList.get(i);

            for ( int j = 0; j < numberOfballs; j++) { // iterating through list to find second ball
                if ( j != i) {
                    Ball otherBall = ballList.get(j);

                    if (ball.intersect(otherBall)) { // checking for intersection
                        ball.collide(otherBall); // calling collide function
                        collisionLogger.collide(ball, otherBall); // calling collisionLogger
                    }
                }
            }

            // handling collisions within the border and X-position
            if ( ball.getXPos() + ball.getRadius()< BORDER || ball.getXPos() + ball.getRadius()>getHeight()-BORDER ){
                double v = ball.getSpeedX() * -1;
                ball.setSpeedX(v);
            }

            // handling collisions within the border and Y-position
            if ( ball.getYPos()< BORDER || ball.getYPos() + ball.getRadius()>getWidth()-BORDER ){
                double v = ball.getSpeedY() * -1;
                ball.setSpeedY(v);

            }

            ball.setPos(ball.getXPos() + ball.getSpeedX(), ball.getYPos() + ball.getSpeedY());
        }


    }

    // overriding the draw method in AnimationFrame class
    // takes in @param g of type Graphics
    @Override
    public  void draw(Graphics g){

        g.setColor(Color.white); // setting background color to white
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.black);
        g.drawRect(BORDER,BORDER,getWidth()-BORDER*2,getHeight()-BORDER*2); // drawing rectangular border
        for(int i = 0; i < numberOfballs; i++) {
            Ball ball = ballList.get(i);
            g.setColor(ball.getColor()); // setting color to ball's color
            g.fillOval((int)ball.getXPos(),(int)ball.getYPos(),(int)ball.getRadius(),(int)ball.getRadius());
        }
        g.setColor(Color.black);

    }

    // overriding the processKeyEvent method in AnimationFrame class
    @Override
    protected void processKeyEvent(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        //move if left key is pressed
        if (keyCode == KeyEvent.VK_LEFT)
        {
            System.out.println("Left key pressed..."); // printing out when left key is pressed
            for(int i = 0; i < numberOfballs; i++) { // going over ball List
                Ball ball = ballList.get(i); // obtaining ball from List
                ball.setSpeedX(ball.getSpeedX() - ball.getSpeedX() *.1); // decreasing the X-speed by 10%
                ball.setSpeedY(ball.getSpeedX() - ball.getSpeedY() * .1); // decreasing the Y-speed by 10%
            }
        }
        else if (keyCode == KeyEvent.VK_RIGHT)
        {
            System.out.println("Right key pressed..."); // printing out when right key is pressed
            for(int i = 0; i < numberOfballs; i++) { // going over ball List
                Ball ball = ballList.get(i); // obtaining ball from list
                ball.setSpeedX(ball.getSpeedX() + ball.getSpeedX() *.1); // increasing the X-speed by 10%
                ball.setSpeedY(ball.getSpeedX() + ball.getSpeedY() * .1); // increasing the Y-speed bt 10%
            }
        }else if (keyCode == KeyEvent.VK_P) { // printing of png image when user presses "p" on keyboard
            EasyBufferedImage image = EasyBufferedImage.createImage(collisionLogger.getNormalizedHeatMap());
            collisionLogger.printCollisionLoggerInformation();
            try {
                image.save("heatmap"+saveCounter+".png", EasyBufferedImage.PNG); // setting image details
                saveCounter++;
            } catch (IOException exc) {
                exc.printStackTrace();
                System.exit(1);
            }
        }
    }

    public int randInt(int min, int max){
        //a utility method to get a random int between min and max.
        return (int)(Math.random()*(max-min)+min);
    }

    public double randdouble(double min, double max){
        //a utility method to get a random double between min and max.
        return (Math.random()*(max-min)+min);
    }


}
