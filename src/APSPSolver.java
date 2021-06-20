/**
 * solution to the _all pairs shortest path_ problem,
 * using concurrent ants.
 *
 * The _standard_ APSP problem is formulated on weighted graphs instead of fields.
 *
 * Remark: The term {@code Ant} is overloaded in Javaland, due to the old
 * build tool @see <a href="https://ant.apache.org/">ant</a> of this name.
*
 *
 */
public class APSPSolver {

    public static void main(String[] args) {

        printAllFields();
    }


    static void printAllFields() {

        System.out.println(  "FIELD-1\n" + new AntField( AntFields.FIELD1));
        System.out.println("\n\n");
        System.out.println(  "FIELD-2\n" + new AntField( AntFields.FIELD2));
        System.out.println("\n\n");
        System.out.println(  "FIELD-3\n" + new AntField( AntFields.FIELD3));
        System.out.println("\n\n");
        System.out.println(  "FIELD-4\n" + new AntField( AntFields.FIELD4));
        System.out.println("\n\n");
        System.out.println(  "FIELD-5\n" + new AntField( AntFields.FIELD5));
        System.out.println("\n\n");
        System.out.println(  "FIELD-6\n" + new AntField( AntFields.FIELD6));
        System.out.println("\n\n");
    }
}
