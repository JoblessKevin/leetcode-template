package problems.stack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class EvaluateReversePolishNotation {
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (String ch : tokens) {
            switch (ch) {
                case "+" -> {
                    stack.push(stack.pop() + stack.pop());
                }
                case "-" -> {
                    int a = stack.pop();
                    int b = stack.pop();
                    stack.push(b - a);
                }
                case "*" -> {
                    stack.push(stack.pop() * stack.pop());
                }
                case "/" -> {
                    int a = stack.pop();
                    int b = stack.pop();
                    stack.push(b / a);
                }

                default -> stack.push(Integer.parseInt(ch));
            }
        }

        return stack.pop();
    }

    public int evalRPN_dfs(String[] tokens) {
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
