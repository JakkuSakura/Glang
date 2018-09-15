package top.jackhack.glang.elements;

import java.util.ArrayList;

public class ConstInteger extends Element {
    public int i;

    @Override
    public int tryMatch(ArrayList<Element> elements, int index) {
        Element e;
        StringBuilder sb = new StringBuilder();
        while (index < elements.size() && (e = elements.get(index)) instanceof SourceChar && ((SourceChar) e).getType() == SourceCharType.NUMBER) {
            sb.append(((SourceChar) e).getChar());
            ++index;
        }
        if (sb.length() > 0) {
            i = Integer.parseInt(sb.toString());
            return sb.length();
        }
        return 0;
    }

    @Override
    public String toString() {
        return String.valueOf(i);
    }
}
