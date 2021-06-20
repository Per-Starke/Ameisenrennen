package src;

public interface AntFields {

    // modifier public is redundant for interface fields
    // modifier final is redundant for interface fields
    int[][] FIELD1 = {{0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, -1, -1, -1, -1, 0}, {0, 0, 0, -1, 0, 0, -1, 0},
            {0, 0, 0, -1, 0, 0, -1, 0}, {0, 0, 0, -1, 0, 0, -1, 0},
            {0, -1, -1, -1, 0, 0, -1, 0}, {0, 0, 0, -1, 0, 0, -1, 0},
            {0, 0, 0, -1, 0, 0, -1, 0}, {0, 0, 0, -1, -1, -1, -1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}};

    public final int[][] FIELD2 = {{0, 0, 0, 0, 0, 0, -1, 0, 0},
            {0, 0, 0, -1, 0, 0, 0, 0, 0}, {0, 0, -1, 0, 0, 0, 0, 0, -1},
            {0, 0, -1, 0, 0, 0, 0, 0, -1}, {0, 0, -1, 0, -1, 0, 0, 0, -1},
            {0, 0, -1, 0, -1, 0, 0, 0, 0}, {0, 0, 0, 0, -1, 0, 0, 0, 0},
            {-1, -1, -1, 0, -1, 0, 0, 0, 0}, {0, 0, -1, 0, 0, 0, 0, 0, -1},
            {0, 0, -1, 0, 0, 0, 0, 0, 0}};

    public final int[][] FIELD3 = {{0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, -1, -1, -1, -1, 0}, {0, 0, 0, -1, 0, 0, -1, 0},
            {0, 0, 0, -1, 0, 0, -1, 0}, {0, 0, 0, -1, 0, 0, -1, 0},
            {0, -1, -1, -1, 0, 0, -1, 0}, {0, 0, 0, -1, 0, 0, -1, 0},
            {0, 0, 0, -1, 0, 0, -1, 0}, {0, 0, 0, -1, -1, -1, -1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, -1, 0, 0, -1, 0},
            {0, 0, 0, -1, 0, 0, -1, 0}, {0, 0, 0, -1, -1, -1, -1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}};

    public final int[][] FIELD4 = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, -1},
            {0, 0, 0, -1, -1, -1, -1, 0, 0, 0, -1, 0, 0, 0, 0},
            {0, 0, 0, -1, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, 0},
            {0, 0, 0, -1, 0, 0, -1, 0, 0, 0, 0, -1, 0, 0, 0},
            {0, 0, 0, -1, 0, 0, -1, 0, 0, 0, 0, 0, 0, -1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1},
            {0, 0, 0, -1, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, 0}};

    public final int[][] FIELD5 = {
            {-1, 0, 0, 0, 0, 0, 0, 0, -1},
            {-1, -1, -1, -1, 0, 0},
            {-1, 0, 0, 0, 0, -1, 0, 0, -1},
            {-1, 0, 0, 0, 0, -1, 0, 0, -1},
            {-1, 0, 0, -1, -1, -1, 0, 0, -1},
            {-1, 0, 0, 0, 0, 0, 0, 0, -1},
            {0, 0, 0, 0}
    };

    public final int[][] FIELD6 = {{0}};

    // for testing the Moore-Neighbourhood

    public final int[][] FIELD7 = {
            {0, 0,},
            {0, 0}};

    public final int[][] FIELD8 = {
            {0, 0, 0 }};

    public final int[][] FIELD9 = {
            {0, 0, 0 },
            {0, 0, 0 },
            {0, 0, 0 }};

    public final int[][] FIELD10 = {
            {0, 0, 0, 0},
            {-1, -1, 0, 0 },
            {0, 0, 0, 0 }};



};

