package top.jackhack.glang.elements;

import top.jackhack.glang.elements.Element;

import java.util.ArrayList;

public class Root extends TreeLikeElement {

    @Override
    public int tryMatch(ArrayList<Element> elements, int index) {
        return elements.size();
    }
}
