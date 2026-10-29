package problems.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/** 最佳解為 CustomNodeStack，但是這個解沒有用到 Queue。實作建議使用 OneQueueStack */
public class ImplementStackUsingQueues {
    public class DoubleQueueStack {
        private Deque<Integer> q1;
        private Deque<Integer> q2;

        public DoubleQueueStack() {
            q1 = new ArrayDeque<>();
            q2 = new ArrayDeque<>();
        }

        public void push(int x) {
            q2.offer(x);
            while (!q1.isEmpty()) {
                q2.offer(q1.poll());
            }
            Deque<Integer> temp = q1;
            q1 = q2;
            q2 = temp;
        }

        public int pop() {
            return q1.poll();
        }

        public int top() {
            return q1.peek();
        }

        public boolean empty() {
            return q1.isEmpty();
        }
    }

    public class OneQueueStack {
        private Deque<Integer> q;

        public OneQueueStack() {
            q = new ArrayDeque<>();
        }

        public void push(int x) {
            q.offer(x);
            for (int i = q.size() - 1; i > 0; i--) {
                q.offer(q.poll());
            }
        }

        public int pop() {
            return q.poll();
        }

        public int top() {
            return q.peek();
        }

        public boolean empty() {
            return q.isEmpty();
        }
    }

    public class CustomNodeStack {
        private class Node {
            int val;
            Node next; // 指向底下前一個被 push 進來的節點

            Node(int val) {
                this.val = val;
                this.next = null;
            }
        }

        private Node top; // 永遠指向堆疊的最頂端

        public CustomNodeStack() {
            this.top = null; // 初始狀態為空
        }

        // 將新元素推入堆疊頂端
        public void push(int x) {
            Node newNode = new Node(x);
            newNode.next = top; // 新節點的下一個指向原本的頂端
            top = newNode; // 更新頂端指標為這個新節點
        }

        // 移除並回傳堆疊頂端的元素
        public int pop() {
            if (empty()) {
                return -1; // 實務上通常會拋出 EmptyStackException
            }
            int value = top.val; // 取出頂端的值
            top = top.next; // 將頂端指標往下移
            return value;
        }

        // 回傳堆疊頂端的元素但不移除
        public int top() {
            if (empty()) {
                return -1;
            }
            return top.val;
        }

        // 檢查堆疊是否為空
        public boolean empty() {
            return top == null;
        }
    }
}
