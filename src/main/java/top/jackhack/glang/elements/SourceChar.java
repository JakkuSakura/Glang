package top.jackhack.glang.elements;

import top.jackhack.glang.SignalSet;

import java.util.ArrayList;

public class SourceChar extends Element
{
    private char c;
    private SourceCharType type;
    public static boolean isPrint(char ch) {
        return Character.isDigit(ch) || Character.isAlphabetic(ch) || SignalSet.isSignal(ch);
    }
    public SourceChar setChar(char ch)
    {
        c = ch;
        type = SourceCharType.getType(ch);
        return this;
    }

    public SourceCharType getType() {
        return type;
    }
    @Override
    public int tryMatch(ArrayList<Element> elements, int index) {
        return 0;
    }

    public char getChar() {
        return c;
    }

    /**
     * Your cannot use this because it will be automatically updated when you call setChar.
     * @param type
     */
    @Deprecated
    public void setType(SourceCharType type) {
        this.type = type;
    }
}
