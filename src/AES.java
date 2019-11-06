import state.MatrixStates;

public class AES {
    public static void main(String[] args) {

        //ABCDEFGHIJKLMNOP
        int[] key = {0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49, 0x4a, 0x4b, 0x4c, 0x4d, 0x4e, 0x4f, 0x50};

        //DESENVOLVIMENTO
        int[][] text = {
                {0x44, 0x4e, 0x56, 0x4e},
                {0x45, 0x56, 0x49, 0x54},
                {0x53, 0x4f, 0x4d, 0x4f},
                {0x45, 0x4c, 0x45, 0x21}
        };



        expansion.RoundKey rk = new expansion.RoundKey(MatrixStates.getInstance().generateStates(key));
        rk.execute();

        encryption.RoundKey erk = new encryption.RoundKey(rk.getKeySchedule(), text);
        erk.execute();

    }
}
