package top.jackhack.glang.elements;

import java.util.ArrayList;

public class SourceString extends Element {
    private String string;

    @Override
    public int tryMatch(ArrayList<Element> elements, final int index) {
        int new_index = index;
        StringBuilder sb = new StringBuilder();
        Element first = elements.get(index);
        if (first instanceof SpaceChar) {
            ++new_index;
        }
        Element element = elements.get(new_index);
        SourceCharType firstType = ((SourceChar) element).getType();
        char firstChar = ((SourceChar) element).getChar();
        copyPosition(element);
        while (new_index < elements.size() && elements.get(new_index) instanceof SourceChar) {
            element = elements.get(new_index);
            SourceChar charElement = (SourceChar) element;
            if (firstType == SourceCharType.NUMBER) {
                if (charElement.getType() != SourceCharType.NUMBER) {
                    break;
                }
            } else if (firstType == SourceCharType.ALPHA) {
                if (charElement.getType() == SourceCharType.SIGNAL && charElement.getChar() != '_') {
                    break;
                }
            } else if (firstType == SourceCharType.SIGNAL) {
                if (firstChar == '_') {
                    if (charElement.getType() == SourceCharType.SIGNAL && charElement.getChar() != '_') {
                        break;
                    }
                } else {
                    if (charElement.getType() != SourceCharType.SIGNAL) {
                        break;
                    }
                }
            }
            sb.append(charElement.getChar());
            ++new_index;
        }
        setString(sb.toString());
        return new_index - index;

    }

    public String getString() {
        return string;
    }

    public SourceString setString(String string) {
        this.string = string;
        return this;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + string + "]";
    }
}
