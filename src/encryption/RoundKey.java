package encryption;

import state.RoundConstant;
import util.*;
import util.Byte;

public class RoundKey extends RoundConstant implements IRoundKey {

    private int[][] matrixStates;
    private int[][] plainText;
    private int[][] resultEncrypt;

    public RoundKey(final int[][] matrixStates, final int[][] plainText){
        this.matrixStates = matrixStates;
        this.plainText = plainText;
    }

    @Override
    public void execute() {
        this.resultEncrypt = null;
        this.currentRound = 0;

        //Stage1
        int[][] roundBuffer = this.addRoundKey(this.plainText, this.getRound());

        for (this.currentRound = 1; this.currentRound < this.MAX_ROUND; this.currentRound++) {
            //Stage2
            roundBuffer = this.subBytes(roundBuffer);

            //Stage3
            roundBuffer = this.shiftRows(roundBuffer);

            //Stage4
            roundBuffer = this.mixedColumns(roundBuffer);

            //Stage5
            roundBuffer = this.addRoundKey(roundBuffer, this.getRound());
        }

        //Stage6
        roundBuffer = this.subBytes(roundBuffer);
        roundBuffer = this.shiftRows(roundBuffer);

        this.currentRound = 10;
        this.resultEncrypt = this.addRoundKey(roundBuffer, this.getRound());
    }

    private int[][] mixedColumns(int[][] roundBuffer) {
        int size = roundBuffer.length, multiplier, elementShiftRows, result;
        int[][] mixColumns = new int[size][size];
        int[] temp;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                temp = new int[size];
                for (int k = 0; k < MatrixMultiplication.getLengthMatrix(); k++) {
                    multiplier = MatrixMultiplication.getMatrixMultiplicationValue(j, k);
                    elementShiftRows = roundBuffer[k][i];
                    result = 0;

                    if (elementShiftRows == 1) {
                        result = multiplier;

                    } else if (multiplier == 1){
                        result = elementShiftRows;

                    } else if (multiplier != 0 && elementShiftRows != 0) {
                        result = (LBox.getLBoxValue(Byte.mostSignificantBits(elementShiftRows, 4),
                                                    Byte.leastSignificantBits(elementShiftRows))

                                + LBox.getLBoxValue(Byte.mostSignificantBits(multiplier, 4),
                                                    Byte.leastSignificantBits(multiplier)));

                        result = Byte.exceededLimit(result);

                        result = EBox.getEBoxValue( Byte.mostSignificantBits(result, 4),
                                                    Byte.leastSignificantBits(result));
                    }

                    temp[k] = result;
                }

                mixColumns[j][i] = Byte.xor(temp);
            }
        }

        this.printDebug(roundBuffer);

        return mixColumns;
    }

    private int[][] shiftRows(int[][] roundBuffer) {
        int[] shiftBuffer = new int[3];

        for (int i = 1; i < roundBuffer.length; i++) {
            System.arraycopy(roundBuffer[i], 0, shiftBuffer, 0, i);
            System.arraycopy(roundBuffer[i], i, roundBuffer[i], 0, roundBuffer[i].length - i);
            System.arraycopy(shiftBuffer, 0, roundBuffer[i], roundBuffer[i].length - i, i);
        }

        this.printDebug(roundBuffer);

        return roundBuffer;
    }

    private int[][] subBytes(int[][] roundBuffer) {
        for (int i = 0; i < roundBuffer.length; i++) {
            for (int j = 0; j < roundBuffer[i].length; j++) {
                roundBuffer[i][j] = SBox.getSboxValue(  Byte.mostSignificantBits(roundBuffer[i][j], 4),
                                                        Byte.leastSignificantBits(roundBuffer[i][j]));
            }
        }

        this.printDebug(roundBuffer);

        return roundBuffer;
    }

    private int[][] addRoundKey(int[][] compareBuffer, int[][] roundBuffer){
        for (int i = 0; i < roundBuffer.length; i++) {
            for (int j = 0; j < roundBuffer[i].length; j++) {
                roundBuffer[i][j] = compareBuffer[i][j] ^ roundBuffer[i][j];
            }
        }

        this.printDebug(roundBuffer);

        return roundBuffer;
    }

    public int[][] getResultEncrypt() {
        return this.resultEncrypt;
    }

    private int[][] getRound(){
        int size = this.matrixStates.length;
        int[][] roundBuffer = new int[size][size];
        int currentRound = this.currentRound * 4;

        for (int i = 0; i < size; i++) {
            for (int j = 0, r = currentRound; r < currentRound + size; r++, j++) {
                roundBuffer[i][j] = this.matrixStates[i][r];
            }
        }

        return roundBuffer;
    }
}
