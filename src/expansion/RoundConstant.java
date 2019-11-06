package expansion;

public abstract class RoundConstant {
    private final int[] VALUE_ROUND_CONSTANT = {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1B, 0x36};

    public final int MAX_LINE = 4;
    public final int MAX_COLUMN = 44;
    public final int MAX_ROUND = 10;

    public int[] getValueRoundConstant(final int currentRound) {
        return new int[]{VALUE_ROUND_CONSTANT[currentRound - 1], 0, 0, 0};
    }
}
