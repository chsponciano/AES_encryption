package expansion;

import util.SBox;

public class RoundKey extends RoundConstant{

    private final int MAX_LINE = 4;
    private final int MAX_COLUMN = 44;
    private final int MAX_ROUND = 10;

    private int[][] keySchedule;

    public RoundKey(final int[][] matrixStates){
        this.keyScheduleStartup(matrixStates);
    }

    public void execute(){
        int[] copyRound, auxRound, auxConstant;
        int lastModified;

        for (int currentRound = 1; currentRound <= MAX_ROUND; currentRound++) {
            for (int word = 0; word < MAX_LINE; word++) {
                lastModified = ((currentRound * MAX_LINE) - 1) + word;

                if (word == 0) {

                    //Stage1
                    copyRound = this.lastWordCopy(lastModified);

                    //Stage2
                    auxRound = this.rotWord(copyRound);

                    //Stage3
                    auxRound = this.subWord(auxRound);

                    //Stage4
                    auxConstant = this.getValueRoundConstant(currentRound);

                    //Stage5
                    auxRound = this.applyXorInRoundConstant(auxRound, auxConstant);

                } else {
                    auxRound = this.lastWordCopy(lastModified);
                }

                //Stage6
                auxRound = this.applyXorInLastToResult(auxRound, currentRound, word);

                updateKeySchedule(lastModified + 1, auxRound);
            }

            System.out.println(this.toString());
        }
    }
    
    private void keyScheduleStartup(final int[][] matrixStates){
        this.keySchedule = new int[MAX_LINE][MAX_COLUMN];

        for (int line = 0; line < matrixStates.length; line++) {
            for (int column = 0; column < matrixStates[line].length; column++) {
                this.keySchedule[line][column] = matrixStates[line][column];
            }
        }
    }

    private void updateKeySchedule(final int column, final int[] auxRound){
        for (int line = 0; line < auxRound.length; line++) {
            this.keySchedule[line][column] = auxRound[line];
        }
    }

    private int[] lastWordCopy(final int lastResultRound){
        int[] copyRound = new int[MAX_LINE];

        for (int idx = 0; idx < MAX_LINE; idx++) {
            copyRound[idx] = this.keySchedule[idx][lastResultRound];
        }

        return copyRound;
    }

    private int[] rotWord(final int[] copyRound){
        int len = copyRound.length;

        int[] rotate = new int[len];
        rotate[len - 1] = copyRound[0];

        for (int idx = 0; idx < len - 1; idx++) {
            rotate[idx] = copyRound[idx + 1];
        }

        return rotate;
    }

    private int[] subWord(int[] auxRound){
        for (int idx = 0; idx < auxRound.length; idx++) {
            auxRound[idx] = SBox.getSboxValue((auxRound[idx] & 0xf0) >> 4, (auxRound[idx] & 0x0f));
        }

        return auxRound;
    }

    private int[] applyXorInRoundConstant(int[] auxRound, final int[] auxConstant){
        for (int idx = 0; idx < auxRound.length; idx++) {
            auxRound[idx] = auxRound[idx] ^ auxConstant[idx];
        }

        return auxRound;
    }

    private int[] applyXorInLastToResult(int[] auxRound, int currentRound, int word){
        currentRound = (currentRound == 1) ? currentRound + word - 1 : (currentRound * 4) - 4 + word;

        for (int idx = 0; idx < auxRound.length; idx++) {
            auxRound[idx] = this.keySchedule[idx][currentRound] ^ auxRound[idx];
        }

        return auxRound;
    }

    @Override
    public String toString() {
        String out = "";
        for (int line = 0; line < this.keySchedule.length; line++) {
            out += "[";
            for (int column = 0; column < this.keySchedule[line].length; column++) {
                out += " 0x" + String.format("%X", this.keySchedule[line][column]) + " ";
            }
            out += "]\n";
        }

        return out;
    }
}
