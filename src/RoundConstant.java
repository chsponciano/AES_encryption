public abstract class RoundConstant {
    private final int[] VALUE_ROUND_CONSTANT = {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1B, 0x36};

    public int[] getValueRoundConstant(final int currentRound) {
        return new int[]{VALUE_ROUND_CONSTANT[currentRound - 1], 0, 0, 0};
    }
}
