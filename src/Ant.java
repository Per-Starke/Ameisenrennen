package src;

import java.util.ArrayList;

/**
 * An {@code Ant} is created at a specific position of an {@link AntField} with
 * an initial {@code stepCount}. When running an Ant, it will lookup the values
 * on the current and all surrounding {@link AntField}
 * (Moore-neighborhood) instances and test if the position is free, i.e. has a
 * value of {@code 0}, or if the value is greater than the {@code stepCount} of
 * this Ant. For both cases, the Ant will set the value of the {@code Field} at
 * the visited position to its own {@code stepCount+1}. After an {@code Ant} has
 * successfully visited one field, it will create new {@code Ant} instances with
 * an incremented {@code stepCount} to visit the other available {@code Field}
 * elements. The Ant will run until it finds no more {@code Field} elements in
 * its neighborhood to be altered.
 *
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 */
public class Ant implements Runnable {

    /**
     * The fields / world this ant is s
     */
    private AntField fields;

    /**
     * The x and y position of this ant in the fields / world
     */
    private int x;
    private int y;

    private int startX;
    private int startY;

    /**
     * The step-count of this ant
     */
    private int stepCount;

    /**
     * The current neighbour fields of this ant
     */
    private ArrayList<FieldCoordinate> neighbors;

    /**
     * @param fields    the {@code AntField} on which this {@code Ant} operates
     * @param x         x-axis value of the starting position
     * @param y         y-axis value of the starting position
     * @param stepCount initial stepCount of this {@code Ant}.
     * @throws IllegalArgumentException If the {@code Field} at position {@code x,y} does not exist, or
     *                                  if its value is < 0
     */
    public Ant(AntField fields, int x, int y, int stepCount) {
        this.fields = fields;
        this.x = x;
        this.y = y;
        this.stepCount = stepCount;

        this.startX = x;
        this.startY = y;

        // Set the stepCount on the current field on this.stepCount
        this.fields.getField(this.x, this.y).setValue(this.stepCount);
        //System.out.format("   constructed ant %d:%d set field value to %d\n", this.x, this.y, stepCount);

    }

    /**
     * Set the position of this ant to a new position.
     * Automatically add 1 to this stepCount
     *
     * @param x the new x-pos
     * @param y the new y-pos
     */
    public void walkToNewPositionAndIncrementStepcount(int x, int y) {
        synchronized (fields) {
            //System.out.format("Ant %d:%d walks to %d:%d with stepcount=%d \n", startX, startY, x, y, stepCount);
            this.x = x;
            this.y = y;
            this.stepCount++;
            try {
                fields.getField(x, y).setValue(stepCount);
            }
            catch (IllegalArgumentException iae) {
                System.out.format( "==== damned error in %d:%d", x,y);
            }
            //printAntStatus();

        }
    }

    /**
     * Check whether the field at the given position is free or its stepCount is greater than the one of this ant
     *
     * @param x the x pos of the field to check
     * @param y the y pos of the field to check
     * @return True if field is free or has greater value than this.stepCount, False otherwise
     */
    public synchronized boolean isValidFieldToWalkOn(int x, int y) {
        int value = fields.getField(x, y).getValue();

        return (value == 0 || value > this.stepCount + 1);
    }

    /**
     * Is this ant finished or should it continue taking steps?
     *
     * @return True if this ant has no neighbors or all neighbors are "false" by the checkField method
     */
    private boolean finished() {
        neighbors = fields.validMooreNeighbours(this.x, this.y, this.stepCount);

        boolean allNeighborsFalse = true;
        for (int i = 0; i < neighbors.size(); i++) {
            if (isValidFieldToWalkOn(neighbors.get(i).getX(), neighbors.get(i).getY())) {
                allNeighborsFalse = false;
            }
        }

        return allNeighborsFalse || neighbors.isEmpty();
    }


    private synchronized void printAntStatus() {

        System.out.format("Status: Ant %d:%d, stepCount %d has %d neighbours, currentPos %d:%d %s\n%s",
                startX, startY, stepCount, neighbors.size(), x, y, neighbors, fields);
    }

    /**
     * Run through the fields and find APSP
     */
    public void run() {
        while (!finished()) {
            // for debugging purpuses - print this ants' status
            printAntStatus();

            // nicer code with Iterator... but leave that for another day
            // Iterator currentNeighbours = neighbors.iterator();

            // if we have at least one neighbour...
            if (neighbors.size() >= 1) {

                // move THIS ant to first neighbour,
                // and increment its stepcount
                int neighbourX = neighbors.get(0).getX();
                int neighbourY = neighbors.get(0).getY();

                // don't need to check for valid field here, as that's already done
                // in validMooreNeighbours()
                if (isValidFieldToWalkOn(neighbourX, neighbourY))
                    walkToNewPositionAndIncrementStepcount(neighbourX, neighbourY);

                //System.out.format("...ant %d:%d from pos %d:%d will create %d new ants at %s \n",
                //        x, y, startX, startY, neighbors.size() - 1, neighbors);

                // Iterate through all others neighbors and start new ant-threads

                synchronized (fields) {
                    for (int i = 1; i < neighbors.size(); i++) {
                        neighbourX = neighbors.get(i).getX();
                        neighbourY = neighbors.get(i).getY();

                        if (isValidFieldToWalkOn(neighbourX, neighbourY)) {
                            System.out.format("New ant starting at field %d:%d with stepcount %d \n", neighbourX, neighbourY, stepCount);
                            Ant ant = new Ant(this.fields, neighbourX, neighbourY, this.stepCount);
                            Thread thread = new Thread(ant);
                            thread.start();
                        }
                    }
                } // synchronized
            }
            // we have no neighbour and need to wait for the other threads...
            else {
                // FIXME: wait for other threads...
            }
        }

    }

}