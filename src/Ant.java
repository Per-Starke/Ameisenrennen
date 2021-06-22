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

    private ArrayList<Thread> threads = new ArrayList<Thread>();

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
        if (this.fields.getField(this.x, this.y) != null) {
            this.fields.getField(this.x, this.y).setValue(this.stepCount);
            System.out.format("Field value set to %d ", this.stepCount);
        }
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
    private synchronized boolean finished() {
        neighbors = fields.mooreNeighbours(this.x, this.y);

        boolean allNeighborsFalse = true;
        for (int i = 0; i < neighbors.size(); i++) {
            if (isValidFieldToWalkOn(neighbors.get(i).getX(), neighbors.get(i).getY())) {
                allNeighborsFalse = false;
            }
        }

        return allNeighborsFalse || neighbors.isEmpty();
    }


    private void printAntStatus() {

        System.out.format("Status: Ant %d:%d, stepCount %d has %d neighbours, currentPos %d:%d %s\n%s",
                startX, startY, stepCount, neighbors.size(), x, y, neighbors, fields);
    }

    /**
     * Run through the fields and find APSP
     */
    public void run() {
        while (!finished()) {
            // for debugging purposes - print this ants' status
            printAntStatus();

            // nicer code with Iterator... but leave that for another day
            // Iterator currentNeighbours = neighbors.iterator();


        // If we have at least one neighbor
            boolean this_ant_walked_to_neighbor = false;

            if (neighbors.size() >= 1) {

                // Check each neighbor if it is valid field to walk on
                for (FieldCoordinate neighbor : neighbors) {
                    int neighbor_x = neighbor.getX();
                    int neighbor_y = neighbor.getY();

                    synchronized (fields) {
                        if (isValidFieldToWalkOn(neighbor_x, neighbor_y))

                           // System.out.println("Test");

                            // If the ant has not walked yet, walk there
                            if (!this_ant_walked_to_neighbor) {
                                walkToNewPositionAndIncrementStepcount(neighbor_x, neighbor_y);
                                this_ant_walked_to_neighbor = true;
                            }
                            // If the ant has walked already, create new ants for remaining valid neighbors
                            else {
                                System.out.format("New ant starting at field %d:%d with stepcount %d \n", neighbor_x, neighbor_y, stepCount);
                                Ant ant = new Ant(this.fields, neighbor_x, neighbor_y, this.stepCount);
                                Thread thread = new Thread(ant);
                                thread.start();
                                threads.add(thread);
                            }
                    }


                }
            }
            else {
                for (Thread thread: threads){
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


//            // if we have at least one neighbour...
//            if (neighbors.size() >= 1) {
//
//                // move THIS ant to first neighbour,
//                // and increment its stepcount
//                int neighbourX = neighbors.get(0).getX();
//                int neighbourY = neighbors.get(0).getY();
//
//                if (isValidFieldToWalkOn(neighbourX, neighbourY))
//                    walkToNewPositionAndIncrementStepcount(neighbourX, neighbourY);
//
//                //System.out.format("...ant %d:%d from pos %d:%d will create %d new ants at %s \n",
//                //        x, y, startX, startY, neighbors.size() - 1, neighbors);
//
//                // Iterate through all others neighbors and start new ant-threads
//
//                synchronized (fields) {
//                    for (int i = 1; i < neighbors.size(); i++) {
//                        neighbourX = neighbors.get(i).getX();
//                        neighbourY = neighbors.get(i).getY();
//
//                        if (isValidFieldToWalkOn(neighbourX, neighbourY)) {
//                            System.out.format("New ant starting at field %d:%d with stepcount %d \n", neighbourX, neighbourY, stepCount);
//                            Ant ant = new Ant(this.fields, neighbourX, neighbourY, this.stepCount);
//                            Thread thread = new Thread(ant);
//                            thread.start();
//                        }
//                    }
//                } // synchronized
//            }
            // we have no neighbour and need to wait for the other threads...

        }

    }

