package problems.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class ImplementQueueUsingStacks {
    public class MyQueue_BruteForce {
        private Deque<Integer> stack1;
        private Deque<Integer> stack2;

        public MyQueue_BruteForce() {
            stack1 = new ArrayDeque<>();
            stack2 = new ArrayDeque<>();
        }

        public void push(int x) {
            stack1.push(x);
        }

        public int pop() {
            while (stack1.size() > 1) {
                stack2.push(stack1.pop());
            }
            int res = stack1.pop();
            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
            return res;
        }

        public int peek() {
            while (stack1.size() > 1) {
                stack2.push(stack1.pop());
            }
            int res = stack1.peek();
            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
            return res;
        }

        public boolean empty() {
            return stack1.isEmpty();
        }
    }

    public class MyQueue {
        private Deque<Integer> s1;
        private Deque<Integer> s2;

        public MyQueue() {
            s1 = new ArrayDeque<>();
            s2 = new ArrayDeque<>();
        }

        public void push(int x) {
            s1.push(x);
        }

        public int pop() {
            if (s2.isEmpty()) {
                while (!s1.isEmpty()) {
                    s2.push(s1.pop());
                }
            }
            return s2.pop();
        }

        public int peek() {
            if (s2.isEmpty()) {
                while (!s1.isEmpty()) {
                    s2.push(s1.pop());
                }
            }
            return s2.peek();
        }

        public boolean empty() {
            return s1.isEmpty() && s2.isEmpty();
        }
    }
}
