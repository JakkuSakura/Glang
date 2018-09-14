package top.jackhack.glang.elements;

import top.jackhack.glang.SignalSet;

public enum SourceCharType {
    ALPHA, NUMBER, SIGNAL, KNOWN;

    static SourceCharType getType(char ch) {
        if (Character.isDigit(ch)) {
            return NUMBER;
        }
        if (Character.isAlphabetic(ch)) {
            return ALPHA;
        }
        if (SignalSet.isSignal(ch)) {
            return SIGNAL;
        }
        return KNOWN;
    }
}
