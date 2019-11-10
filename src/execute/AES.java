package execute;

import state.MatrixStates;
import state.RoundConstant;
import util.Byte;

public class AES {
    private final int[] content;
    private final int[] key;

    public AES(final byte[] content, final String key, boolean debug){
        this.content = formatText(content);
        this.key = formatKey(key);
        RoundConstant.debug = debug;
    }

    private int[] formatKey(String keyStr){
        int[] keyInt = new int[16];
        String[] strBuffer = keyStr.split(",");

        for (int i = 0; i < strBuffer.length; i++) {
            keyInt[i] = new Integer(strBuffer[i]).byteValue();
        }

        return keyInt;
    }

    private int[] formatText(byte[] textInByte){
        int[] textInt = new int[textInByte.length];

        for (int i = 0; i < textInByte.length; i++) {
            textInt[i] = Byte.byteToInteger(textInByte[i]);
        }

        return textInt;
    }

    public byte[] execute() {
        try {
            expansion.RoundKey rk = new expansion.RoundKey(MatrixStates.getInstance().generateStates(this.key));
            rk.execute();

            encryption.RoundKey erk = new encryption.RoundKey(rk.getKeySchedule(), MatrixStates.getInstance().generateStates(this.content));
            erk.execute();

            return erk.getResultEncrypt();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
