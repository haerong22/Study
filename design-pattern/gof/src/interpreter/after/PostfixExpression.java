package interpreter.after;

import java.util.Map;

public interface PostfixExpression {

    int interpret(Map<Character, Integer> context);

    static PostfixExpression plus(PostfixExpression left, PostfixExpression right) {
        return context -> left.interpret(context) + right.interpret(context);
    }

    static PostfixExpression minus(PostfixExpression left, PostfixExpression right) {
        return context -> left.interpret(context) - right.interpret(context);
    }

    static PostfixExpression multiply(PostfixExpression left, PostfixExpression right) {
        return context -> left.interpret(context) * right.interpret(context);
    }

    static PostfixExpression variable(Character c) {
        return context -> context.get(c);
    }
}
