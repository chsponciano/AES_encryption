package encryption;

import expansion.RoundConstant;
import util.IRoundKey;
import util.SBox;

public class RoundKey extends RoundConstant implements IRoundKey {

    private int[][] matrixStates;
    private int[][] plainText;

    public RoundKey(final int[][] matrixStates, final int[][] plainText){
        this.matrixStates = matrixStates;
        this.plainText = plainText;
    }

    @Override
    public void execute() {
        //Stage1
        int[][] auxRound = this.addRoundKey(this.plainText, this.getRound(0));

        for (int currentRound = 1; currentRound < MAX_ROUND; currentRound++) {
            //Stage2
            auxRound = this.subBytes(auxRound);

            //Stage3
            auxRound = this.shiftRows(auxRound);

            //Stage4
            auxRound = this.mixColumns(auxRound);

            //Stage5
            auxRound = this.addRoundKey(auxRound, this.getRound(currentRound));
        }

        //Stage6
        auxRound = this.subBytes(auxRound);
        auxRound = this.shiftRows(auxRound);
        auxRound = this.addRoundKey(auxRound, this.getRound(10));


        printRound(auxRound);
    }

    private int[][] mixColumns(int[][] auxRound) {
        return null;
    }

    private int[][] shiftRows(int[][] auxRound) {
        int[] auxShift = new int[3];
        int len = auxRound.length;

        //line1 - 1 2 3 0
        auxShift[0] = auxRound[1][0];
        for (int i = 0; i < len - 1; i++) {
            auxRound[1][i] = auxRound[1][i + 1];
        }
        auxRound[1][len - 1] = auxShift[0];

        //line2 - 2 3 0 1
        auxShift[0] = auxRound[2][0];
        auxShift[1] = auxRound[2][1];
        for (int i = 0; i < len - 2; i++) {
            auxRound[2][i] = auxRound[1][i + 1];
        }
        auxRound[2][len - 2] = auxShift[0];
        auxRound[2][len - 1] = auxShift[1];

        //line3 - 3 0 1 2
        for (int i = 0; i < len - 1; i++) {
            auxShift[i] = auxRound[3][i];
        }
        auxRound[3][0] = auxRound[3][len - 1];
        for (int i = 0; i < len - 1; i++) {
            auxRound[3][i + 1] = auxShift[i];
        }

        return auxRound;
    }

    private int[][] subBytes(int[][] auxRound) {
        for (int i = 0; i < auxRound.length; i++) {
            for (int j = 0; j < auxRound[i].length; j++) {
                auxRound[i][j] = SBox.getSboxValue((auxRound[i][j] & 0xf0) >> 4, (auxRound[i][j] & 0x0f));
            }
        }

        return auxRound;
    }

    private void printRound(int[][] auxRound){
        for (int i = 0; i < auxRound.length; i++) {
            for (int j = 0; j < auxRound[i].length; j++) {
                System.out.print("0x" + String.format("%X", auxRound[i][j]) + " ");
            }
            System.out.println();
        }
    }

    private int[][] getRound(int round){
        int[][] auxRound = new int[4][4];
        round *= 4;

        for (int r = round, i = 0; r < round + 4; r++, i++) {
            for (int j = 0; j < 4; j++) {
                auxRound[i][j] = this.matrixStates[r][j];
            }
        }

        return auxRound;
    }

    private int[][] addRoundKey(int[][] compare, int[][] round){
        for (int i = 0; i < round.length; i++) {
            for (int j = 0; j < round[i].length; j++) {
                round[i][j] = compare[i][j] ^ round[i][j];
            }
        }

        return round;
    }
}
