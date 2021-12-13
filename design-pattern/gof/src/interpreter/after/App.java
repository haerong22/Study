package interpreter.after;

import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        PostfixExpression expression = PostfixParser.parse("xyz+-");
        Map<Character, Integer> map = new HashMap<>();
        map.put('x', 1);
        map.put('y', 2);
        map.put('z', 3);
        int result = expression.interpret(map);
        System.out.println("result = " + result);
    }
}
