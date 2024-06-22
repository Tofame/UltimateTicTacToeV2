package Utils;

public class MyPair<F, S> {
    private F value1;
    private S value2;

    public MyPair(F v1, S v2) {
        this.value1 = v1;
        this.value2 = v2;
    }

    public F getFirst() {
        return value1;
    }

    public S getSecond() {
        return value2;
    }
}