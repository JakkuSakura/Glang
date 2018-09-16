package top.jackhack.glang;

import top.jackhack.glang.elements.Element;
import top.jackhack.glang.elements.Operator;

public class Utils {

    public static boolean isStr(Element child, String str) {
        return child instanceof Operator && ((Operator) child).getString().equals(str);
    }
}
