package top.jackhack.glang.elements;

import java.util.ArrayList;

public abstract class Element {

    public int getRow() {
        return row;
    }

    public Element setRow(int row) {
        this.row = row;
        return this;
    }

    public int row;
    public int col;

    public int getCol() {
        return col;
    }

    public Element setCol(int col) {
        this.col = col;
        return this;
    }

    public Element copyPosition(Element e) {
        setCol(e.getCol());
        setRow(e.getRow());
        return this;
    }

    /**
     * @param elements the source elements
     * @param index the beginning of precessing
     * @return the length of element
     */
    public abstract int tryMatch(ArrayList<Element> elements, int index);
}

