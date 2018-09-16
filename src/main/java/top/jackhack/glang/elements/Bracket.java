package top.jackhack.glang.elements;

import java.util.ArrayList;

public class Bracket extends Brackets{
    @Override
    public int tryMatch(ArrayList<Element> elements, int index) {

        return matchBrakets(elements, index, "{", "}");
    }

}

