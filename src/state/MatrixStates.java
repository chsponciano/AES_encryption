package state;

public class MatrixStates {
    private static MatrixStates instance;
    int[][] matrixStates;

    private MatrixStates(){
        this.matrixStates = new int[4][4];
    }

    public static MatrixStates getInstance() {
        if (instance == null) {
            instance = new MatrixStates();
        }
        return instance;
    }

    public int[][] generateStates(final int[] content){
        int line = 0, column = 0;

        for (int idx = 0; idx < content.length; idx++) {

            if (line == 3){
                column++;
            }

            line = idx % 4;

            this.matrixStates[line][column] = content[idx];
        }

        return this.matrixStates;
    }
}
