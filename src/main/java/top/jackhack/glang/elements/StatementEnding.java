package top.jackhack.glang.elements;

import java.util.ArrayList;

public class StatementEnding extends Element {
    @Override
    public int tryMatch(ArrayList<Element> elements, int index) {
        final Element e = elements.get(index);
        if (e instanceof SourceChar && ((SourceChar) e).getChar() == ';')
        {
            copyPosition(e);
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return ";";
    }
}
