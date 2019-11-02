public class AES {
    private int[][] matrixStates;

    public AES(){
        this.matrixStates = new int[4][4];
    }

    public void generateStates(final int[] key){
        int line = 0, column = 0;

        for (int idx = 0; idx < key.length; idx++) {
            if (line == 3){
                column++;
            }

            line = idx % 4;

            this.matrixStates[line][column] = key[idx];
        }
    }

    public int[][] getMatrixStates() {
        return this.matrixStates;
    }
}
