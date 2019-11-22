package state;

public class Block {
    private MatrixStates matrixStates;

    public Block(MatrixStates matrixStates) {
        this.setMatrixStates(matrixStates);
    }

    public int[][] getMatrixStates() {
        return this.matrixStates.getContent();
    }

    public byte[] convertStateToByte() {
        int size = this.matrixStates.getContent().length;
        int counterBuffer = 0;
        byte[] buffer = new byte[size * size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++, counterBuffer++) {
                buffer[counterBuffer] = new Integer(this.matrixStates.getContent()[i][j]).byteValue();
            }
        }

        return buffer;
    }

    public void setMatrixStates(MatrixStates matrixStates) {
        this.matrixStates = matrixStates;
    }


}
