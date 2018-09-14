package top.jackhack.glang.elements;

import java.util.ArrayList;

public abstract class TreeLikeElement extends Element {

    public ArrayList<Element> childs = new ArrayList<>();

    @Override
    public String toString() {
        return childs.toString();
    }
}
