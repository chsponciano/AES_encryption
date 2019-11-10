package util;

public abstract class MatrixMultiplication {
    private static final int[][] matrix = {
            {0x02, 0x03, 0x01, 0x01},
            {0x01, 0x02, 0x03, 0x01},
            {0x01, 0x01, 0x02, 0x03},
            {0x03, 0x01, 0x01, 0x02}
    };

    public static int getLengthMatrix(){
        return matrix.length;
    }

    public static int getMatrixMultiplicationValue(int line, int column) {
        return matrix[line][column];
    }
}
