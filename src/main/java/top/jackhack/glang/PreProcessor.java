package top.jackhack.glang;

import top.jackhack.glang.elements.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.*;

public class PreProcessor {
    private char[] source;
    private String filename;
    private Root rootElement = new Root();

    public Root getRootElement() {
        return rootElement;
    }

    private char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);

        return cb.array();
    }

    public int readFile(String url) throws IOException {
        filename = url;
        File file = new File(url);

        FileInputStream fileInputStream = new FileInputStream(file);

        byte[] bytes = new byte[fileInputStream.available()];
        int l = fileInputStream.read(bytes);

        source = getChars(bytes);

        return l;


    }

    public void strip() throws CannotMatchException {
        int p1 = 0;
        int row = 1;
        int col = 1;
        while (p1 < source.length) {
            char ch = source[p1++];
            if (SourceChar.isPrint(ch)) {
                rootElement.childs.add(new SourceChar().setChar(ch).setRow(row).setCol(col));
            } else if (Character.isWhitespace(ch)) {
                rootElement.childs.add(new SpaceChar().setRow(row).setCol(col));
            } else {
                throw new CannotMatchException("Cannot match at row:" + row + ", col:" + col);
            }
            if (ch == '\n') {
                ++row;
                col = 0;
            }

            ++col;
        }


    }

    public void process(TreeLikeElement root, Collection<Class<? extends Element>> elementTypes, boolean thw) throws CannotMatchException {

        for (int i = 0; i < root.size(); ++i) {
            MatchResult matchResult = match(elementTypes, root.childs, i, thw);
            if (matchResult != null) {
                Collection<Element> matched = root.childs.subList(i, i + matchResult.length);
                root.childs.removeAll(matched);
                root.childs.add(i, matchResult.element);
            }
        }
    }


    private static Collection<Class<? extends Element>> SPLIT_WORDS_ELEMENTS = new ArrayList<>();

    static {
        SPLIT_WORDS_ELEMENTS.add(Type.class);
        SPLIT_WORDS_ELEMENTS.add(ConstInteger.class);
        SPLIT_WORDS_ELEMENTS.add(Identifier.class);
        SPLIT_WORDS_ELEMENTS.add(StatementEnding.class);
        SPLIT_WORDS_ELEMENTS.add(Operator.class);
    }

    public void splitWords() throws CannotMatchException {
        for (int i = 0; i < rootElement.size(); ++i) {
            while (i < rootElement.size() && rootElement.childs.get(i) instanceof SpaceChar) {
                rootElement.childs.remove(i);
            }
            if (i < rootElement.size()) {
                MatchResult matchResult = match(SPLIT_WORDS_ELEMENTS, rootElement.childs, i, true);
                Collection<Element> matched = rootElement.childs.subList(i, i + matchResult.length);
                rootElement.childs.removeAll(matched);
                rootElement.childs.add(i, matchResult.element);

            }
        }
    }

    private int processBrackets(TreeLikeElement root, final int index) {
        int i = index;
        while (i < rootElement.size()) {
            Element e = rootElement.childs.get(i);
            if (e instanceof Bracket) {
                if (((Bracket) e).isLeft()) {
                    i = processBrackets((TreeLikeElement) e, i + 1);
                } else if (!((Bracket) e).isLeft()) {
                    return i;
                }
            }
            root.childs.add(e);
            ++i;
        }
        return i;
    }

    public void splitBrackets() throws CannotMatchException {
        process(rootElement, Collections.singletonList(Bracket.class), false);
        Root root = new Root();
        processBrackets(root, 0);
        rootElement.childs = root.childs;
    }

    private void splitStatement(TreeLikeElement element) throws CannotMatchException {
        for (int i = 0; i < element.size(); ++i) {
            Element e = element.childs.get(i);
            if (e instanceof Bracket) {
                splitStatement((TreeLikeElement) e);
            } else {
                MatchResult matchResult = match(Collections.singletonList(Statement.class), element.childs, i, true);
                if (matchResult != null) {
                    Collection<Element> matched = element.childs.subList(i, i + matchResult.length);
                    element.childs.removeAll(matched);
                    element.childs.add(i, matchResult.element);
                }
            }

        }


    }

    public void sentenceSplit() throws CannotMatchException {
        splitStatement(rootElement);
    }

    public ArrayList<Element> postFix(ArrayList<Element> expr) throws CannotMatchException {
        ArrayList<Element> postfix = new ArrayList<>();
        Stack<Operator> stack = new Stack<>();
        stack.add(new StackBottom());
        for (Element e : expr) {
            assert e instanceof Operator || e instanceof ConstInteger || e instanceof Identifier;
            if (e instanceof Identifier || e instanceof ConstInteger) {
                postfix.add(e);
            } else if (e instanceof Parentheses && ((Parentheses) e).isLeft()) {
                stack.push((Operator) e);
            } else if (e instanceof Parentheses && !((Parentheses) e).isLeft()) {
                while (stack.peek() instanceof Parentheses)
                    postfix.add(stack.pop());
            } else {
                while (stack.peek().getPriority() < ((Operator) e).getPriority()) {
                    postfix.add(stack.pop());
                }
                stack.push((Operator) e);
            }
        }
        while (stack.size() > 1) {
            postfix.add(stack.pop());
        }
        return postfix;
    }


    public Element buildTree(ArrayList<Element> expr) {
        Stack<Element> stack = new Stack<>();
        for (Element e : expr) {
            assert e instanceof Identifier || e instanceof ConstInteger || e instanceof Operator;
            if (e instanceof Identifier || e instanceof ConstInteger) {
                stack.push(e);
            } else {
                Element right = stack.pop();
                Element left = stack.pop();
                ((Operator) e).childs.add(left);
                ((Operator) e).childs.add(right);
                stack.push(e);
            }
        }
        return stack.elementAt(0);
    }


    private class MatchResult {
        public Element element;
        public int length;

        public MatchResult(Element e, int l) {
            element = e;
            length = l;
        }
    }

    public MatchResult match(Collection<Class<? extends Element>> elementTypes, ArrayList<Element> elements,
                             int index, boolean thw) throws CannotMatchException {
        for (Class<? extends Element> element_class : elementTypes) {
            try {
                Element element1 = element_class.newInstance();
                int length = element1.tryMatch(elements, index);
                if (length > 0) {
                    return new MatchResult(element1, length);
                }

            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        if (thw)
            throw new CannotMatchException("Cannot match at row:" + elements.get(index).row + ", col:" + elements.get(index).col);
        else
            return null;

    }

    public String getFilename() {
        return filename;
    }

    public char[] getSource() {
        return source;
    }

    public void setSource(char[] chs) {
        source = chs;
    }

}
