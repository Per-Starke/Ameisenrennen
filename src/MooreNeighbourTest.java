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
        System.out.println("Rect with 8 neighbours: " + rectWith8Neighbours());
        System.out.println("Rect with 3 neighbours: " + rectWithFence3Neighbours());
        System.out.println("Rect with 2 Fence: " + rectWithFence2Neighbours());
        System.out.println("Rect with 1 Fence: " + rectWithFence1Neighbour());


    }

    public static boolean singleField() {
        testField = new AntField(AntFields.FIELD6);
        // public final int[][] FIELD6 = {{0}};

        neighbours = testField.mooreNeighbours(0, 0);

        if (neighbours == null) return false;
        if (neighbours.size() == 0) return true;
        else return false;

    }

    public static boolean squareWithThreeNeighbours() {
        testField = new AntField(AntFields.FIELD7);

        neighbours = testField.mooreNeighbours(0, 0);

        if (neighbours == null) return false;
        else if (neighbours.size() == 3) return true;
        else return false;
    }

    public static boolean rowWithOneNeighbour() {
        testField = new AntField(AntFields.FIELD8);
        // FIELD8 = {{0, 0, 0 }};

        neighbours = testField.mooreNeighbours(0, 0);

        if (neighbours == null) return false;
        else if (neighbours.size() == 1) return true;
        else return false;
    }

    public static boolean rowWithTwoNeighbours() {
        testField = new AntField(AntFields.FIELD8);
        // FIELD8 = {{0, 0, 0 }};

        neighbours = testField.mooreNeighbours(1, 0);

        if (neighbours == null) return false;
        else if (neighbours.size() == 2) return true;
        else return false;
    }

    ;

    public static boolean rectWith8Neighbours() {
        testField = new AntField(AntFields.FIELD10);

        neighbours = testField.mooreNeighbours(1, 1);

        if (neighbours == null) return false;
        else if (neighbours.size() == 8) return true;
        else return false;
    }

    public static boolean rectWithFence3Neighbours() {
        testField = new AntField(AntFields.FIELD11);

        neighbours = testField.mooreNeighbours(0, 1);

        printFieldAndNeighbours(0, 1);

        if (neighbours == null) return false;
        else if (neighbours.size() == 3) return true;
        else return false;
    }

    public static boolean rectWithFence2Neighbours() {
        testField = new AntField(new int[][]{
                {1, 0, 4, 0},
                {-1, -1, 0, 0},
                {0, 0, 0, 0}});
        neighbours = testField.mooreNeighbours(0, 1);

        printFieldAndNeighbours(0, 1);
        if (neighbours == null) return false;
        else if (neighbours.size() == 3) return true;
        else return false;
    }

    public static boolean rectWithFence1Neighbour() {
        testField = new AntField(new int[][]{
                {1, 2, 3, 4},
                {-1, -1, 3, 4},
                {0, 4, 4, 0}});
        neighbours = testField.mooreNeighbours(1, 3);

        printFieldAndNeighbours(1, 3);
        if (neighbours == null) return false;
        else if (neighbours.size() == 5) return true;
        else return false;
    }

    public static void printFieldAndNeighbours(int x, int y) {
        System.out.println(testField);
        System.out.format("%d:%d -> %s\n", x, y, neighbours);
    }
}



