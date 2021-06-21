package src;

import java.util.ArrayList;

/*

 */
public class MooreNeighbourTest {

    static AntField testField;
    static ArrayList<FieldCoordinate> neighbours = new ArrayList<FieldCoordinate>();

    public static void main(String[] args) {
        System.out.println("SingleField has no neighbours: " + singleField());
        System.out.println("Square with three neighbours: " + squareWithThreeNeighbours());
        System.out.println("Row with one neighbour: " + rowWithOneNeighbour());
        System.out.println("Row with two neighbours: " + rowWithTwoNeighbours());


    }

    public static boolean singleField() {
        testField = new AntField(AntFields.FIELD6);
        // public final int[][] FIELD6 = {{0}};

        neighbours = testField.validMooreNeighbours(0, 0, 1);

        if (neighbours == null) return false;
        if (neighbours.size() == 0) return true;
        else return false;

    }

    public static boolean squareWithThreeNeighbours() {
        testField = new AntField(AntFields.FIELD7);

        neighbours = testField.validMooreNeighbours(0, 0, 1);

        if (neighbours == null) return false;
        else if (neighbours.size() == 3) return true;
        else return false;
    }

    public static boolean rowWithOneNeighbour() {
        testField = new AntField(AntFields.FIELD8);
        // FIELD8 = {{0, 0, 0 }};

        neighbours = testField.validMooreNeighbours(0, 0, 1);

        if (neighbours == null) return false;
        else if (neighbours.size() == 1) return true;
        else return false;
    }

    public static boolean rowWithTwoNeighbours() {
        testField = new AntField(AntFields.FIELD8);
        // FIELD8 = {{0, 0, 0 }};

        neighbours = testField.validMooreNeighbours(1, 0, 1);

        if (neighbours == null) return false;
        else if (neighbours.size() == 2) return true;
        else return false;
    }
}



