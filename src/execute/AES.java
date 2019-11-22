package execute;

import state.Block;
import state.MatrixStates;
import state.RoundConstant;
import util.Byte;
import java.util.ArrayList;
import java.util.List;

public class AES {
    private final List<Block> content;
    private final int[] key;

    public AES(final byte[] content, final String key, boolean debug){
        this.content = this.createContent(content);
        this.key = this.createKey(key);
        RoundConstant.debug = debug;
    }

    private int[] createKey(String keyStr){
        int[] keyInt = new int[RoundConstant.MAX_BYTE];
        String[] strBuffer = keyStr.split(",");

        for (int i = 0; i < strBuffer.length; i++) {
            keyInt[i] = new Integer(strBuffer[i]).byteValue();
        }

        return keyInt;
    }

    private List<Block> createContent(byte[] content){
        List<Block> blocks = new ArrayList<>();
        int[] temp;

        for (int i = 0; i < content.length; i += RoundConstant.MAX_BYTE) {
            temp = new int[RoundConstant.MAX_BYTE];
            for (int j = 0; j < RoundConstant.MAX_BYTE; j++) {
                temp[j] = Byte.byteToInteger(content[i + j]);
            }

            blocks.add(new Block(new MatrixStates().generateStates(temp)));
        }

        return blocks;
    }

    public List<Block> execute() {
        try {
            expansion.RoundKey expansion = new expansion.RoundKey(new MatrixStates().generateStates(this.key));
            expansion.execute();

            encryption.RoundKey encryption;
            for (Block block : this.content) {
                encryption = new encryption.RoundKey(expansion.getKeySchedule(), block.getMatrixStates());
                encryption.execute();
                block.setMatrixStates(encryption.getResultEncrypt());
            }

            return this.content;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
