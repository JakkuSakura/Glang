package top.jackhack.glang.elements;

import top.jackhack.glang.Utils;

import java.util.ArrayList;

public class Parentheses extends Brackets {
    @Override
    public int tryMatch(ArrayList<Element> elements, int index) {

        return matchBrakets(elements, index, "(", ")");
    }

}
