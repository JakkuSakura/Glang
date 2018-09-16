package top.jackhack.glang.elements;

import java.util.ArrayList;

public abstract class TreeLikeElement extends Element {

    public ArrayList<Element> childs = new ArrayList<>();

    @Override
    public String toString() {
        return childs.toString();
    }

    public int size() {
        return childs.size();
    }

    public TreeLikeElement add(Element e) {
        childs.add(e);
        return this;
    }

    public Element get(int index) {
        if (index >= 0)
            return childs.get(index);
        else
            return childs.get(size() - index);
    }
}
