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

    private char[] getChars (byte[] bytes) {
        Charset cs = Charset.forName ("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate (bytes.length);
        bb.put (bytes);
        bb.flip ();
        CharBuffer cb = cs.decode (bb);

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
        char last = 0;
        int row = 1;
        int col = 1;
        while (p1 < source.length) {
            char ch = source[p1++];
            if (SourceChar.isPrint(ch)) {
                rootElement.childs.add(new SourceChar().setChar(ch).setRow(row).setCol(col));
            }
            else if (Character.isWhitespace(ch) && !Character.isWhitespace(last))
            {
                rootElement.childs.add(new SpaceChar().setRow(row).setCol(col));
            }
            else {
                throw new CannotMatchException("Cannot match at row:" + row + ", col:" + col);
            }
            if (ch == '\n')
            {
                ++row;
                col = 0;
            }

            ++col;
            last = ch;
        }


    }
    public void process(Collection<Class<? extends Element>> elementTypes) throws CannotMatchException {

        for (int i = 0; i < rootElement.childs.size(); ++i) {
            MatchResult matchResult = match(elementTypes, rootElement.childs, i);
            Collection<Element> matched = rootElement.childs.subList(i, i + matchResult.length);
            rootElement.childs.removeAll(matched);
            rootElement.childs.add(i, matchResult.element);
        }
    }

    public void splitWords() throws CannotMatchException {
        process(Collections.singletonList(SourceString.class));
    }

    private static Collection<Class<? extends Element>> PARSE_ELEMENT = new ArrayList<>();
    static{
        PARSE_ELEMENT.add(SentenceEnding.class);
        PARSE_ELEMENT.add(Type.class);
        PARSE_ELEMENT.add(Operator.class);
        PARSE_ELEMENT.add(Identifier.class);
        PARSE_ELEMENT.add(ConstInteger.class);

    }

    public void parseElement() throws CannotMatchException {
        process(PARSE_ELEMENT);
    }

    public void sentenceSplit() throws CannotMatchException{
        process(Collections.singletonList(Sentence.class));
    }


    private class MatchResult{
        public Element element;
        public int length;
        public MatchResult(Element e, int l) {
            element = e;
            length = l;
        }
    }
    public MatchResult match(Collection<Class<? extends Element>> elementTypes, ArrayList<Element> elements, int index) throws CannotMatchException {
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
        throw new CannotMatchException("Cannot match at row:" + elements.get(index).row + ", col:" + elements.get(index).col);

    }

    public String getFilename() {
        return filename;
    }
    public char[] getSource()
    {
        return source;
    }

    public void setSource(char[] chs) {
        source = chs;
    }

}
