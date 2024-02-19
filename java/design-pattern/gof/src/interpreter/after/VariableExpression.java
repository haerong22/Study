package interpreter.after;

import java.util.Map;

public class VariableExpression implements PostfixExpression {

    private Character variable;

    public VariableExpression(Character variable) {
        this.variable = variable;
    }

    @Override
    public int interpret(Map<Character, Integer> context) {
        return context.get(variable);
    }
}
