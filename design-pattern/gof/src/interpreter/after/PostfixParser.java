package interpreter.after;

import java.util.Stack;

import static interpreter.after.PostfixExpression.*;

public class PostfixParser {

    public static PostfixExpression parse(String expression) {
        Stack<PostfixExpression> stack = new Stack<>();
        for (char c : expression.toCharArray()) {
            stack.push(getExpression(c, stack));
        }
        return stack.pop();
    }

    private static PostfixExpression getExpression(char c, Stack<PostfixExpression> stack) {
        switch (c) {
            case '+':
                return plus(stack.pop(), stack.pop());
            case '-':
                PostfixExpression right = stack.pop();
                PostfixExpression left = stack.pop();
                return minus(left, right);
            case '*':
                return multiply(stack.pop(), stack.pop());
            default:
                return variable(c);
        }
    }
}
