package GameUtils;

public enum BoardMarks {
    MARK_EMPTY(0),
    MARK_X(1),
    MARK_O(2);

    public final int value;

    public int getValue() { return value; };

    private BoardMarks(int value) {
        this.value = value;
    }
}