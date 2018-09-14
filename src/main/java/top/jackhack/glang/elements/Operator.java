package top.jackhack.glang.elements;

import top.jackhack.glang.SignalSet;

import java.util.ArrayList;

public class Operator extends SourceString {
    @Override
    public int tryMatch(ArrayList<Element> elements, int index) {
        Element element = elements.get(index);
        if (element instanceof SourceString)
        {
            String str = ((SourceString) elements.get(index)).getString();
            if (SignalSet.isOperator(str)) {

                setString(str);
                return 1;
            }
        }
        return 0;
    }
}
