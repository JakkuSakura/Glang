package top.jackhack.glang.elements;

import java.util.ArrayList;

public class Sentence extends TreeLikeElement {
    @Override
    public int tryMatch(ArrayList<Element> elements, int index) {
        for (int i = index; i < elements.size(); ++i) {
            if (elements.get(i) instanceof SentenceEnding) {
                return i - index + 1;
            }
            childs.add(elements.get(i));
        }
        return 0;
    }
}
