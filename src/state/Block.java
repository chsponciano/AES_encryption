package state;

public class Block {
    private int[][] matrixStates;

    public Block(final int[][] matrixStates) {
        this.setMatrixStates(matrixStates);
    }

    public int[][] getMatrixStates() {
        return this.matrixStates;
    }

    public byte[] convertStateToByte() {
        int size = this.matrixStates.length;
        int counterBuffer = 0;
        byte[] buffer = new byte[size * size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++, counterBuffer++) {
                buffer[counterBuffer] = new Integer(this.matrixStates[i][j]).byteValue();
            }
        }

        return buffer;
    }

    public void setMatrixStates(final int[][] matrixStates) {
        this.matrixStates = matrixStates;
    }


}
