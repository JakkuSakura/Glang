package top.jackhack.glang.elements;

import java.util.ArrayList;

public class ConstInteger extends Element
{
    public int i;

    @Override
    public int tryMatch(ArrayList<Element> elements, int index) {
        final Element e = elements.get(index);
        if (e instanceof SourceString) {
            i = Integer.parseInt(((SourceString) e).getString());
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return String.valueOf(i);
    }
}
