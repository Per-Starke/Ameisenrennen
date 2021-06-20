package src;

import java.util.ArrayList;

/**
 * The complete world on which the {@code Ant} instances in an {@code AntRace}
 * operate. Consists of a 2 dimensional Array of {@link Field}
 * elements. May be initialized by an {@code int[][]} which may have columns
 * with different lengths.
 *
 * @author Mathias Menninghaus (mathias.menninghaus@uos.de)
 */
public class AntField {

    public static final int BORDER = -1;

    public static final int FREE = 0;

    private Field[][] fields;

    /**
     * Uses {@code int} values to instantiate a new {@code AntField}. The columns
     * of {@code fields} may have different length. The {@code Field} elements of
     * this {@code AntField} will be set to the value of the {@code fields} array
     * if it is > 0 or {@code FREE}, else the corresponding {@code Field} in this
     * {@code AntField} will be set to {@code null}.
     *
     * @param fields the values of the fields to be created
     * @throws NullPointerException     if {@code fields} is {@code null}
     * @throws IllegalArgumentException if the length of {@code fields} is 0
     */
    public AntField(int[][] fields) {
        this.fields = createFields(fields);
    }

    private Field[][] createFields(int[][] fields) {
        if (fields == null) {
            throw new NullPointerException();
        }
        if (fields.length == 0) {
            throw new IllegalArgumentException("must be of length 1 minimum");
        }
        Field[][] ret = new Field[fields.length][];

        for (int i = 0; i < fields.length; i++) {
            ret[i] = new Field[fields[i].length];

            for (int j = 0; j < fields[i].length; j++) {

                if (fields[i][j] < 0) {
                    ret[i][j] = null;
                } else {
                    ret[i][j] = new Field(fields[i][j]);
                }

            }
        }
        return ret;
    }

    /**
     * Returns the {@code Field} at position {@code x},{@code y} of this
     * {@code AntField}. This will be {@code null} for a field which does not
     * exist or which value has been set < 0 or {@code BORDER} on the
     * instantiation of this {@code AntField}.
     *
     * @param x x-axis value of the position of the returned {@code Field}
     * @param y y-axis value of the position of the returned {@code Field}
     * @return the {@link Field} or {@code null} if {@code x,y} is out of the
     * range of this {@code AntField} or its value has been set to < 0 on
     * instantiation.
     */
    public Field getField(int x, int y) {
        if (x >= this.fields.length || x < 0 || y >= this.fields[x].length
                || y < 0) {
            return null;
        } else
            return this.fields[x][y];
    }

    /**
     * Returns a list of neighbours of the field at {@code x}, {@code y}.
     * A field is a neighbour if it is >= 0.
     * We blindly try all 8 possible fields - using the {@see isInField(newX, newY)} method.
     *
     * @param x x-axis value of
     * @param y
     * @return the moore-neighbours of this field
     */
    public ArrayList<FieldCoordinate> mooreNeighbours(int x, int y) {
        ArrayList<FieldCoordinate> neighboursFound = new ArrayList<>();

        // P is our current positition
        //        0 | 1 |  2
        //  0 |  NW | N | NE
        //  1 |   W | P |  E
        //  2 |  SW | S | SE

        // try NW (x-1, y-1)
        addNeighbourIfValidField(x - 1, y - 1, neighboursFound);
        // try N (x, y-1)
        addNeighbourIfValidField(x, y - 1, neighboursFound);
        // try NE (x+1, y-1)
        addNeighbourIfValidField(x + 1, y - 1, neighboursFound);
        // try W
        addNeighbourIfValidField(x - 1, y, neighboursFound);
        // try E
        addNeighbourIfValidField(x + 1, y, neighboursFound);
        // try SW
        addNeighbourIfValidField(x - 1, y + 1, neighboursFound);
        // try S
        addNeighbourIfValidField(x, y + 1, neighboursFound);
        // try SE
        addNeighbourIfValidField(x + 1, y + 1, neighboursFound);

        return neighboursFound;
    }

    private void addNeighbourIfValidField(int x, int y, ArrayList<FieldCoordinate> neighboursFound) {
        if (getField(x, y) != null)
            neighboursFound.add(new FieldCoordinate(x, y));
    }


    public String toString() {
        StringBuilder build = new StringBuilder();
        int maxlength = fields[0].length;
        for (int i = 1; i < fields.length; i++) {
            if (maxlength < fields[i].length) {
                maxlength = fields[i].length;
            }
        }

        build.append("  |");
        for (int i = 0; i < maxlength; i++) {
            build.append(String.format("%2s ", i));
        }
        build.append("\n");

        build.append("--+");
        for (int i = 0; i < maxlength; i++) {
            build.append("---");
        }
        build.append("\n");

        for (int i = 0; i < fields.length; i++) {

            build.append(String.format("%-2s|", i));

            for (int j = 0; j < maxlength; j++) {
                if (i >= this.fields.length || i < 0 || j >= this.fields[i].length
                        || j < 0) {
                    build.append("##");
                } else {
                    if (fields[i][j] == null) {
                        build.append("##");
                    } else {

                        build.append(String.format("%2d", fields[i][j].getValue()));
                    }
                }
                if (j != maxlength - 1) {
                    build.append(" ");
                }
            }
            build.append("\n");
        }

        return build.toString();
    }

    /**
     * One {@code Field} has a value, which may be
     * <ul>
     * <li>{@code 0}, which means that no {@code Ant} has visited this field yet.
     * <li>greater than {@code 0}, which means, that an {@code Ant} is able to
     * visit this field in that number of steps.
     * </ul>
     * A field with a value < 0 cannot be created.
     */
    public class Field {

        private int value;

        /**
         * @param value the value of the new {@code Field}. Must be >= 0.
         * @throws IllegalArgumentException if {@code} value is lesser than 0
         */
        private Field(int value) {
            if (value < 0) {
                throw new IllegalArgumentException(
                        "cannot create Field with value < 0");
            }
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        /**
         * Sets the new value of this {@code Field}. A fields value can only be
         * set to {@code FREE} on instantiation. It must be set to a value >= 0
         * with this method.
         *
         * @param value the new value, must be >= 0
         * @throws IllegalArgumentException if {@code value} is <= 0
         */
        public void setValue(int value) {
            if (value <= 0) {
                throw new IllegalArgumentException("cannot set value to <= 0");
            }
            if (this.value != FREE && value > this.value) {
                throw new IllegalArgumentException(
                        "cannot only set to a greater value of the value of this Field is set to FREE (value is "
                                + this.value + " and shall be set to " + value + ")");

            }
            this.value = value;
        }
    }
}
