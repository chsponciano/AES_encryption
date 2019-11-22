package state;

public abstract class RoundConstant {
    private final int[] VALUE_ROUND_CONSTANT = {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1B, 0x36};
    public final int MAX_LINE = 4;
    public final int MAX_COLUMN = 44;
    public final int MAX_ROUND = 10;

    public int currentRound = 0;

    public static boolean debug = false;
    public static final int MAX_BYTE = 16;

    public int[] getValueRoundConstant() {
        return new int[]{VALUE_ROUND_CONSTANT[this.currentRound - 1], 0, 0, 0};
    }

    private String nameCurrentMethod(){
        StackTraceElement[] element = Thread.currentThread().getStackTrace();
        if (element.length < 3) {
            return element[element.length - 1].getMethodName().toUpperCase();
        }

        return Thread.currentThread().getStackTrace()[3].getMethodName().toUpperCase();

    }

    public void printDebug(int[][] matrix){
        if (debug) {
            StringBuilder sb = new StringBuilder("****" + this.nameCurrentMethod() + " - Round " + this.currentRound + "****\n");

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    sb.append("0x" + String.format("%X", matrix[i][j]) + " ");
                }
                sb.append("\n");
            }

            System.out.println(sb.toString());
        }
    }

    public void printDebug(int[] vector){
        if (debug) {
            StringBuilder sb = new StringBuilder("****" + this.nameCurrentMethod() + " - Round " + this.currentRound + "****\n");

            for (int i = 0; i < vector.length; i++) {
                sb.append("0x" + String.format("%X", vector[i]) + " ");
            }

            System.out.println(sb.toString() + "\n");
        }
    }
}
