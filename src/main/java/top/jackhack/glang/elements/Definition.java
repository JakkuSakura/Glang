package top.jackhack.glang.elements;

import java.util.ArrayList;

public class Definition extends Statement {
    private Type type;
    private Identifier id;

    @Override
    public int tryMatch(ArrayList<Element> elements, int index) {
        if (elements.get(index) instanceof Statement) {
            Statement statement = (Statement) elements.get(index);
            if (statement.get(0) instanceof Type && statement.get(1) instanceof Identifier) {
                type = (Type) statement.get(0);
                id = (Identifier) statement.get(1);
                if (statement.size() > 4 && statement.get(2) instanceof Operator && ((Operator) statement.get(2)).getString().equals("=")) {
                    for (int i = 3; i < statement.size(); ++i) {
                        add(statement.add(statement.get(i)));
                    }
                }
                return 1;
            }

        }
        return 0;
    }

    public Type getType() {
        return type;
    }

    public Identifier getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("[Def %s as %s:%s]", id, type, childs.toString());
    }
}
