package problems.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class DailyTemperatures {
    /**
     * 宣告一個 Deque 當作 Stack，裡面存放的是「日子的索引 (index)」
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] res = new int[n];

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int prevIndex = stack.pop();
                res[prevIndex] = i - prevIndex;
            }
            stack.push(i);
        }

        return res;
    }

    public int[] dailyTemperatures_bruteForce(int[] temperatures) {
        int[] res = new int[temperatures.length];
        for (int i = 0; i < temperatures.length; i++) {
            for (int j = i + 1; j < temperatures.length; j++) {
                if (temperatures[j] > temperatures[i]) {
                    res[i] = j - i;
                    break;
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        int[] temperatures = {73, 74, 75, 71, 69, 72, 76, 73};
        int[] res = dailyTemperatures.dailyTemperatures(temperatures);
        for (int i : res) {
            System.out.print(i + " ");
        }
    }
}
