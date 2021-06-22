package src;

import java.util.ArrayList;

public class AntRace implements AntFields {


    public static void main(String[] args) {

        //processField(new AntField(FIELD11), 0,1);

        //processField( new AntField(FIELD9), 1,1);
         processField(new AntField(FIELD4), 2, 4);
         // processField(new AntField(FIELD5), 2, 1);
         //processField(new AntField(FIELD6), 0, 0);

    }

    public static void processField(AntField field, int startX, int startY) {
        System.out.println("\n\n\n===== starting =====\n");
        System.out.println(field);

        Ant ant = new Ant(field, startX, startY, 1);

        Thread thread = new Thread(ant);

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(field);
    }
}
