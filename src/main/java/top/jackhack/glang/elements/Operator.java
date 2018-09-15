package top.jackhack.glang.elements;

import com.sun.org.apache.bcel.internal.generic.SIPUSH;
import top.jackhack.glang.SignalSet;

import java.util.ArrayList;

public class Operator extends Element {
    private String string;

    @Override
    public int tryMatch(ArrayList<Element> elements, int index) {
        Element e;
        StringBuilder sb = new StringBuilder();
        while (index < elements.size()
                && (e = elements.get(index)) instanceof SourceChar
                && ((SourceChar) e).getType() == SourceCharType.SIGNAL) {
            sb.append(((SourceChar) e).getChar());
            ++index;
            if (SignalSet.isOperator(sb.toString())) {
                setString(sb.toString());
                return sb.length();
            }
        }
        return 0;
    }

    public String getString() {
        return string;
    }

    public Operator setString(String string) {
        this.string = string;
        return this;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + string + "]";
    }
}
