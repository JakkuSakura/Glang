package top.jackhack.glang.elements;

import javafx.util.Pair;
import top.jackhack.glang.CannotMatchException;

import java.util.ArrayList;

public class Operator extends TreeLikeElement {
    public static String[] OPERATORS = new String[]{
            "+", "-", "*", "/", "<", "<=", ">", ">=", "=", "==",
            "!=", ";", "(", ")", "^", ",", "\"", "\'", "#", "&",
            "&&", "|", "||", "%", "~", "<<", ">>", "[", "]", "{",
            "}", "\\", ".", "?", ":", "!"
    };

    public static ArrayList<Pair<String, Integer>> OPER = new ArrayList<Pair<String, Integer>>() {
        {
            add(new Pair<>("(", 1));
            add(new Pair<>("[", 1));
            add(new Pair<>(".", 1));

            add(new Pair<>("**", 2));
            add(new Pair<>("+", 2));
            add(new Pair<>("-", 2));
            add(new Pair<>("~", 2));
            add(new Pair<>("!", 2));

            add(new Pair<>("*", 3));
            add(new Pair<>("/", 3));
            add(new Pair<>("*", 3));

            add(new Pair<>("+", 4));
            add(new Pair<>("-", 4));

            add(new Pair<>("<<", 5));
            add(new Pair<>(">>", 5));

            add(new Pair<>("<", 6));
            add(new Pair<>("<=", 6));
            add(new Pair<>(">", 6));
            add(new Pair<>(">=", 6));

            add(new Pair<>("==", 7));
            add(new Pair<>("<>", 7));

            add(new Pair<>("&", 8));
            add(new Pair<>("^", 9));
            add(new Pair<>("|", 10));
            add(new Pair<>("&&", 11));
            add(new Pair<>("||", 12));

            add(new Pair<>("?:", 13));

            add(new Pair<>("=", 14));
            add(new Pair<>("-=", 14));
            add(new Pair<>("+=", 14));
            add(new Pair<>("/=", 14));
            add(new Pair<>("%=", 14));
            add(new Pair<>("/=", 14));
            add(new Pair<>("*=", 14));
            add(new Pair<>("|=", 14));
            add(new Pair<>("&=", 14));
            add(new Pair<>("^=", 14));
            add(new Pair<>("<<=", 14));
            add(new Pair<>(">>=", 14));

            add(new Pair<>(",", 15));

        }
    };

    public static boolean isOperator(String str) {
        for (String e : OPERATORS) {
            if (e.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private String string;

    @Override
    public int tryMatch(ArrayList<Element> elements, int index) {
        String last = null;
        Element e;
        StringBuilder sb = new StringBuilder();
        while (index < elements.size()
                && (e = elements.get(index)) instanceof SourceChar
                && ((SourceChar) e).getType() == SourceCharType.SIGNAL) {
            sb.append(((SourceChar) e).getChar());
            ++index;
            if (isOperator(sb.toString())) {
                last = sb.toString();
            } else {
                break;
            }
        }
        if (last != null) {
            setOper(last);
            return last.length();
        }
        return 0;

    }

    public String getString() {
        return string;
    }

    public Operator setOper(String string) {
        this.string = string;
        return this;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + string + "]" + (size() > 0 ? super.toString() : "");
    }

    public int getPriority() throws CannotMatchException {
        for (Pair<String, Integer> e : OPER) {
            if (e.getKey().equals(string)) {
                return e.getValue();
            }
        }
        throw new CannotMatchException();
    }
}
