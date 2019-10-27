import org.apache.commons.codec.binary.Hex;

import java.util.LinkedList;
import java.util.Queue;

public class AES implements IAES{
    private char[][] matrixStates;

    public AES(Integer[] key){
        this.generateStates(Util.intToByte(key));
        this.showMatrixStates();
    }

    private void showMatrixStates() {
        for (int i = 0; i < matrixStates.length; i++) {
            for (int j = 0; j < matrixStates[i].length; j++) {
                System.out.print(matrixStates[i][j] + " - ");
            }
            System.out.println();
        }
    }

    private void generateStates(byte[] key) {
        this.matrixStates = new char[4][4];

        Queue<Character> hex = this.generateQueueHex(key);

        for (int i = 0; i < this.matrixStates.length; i++) {
            for (int j = 0; j < this.matrixStates.length; j++) {
                this.matrixStates[i][j] = hex.poll();
            }
        }

    }

    private Queue<Character> generateQueueHex(byte[] key){
        char[] hex = Hex.encodeHex(key);

        for (int i = 0; i < hex.length; i++) {
            System.out.println(hex[i]);
        }

        Queue<Character> qHex = new LinkedList<>();

        for (int i = 0; i < hex.length; i++) {
            qHex.add(hex[i]);
        }

        return qHex;
    }
}
