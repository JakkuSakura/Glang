package top.jackhack.glang.elements;


import java.util.ArrayList;

public class Type extends Identifier {
    private static final String[] TypeSet = new String[]{
            "char", "float", "float64", "int", "int64", "int16"
    };
    private String string;

    @Override
    public int tryMatch(ArrayList<Element> elements, int index) {
        int v = super.tryMatch(elements, index);
        if (v > 0) {
            for (String e : TypeSet) {
                if (e.equals(string)) {
                    return string.length();
                }
            }
        }
        return 0;

    }

    public String getString() {
        return string;
    }

    public Type setString(String string) {
        this.string = string;
        return this;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + string + "]";
    }
}
