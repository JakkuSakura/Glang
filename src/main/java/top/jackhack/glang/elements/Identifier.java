package top.jackhack.glang.elements;

import java.util.ArrayList;

public class Identifier extends SourceString {
    @Override
    public int tryMatch(ArrayList<Element> elements, int index) {
        final Element element = elements.get(index);
        if (element instanceof SourceString && SourceCharType.getType(((SourceString) element).getString().charAt(0)) != SourceCharType.NUMBER)
        {
            copyPosition(element);
            setString(((SourceString) element).getString());
            return 1;
        }
        return 0;
    }
}
