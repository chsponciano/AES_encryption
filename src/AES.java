import expansion.RoundKey;
import state.MatrixStates;

public class AES {
    public static void main(String[] args) {
        int[] key = {0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49, 0x4a, 0x4b, 0x4c, 0x4d, 0x4e, 0x4f, 0x50};
        RoundKey rk = new RoundKey(MatrixStates.getInstance().generateStates(key));
        rk.execute();
    }
}