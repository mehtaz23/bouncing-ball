// Muntaqim Mehtaz (mehta216)

// importing java built-in class for Math
import static java.lang.Math.abs;

// creating CollisionLogger class
public class CollisionLogger {

    // setting data members for CollisionLogger class
    private int[][] grid;
    private int totalXGridNumber;
    private int totalYGridNumber;
    private int bucketWidth;
    private int maxCollisionNumber;


    /* creating constructor for CollisionLogger class
     * takes in @params screenWidth,screenHeight and bucketWidth of type int
     */
    public CollisionLogger(int screenWidth, int screenHeight, int bucketWidth) {

        totalXGridNumber = screenWidth / bucketWidth;
        totalYGridNumber = screenHeight / bucketWidth;
        this.bucketWidth = bucketWidth;

        grid = new int[totalXGridNumber][totalYGridNumber]; // passing in grid numbers for row and column in the 2D array
        //Initialize Grid
        for (int columnIndex = 0; columnIndex < totalXGridNumber; columnIndex++) { // iterating through column
            for (int rowIndex = 0; rowIndex < totalYGridNumber; rowIndex++) { // iterating through row
                grid[columnIndex][rowIndex] = 0;
            }
        }

        maxCollisionNumber = 0; // keeping track of max collisions
    }

    /**
     * This method records the collision event between two balls. Specifically, in increments the bucket corresponding to
     * their x and y positions to reflect that a collision occurred in that bucket.
     */
    public void collide(Ball one, Ball two) {
        int collisionX = abs((int) ((one.getXPos() + two.getXPos()) / 2)); // finding collision x-coordinate
        int collisionY = abs((int) ((one.getYPos() + two.getYPos()) / 2)); // finding collision y-coordinate
        int divCollisionX = collisionX / bucketWidth; // dividing x-coordinate by bucketWidth
        int divCollisionY = collisionY / bucketWidth; // dividing y-coordinate by bucketWidth

        if (divCollisionX >= totalXGridNumber) {
            divCollisionX = totalXGridNumber - 1;
        }

        if (divCollisionY >= totalYGridNumber) {
            divCollisionY = totalYGridNumber - 1;
        }

        grid[divCollisionX][divCollisionY] += 1;
        if (grid[divCollisionX][divCollisionY] > maxCollisionNumber) { // checking if values crosses max
            maxCollisionNumber = grid[divCollisionX][divCollisionY];
        }

    }

    /**
     * Returns the heat map scaled such that the bucket with the largest number of collisions has value 255,
     * and buckets without any collisions have value 0.
     */
    public int[][] getNormalizedHeatMap() {

        int[][] normalizedHeatMap = new int[totalXGridNumber][totalYGridNumber];
        if (maxCollisionNumber > 0) {
            double ratio = (double)255 / (double) maxCollisionNumber; // finding ratio for normalization
            System.out.println(" RATIO : " + ratio); // printing ratio value
            for (int columnIndex = 0; columnIndex < totalXGridNumber; columnIndex++) { // iterating through column
                for (int rowIndex = 0; rowIndex < totalYGridNumber; rowIndex++) { // iterating through row
                    double value = ratio * grid[columnIndex][rowIndex]; // finding value after normalization
                    double floorValue = Math.floor(value); // flooring the value
                    int intValue = (int) floorValue;
                    normalizedHeatMap[columnIndex][rowIndex] = (int)floorValue;
                }
            }
        }

        return normalizedHeatMap;
    }
    // method for printing out collisionLogger info
    public void printCollisionLoggerInformation() {
        System.out.println(" MAX COllision is : " + maxCollisionNumber); // printing out max collision number
        System.out.println("GRID ");
        for (int columnIndex = 0; columnIndex < totalXGridNumber; columnIndex++) { // going through columns
            for (int rowIndex = 0; rowIndex < totalYGridNumber; rowIndex++) { // going through rows
            }
        }

        System.out.println("Normalize MAP");
        int[][] normalizedHeatMap = getNormalizedHeatMap(); // setting heatMap
        for (int columnIndex = 0; columnIndex < totalXGridNumber; columnIndex++) {
            for (int rowIndex = 0; rowIndex < totalYGridNumber; rowIndex++) {
                System.out.println( " X : " + columnIndex + " Y : " + rowIndex + " Value : " + normalizedHeatMap[columnIndex][rowIndex]);
            } // printing values for columns and rows
        }
    }

}
