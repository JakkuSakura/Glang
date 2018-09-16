package top.jackhack.glang;

import sun.misc.Signal;

import java.util.Arrays;

public class SignalSet {

    public static String[] KEYWORDS = new String[]{
            "def", "break", "case", "char", "const", "continue",
            "default", "do", "else", "float", "float64", "for",
            "goto", "if", "int", "int64", "int16", "return", "in",
            "static", "struct", "switch", "unsigned", "while"
    };

    public static String SIGNALS = "+-*/\\[],.<>?=_)(&^%$#@!~`:;{}";
    public static boolean isSignal(char b) {
        return SIGNALS.indexOf(b) != -1;
    }
}
