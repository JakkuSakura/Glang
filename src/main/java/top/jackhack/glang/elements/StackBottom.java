package top.jackhack.glang.elements;

public class StackBottom extends Operator {
    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }
}
