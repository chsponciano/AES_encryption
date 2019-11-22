package util;

import state.RoundConstant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class Byte {

    private static final int MAX_BIT = 0xff;

    public static int mostSignificantBits(final int bytes, final int priority) {
        return ((bytes & 0xf0) >> priority);
    }

    public static int leastSignificantBits(final int bytes) {
        return (bytes & 0x0f);
    }

    public static int xor(final int... elementBuffer){
        Integer xorBuffer = null;
        for (int e : elementBuffer){
            if (xorBuffer == null) {
                xorBuffer = new Integer(e);
            } else {
                xorBuffer ^= e;
            }
        }
        return xorBuffer;
    }

    public static int exceededLimit(int bit){
        return (bit > MAX_BIT) ? bit - MAX_BIT : bit;
    }

    public static int byteToInteger(byte b){
        return new java.lang.Byte(b).intValue();
    }

    private static byte[] pcks5(byte[] b){
        int size = b.length;
        Integer bufferRest = size % RoundConstant.MAX_BYTE;

        if (bufferRest != 0) {
            bufferRest = RoundConstant.MAX_BYTE - bufferRest;

            byte[] buffer = new byte[size + bufferRest];
            for (int i = 0; i < size; i++) {
                buffer[i] = b[i];
            }

            for (int i = size; i < buffer.length; i++) {
                buffer[i] = bufferRest.byteValue();
            }

            return buffer;

        }

        return b;
    }

    public static byte[] addBytesToList(final byte[] newMembers, final byte[] currentMembers) {
        byte[] newListByte = new byte[currentMembers.length + newMembers.length];
        System.arraycopy(currentMembers, 0, newListByte, 0, currentMembers.length);
        System.arraycopy(newMembers, 0, newListByte, currentMembers.length, newMembers.length);
        return newListByte;
    }

    public static byte[] streamToByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[0xffff];

        for (int len = is.read(buffer); len != -1; len = is.read(buffer)) {
            os.write(buffer, 0, len);
        }

        return pcks5(os.toByteArray());
    }
}
