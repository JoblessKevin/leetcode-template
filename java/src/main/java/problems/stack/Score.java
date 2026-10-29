package problems.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class Score {
    public int calPoints(String[] operations) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (String op : operations) {
            switch (op) {
                case "+":
                    int top = stack.pop();
                    int newTop = top + stack.peek();
                    stack.push(top);
                    stack.push(newTop);
                    break;
                case "D":
                    stack.push(2 * stack.peek());
                    break;
                case "C":
                    stack.pop();
                    break;
                default:
                    stack.push(Integer.valueOf(op));
            }
        }

        int res = 0;
        for (int score : stack) {
            res += score;
        }
        return res;
    }

    public static void main(String[] args) {
        Score score = new Score();
        String[] operations = {"5", "2", "C", "D", "+"};
        int result = score.calPoints(operations);
        System.out.println(result); // Output: 30
    }
}
