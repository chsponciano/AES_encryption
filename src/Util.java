public abstract class Util {

    protected static byte[] intToByte(Integer[] key){
        byte[] converted = new byte[key.length];

        for (int i = 0; i < key.length; i++) {
            converted[i] = key[i].byteValue();
        }

        return converted;
    }

}
