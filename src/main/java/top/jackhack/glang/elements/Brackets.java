package top.jackhack.glang.elements;

import top.jackhack.glang.CannotMatchException;

import java.util.ArrayList;

abstract public class Brackets extends Operator {
    private boolean left;

    public boolean isLeft() {
        return left;
    }

    public int matchBrakets(ArrayList<Element> elements, int index, String lft, String rht) {
        final Element element = elements.get(index);
        if (element instanceof Operator)
        {
            final String s = ((Operator) element).getString();
            if (s.equals(lft)) {
                copyPosition(element);
                setLeft(true);
                return 1;
            }
            if (s.equals(rht)) {
                copyPosition(element);
                setLeft(false);
                return 1;
            }

        }
        return 0;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public String toString() {
        return childs.toString();
    }
}
