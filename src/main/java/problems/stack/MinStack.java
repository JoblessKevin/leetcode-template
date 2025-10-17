package problems.stack;

import java.util.Stack;

/** These two Solution time complexity are the same */

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */

class MinStack {
    long min;
    Stack<Long> stack;

    public MinStack() {
        stack = new Stack<>();
    }

    public void push(int val) {
        if (stack.isEmpty()) {
            stack.push(0L);
            min = val;
        } else {
            stack.push(val - min);
            if (val < min) min = val;
        }
    }

    public void pop() {
        if (stack.isEmpty()) return;

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

//class MinStack {
//
//    private Stack<Node> stack;
//
//    private static class Node {
//        int val;
//        int minSoFar;
//        Node (int val, int minSoFar) {
//            this.val = val;
//            this.minSoFar = minSoFar;
//        }
//    }
//
//    public MinStack() {
//        stack = new  Stack<>();
//    }
//
//    public void push(int val) {
//        if (stack.isEmpty()) {
//            stack.push(new Node(val, val));
//        } else {
//            int currentMin = stack.peek().minSoFar;
//            stack.push(new Node(val, Math.min(val, currentMin)));
//        }
//    }
//
//    public void pop() {
//        stack.pop();
//    }
//
//    public int top() {
//        return stack.peek().val;
//    }
//
//    public int getMin() {
//        return stack.peek().minSoFar;
//    }
//}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
