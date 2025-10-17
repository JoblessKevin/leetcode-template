package problems.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EvaluateReversePolishNotation {
    public int evalRPN(String[] tokens) {
        List<String> tokenList = new ArrayList<>(Arrays.asList(tokens));
        return dfs(tokenList);
    }

    private int dfs(List<String> tokens) {
        String token = tokens.remove(tokens.size() - 1);

        if (!"+-*/".contains(token)) {
            return Integer.parseInt(token);
        }

        int right = dfs(tokens);
        int left = dfs(tokens);

        return switch (token) {
            case "+" -> left + right;
            case "-" -> left - right;
            case "*" -> left * right;
            case "/" -> left / right;
            default -> 0;
        };
    }
}
