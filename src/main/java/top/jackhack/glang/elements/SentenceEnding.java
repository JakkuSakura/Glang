package top.jackhack.glang.elements;

import java.util.ArrayList;

public class SentenceEnding extends Element {
    @Override
    public int tryMatch(ArrayList<Element> elements, int index) {
        if (elements.get(index) instanceof SourceString && ((SourceString) elements.get(index)).getString().equals(";"))
        {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return ";";
    }
}
