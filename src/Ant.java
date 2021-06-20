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
 * 
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

   /**
    * The step-count of this ant
    */
   private int stepCount;

   /**
    * 
    * @param fields
    *           the {@code AntField} on which this {@code Ant} operates
    * @param x
    *           x-axis value of the starting position
    * @param y
    *           y-axis value of the starting position
    * @param stepCount
    *           initial stepCount of this {@code Ant}.
    * 
    * @throws IllegalArgumentException
    *            If the {@code Field} at position {@code x,y} does not exist, or
    *            if its value is < 0
    */
   public Ant(AntField fields, int x, int y, int stepCount) {
      this.fields = fields;
      this.x = x;
      this.y = y;
      this.stepCount = stepCount;

      // Set the stepCount on the current field on this.stepCount
      this.fields.getField(this.x, this.y).setValue(this.stepCount);
   
   }

   /**
    * Set the position of this ant to a new position.
    * Automatically add 1 to this stepCount
    * @param x the new x-pos
    * @param y the new y-pos
    */
   public void setPos(int x, int y){
      synchronized(this.getClass()){
         this.x = x;
         this.y = y;
         this.stepCount++;
         fields.getField(x, y).setValue(stepCount);
      }
   }

   /**
    * Check whether the field at the given position is free or its stepCount is greater than the one of this ant
    * @param x the x pos of the field to check
    * @param y the y pos of the field to check
    * @return True if field is free or has greater value than this.stepCount, False otherwise
    */
   public boolean checkField(int x, int y){
      int value = fields.getField(x, y).getValue();

      return (value==0 || value>this.stepCount+1);
   }

   /**
    * Single iteration of finding + moving to neighbors
    */
   public void oneRun(){
      /*
      Use the get_neighbors method on the current position here, once implemented!
       */
      //int[][] neighbors = {{2,5}, {3,4}, {3,5}};
     ArrayList<FieldCoordinate> neighbors =  fields.mooreNeighbours( this.x, this.y );
     /*
     Three cases:
     1) Neighbors is empty: Stop
     2) Neighbors is only 1 field: move there
     3) Several neighbors: Move to one, start new Threads for the others
     The last 2 cases can be treated in one
      */

      int neighborsLength = neighbors.size();

      // Case: Neighbors is empty, then this ant stops and is finished. We can leave this blank, as we do nothing!

      // Case: Neighbors_length is >=1: Move to first neighbor, start new ants for all others
      if (neighborsLength >= 1){
         int x = neighbors.get(0).getX();
         int y = neighbors.get(0).getY();

         if(checkField(x, y)){
            this.setPos(x, y);
         }

         // Iterate through all others neighbors and start new ant-threads
         for(int i=1; i<neighborsLength; i++){
            x = neighbors.get(i).getX();
            y = neighbors.get(i).getY();

            if(checkField(x, y)){
               Ant ant = new Ant(this.fields, x, y, this.stepCount);
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

   /**
    * Run through the fields and find APSP
    */
   public void run() {
      this.oneRun();
   }

}
