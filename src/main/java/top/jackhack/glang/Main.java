package top.jackhack.glang;

public class Main {
    public static void main(String[] args) throws CannotMatchException {
        System.out.println("Welcome to use Glang");
        PreProcessor preProcessor = new PreProcessor();
//        preProcessor.readFile();
        preProcessor.setSource("{int a = 1;int64 b = 2;}".toCharArray());
        preProcessor.strip();
        System.out.println(new String(preProcessor.getSource()));

        preProcessor.splitWords();
        System.out.println(preProcessor.getRootElement().childs);

        preProcessor.splitBrackets();
        System.out.println(preProcessor.getRootElement().childs);

        preProcessor.sentenceSplit();
        System.out.println(preProcessor.getRootElement().childs);

//        preProcessor.
        preProcessor.getRootElement().childs = preProcessor.buildTree(preProcessor.postFix());

    }
}
