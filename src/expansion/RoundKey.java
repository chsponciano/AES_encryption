package expansion;

import state.RoundConstant;
import util.Byte;
import util.IRoundKey;
import util.SBox;

public class RoundKey extends RoundConstant implements IRoundKey {
    private int[][] keySchedule;

    public RoundKey(final int[][] matrixStates){
        this.keyScheduleStartup(matrixStates);
    }

    @Override
    public void execute(){
        int[] copyRound, roundBuffer, constantBuffer;
        int lastModified;

        for (this.currentRound = 1; this.currentRound <= this.MAX_ROUND; this.currentRound++) {
            for (int word = 0; word < this.MAX_LINE; word++) {
                lastModified = ((this.currentRound * this.MAX_LINE) - 1) + word;

                if (word == 0) {
                    //Stage1
                    copyRound = this.lastWordCopy(lastModified);

                    //Stage2
                    roundBuffer = this.rotWord(copyRound);

                    //Stage3
                    roundBuffer = this.subWord(roundBuffer);

                    //Stage4
                    constantBuffer = this.getValueRoundConstant();

                    //Stage5
                    roundBuffer = this.applyXorInRoundConstant(roundBuffer, constantBuffer);

                } else {
                    roundBuffer = this.lastWordCopy(lastModified);
                }

                //Stage6
                roundBuffer = this.applyXorInLastToResult(roundBuffer, word);

                updateKeySchedule(lastModified + 1, roundBuffer);
            }
        }
    }
    
    private void keyScheduleStartup(final int[][] matrixStates){
        this.keySchedule = new int[this.MAX_LINE][this.MAX_COLUMN];

        for (int line = 0; line < matrixStates.length; line++) {
            for (int column = 0; column < matrixStates[line].length; column++) {
                this.keySchedule[line][column] = matrixStates[line][column];
            }
        }
    }

    private void updateKeySchedule(final int column, final int[] roundBuffer){
        for (int line = 0; line < roundBuffer.length; line++) {
            this.keySchedule[line][column] = roundBuffer[line];
        }
    }

    private int[] lastWordCopy(final int lastResultRound){
        int[] copyRound = new int[this.MAX_LINE];

        for (int idx = 0; idx < this.MAX_LINE; idx++) {
            copyRound[idx] = this.keySchedule[idx][lastResultRound];
        }

        this.printDebug(copyRound);

        return copyRound;
    }

    private int[] rotWord(final int[] copyRound){
        int len = copyRound.length;

        int[] rotate = new int[len];
        rotate[len - 1] = copyRound[0];

        for (int idx = 0; idx < len - 1; idx++) {
            rotate[idx] = copyRound[idx + 1];
        }

        this.printDebug(rotate);

        return rotate;
    }

    private int[] subWord(int[] roundBuffer){
        for (int idx = 0; idx < roundBuffer.length; idx++) {
            roundBuffer[idx] = SBox.getSboxValue(   Byte.mostSignificantBits(roundBuffer[idx], 4),
                                                    Byte.leastSignificantBits(roundBuffer[idx]));
        }

        this.printDebug(roundBuffer);

        return roundBuffer;
    }

    private int[] applyXorInRoundConstant(int[] roundBuffer, final int[] constantBuffer){
        for (int idx = 0; idx < roundBuffer.length; idx++) {
            roundBuffer[idx] = Byte.xor(roundBuffer[idx], constantBuffer[idx]);
        }

        this.printDebug(roundBuffer);

        return roundBuffer;
    }

    private int[] applyXorInLastToResult(int[] roundBuffer, int word){
        int currentRound = (this.currentRound == 1) ? this.currentRound + word - 1 : (this.currentRound * 4) - 4 + word;

        for (int idx = 0; idx < roundBuffer.length; idx++) {
            roundBuffer[idx] = Byte.xor(this.keySchedule[idx][currentRound], roundBuffer[idx]);
        }

        this.printDebug(roundBuffer);

        return roundBuffer;
    }

    public int[][] getKeySchedule() {
        return keySchedule;
    }

}
