package problems.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @formatter:off
 * Restrictions:
 *   1. Methods push, pop, top and getMin operations must all operate in O(1) time complexity.
 * @formatter:on
 */
/** This is the optimal solution. */
class MinStack {
    Deque<int[]> stack = new ArrayDeque<>();

    public MinStack() {}

    public void push(int value) {
        int currentMin = stack.isEmpty() ? value : Math.min(value, stack.peek()[1]);
        stack.push(new int[] {value, currentMin});
    }

    public void pop() {
        stack.pop();
    }

    public int top() {
        return stack.peek()[0];
    }

    public int getMin() {
        return stack.peek()[1];
    }
}


class MinStack_Interpolation {
    private long min;
    private Deque<Long> stack;

    public MinStack_Interpolation() {
        stack = new ArrayDeque<>();
    }

    public void push(int val) {
        if (stack.isEmpty()) {
            stack.push(0L);
            min = val;
        } else {
            stack.push(val - min);
            if (val < min)
                min = val;
        }
    }

    public void pop() {
        if (stack.isEmpty())
            return;

        long pop = stack.pop();

        if (pop < 0) {
            min -= pop;
        }
    }

    public int top() {
        long top = stack.peek();
        if (top > 0) {
            return (int) (top + min);
        } else {
            return (int) min;
        }
    }

    public int getMin() {
        return (int) min;
    }
}
