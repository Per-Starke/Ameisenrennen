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

    }

    /**
     * Set the position of this ant to a new position.
     * Automatically add 1 to this stepCount
     *
     * @param x the new x-pos
     * @param y the new y-pos
     */
    public void setPos(int x, int y) {
        synchronized (this.getClass()) {
            System.out.println("called by ant " + startX + ":" + startY);
            this.x = x;
            this.y = y;
            this.stepCount++;
            fields.getField(x, y).setValue(stepCount);
        }
    }

    /**
     * Check whether the field at the given position is free or its stepCount is greater than the one of this ant
     *
     * @param x the x pos of the field to check
     * @param y the y pos of the field to check
     * @return True if field is free or has greater value than this.stepCount, False otherwise
     */
    public synchronized boolean checkField(int x, int y) {
        int value = fields.getField(x, y).getValue();

        return (value == 0 || value > this.stepCount + 1);
    }

    /**
     * Is this ant finished or should it continue taking steps?
     *
     * @return True if this ant has no neighbors or all neighbors are "false" by the checkField method
     */
    private boolean finished() {
        neighbors = fields.mooreNeighbours(this.x, this.y);

        boolean allNeighborsFalse = true;
        for (int i = 0; i < neighbors.size(); i++) {
            if (checkField(neighbors.get(i).getX(), neighbors.get(i).getY())) {
                allNeighborsFalse = false;
            }
        }

        return allNeighborsFalse || neighbors.isEmpty();
    }


    /**
     * Single iteration of finding + moving to neighbors
     */
    public void oneRun() {
        // Case: Neighbors is empty, then this ant stops and is finished. We can leave this blank, as we do nothing!



        // Case: Neighbors_length is >=1: Move to first neighbor, start new ants for all others
        if (neighbors.size() >= 1) {
            int neighbourX = neighbors.get(0).getX();
            int neighbourY = neighbors.get(0).getY();

            // move THIS ant to first neighbour
            if (checkField(neighbourX, neighbourY)) {
                this.setPos(neighbourX, neighbourY);
            }

            // Iterate through all others neighbors and start new ant-threads
            for (int i = 1; i < neighbors.size(); i++) {
                neighbourX = neighbors.get(i).getX();
                neighbourY = neighbors.get(i).getY();

                if (checkField(neighbourX, neighbourY)) {
                    Ant ant = new Ant(this.fields, neighbourX, neighbourY, this.stepCount);
                    Thread thread = new Thread(ant);
                    thread.start();

//               try {
//                  thread.join();
//               } catch (InterruptedException e) {
//                  e.printStackTrace();
//               }
                }
            }
        }


    }

    private void antStatus() {
        synchronized(this.getClass()) {
            System.out.println("Ant " + startX + "/" + startY + ":" + neighbors.size() + " neighbours, current pos: " + x + "/" + y + " stepCount " + stepCount);
            System.out.println( fields);
        }
    }

    /**
     * Run through the fields and find APSP
     */
    public void run() {
        while (!finished()) {
            // for debugging purpuses - print this ants' status
            antStatus();
            this.oneRun();
        }
    }

}
