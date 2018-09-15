package top.jackhack.glang.elements;

import top.jackhack.glang.Utils;

import java.util.ArrayList;

public class Identifier extends Element {
    private String string;

    @Override
    public int tryMatch(ArrayList<Element> elements, int index) {
        StringBuilder sb = new StringBuilder();
        Element element;
        while (index < elements.size()
                && (element = elements.get(index)) instanceof SourceChar
                && (((SourceChar) element).getType() == SourceCharType.ALPHA
                || ((SourceChar) element).getType() == SourceCharType.NUMBER
                || ((SourceChar) element).getChar() == '_')) {

            SourceChar charElement = (SourceChar) element;

            sb.append(charElement.getChar());
            ++index;
        }
        setString(sb.toString());
        return sb.length();
    }

    public String getString() {
        return string;
    }

    public Identifier setString(String string) {
        this.string = string;
        return this;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + string + "]";
    }
}
