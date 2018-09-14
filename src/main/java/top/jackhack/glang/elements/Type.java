package top.jackhack.glang.elements;


import java.util.ArrayList;

public class Type extends SourceString {
    private static final String[] TypeSet = {
            "char", "float", "float64", "int", "int64", "int16"
    };

    @Override
    public int tryMatch(ArrayList<Element> elements, int index) {
        if (!(elements.get(index) instanceof SourceString)) return 0;

        SourceString stringElement = (SourceString) elements.get(index);
        for (String e : TypeSet)
        {
            if (stringElement.getString().equals(e)) {
                copyPosition(stringElement);
                setString(e);
                return 1;
            }
        }
        return 0;

    }
}
