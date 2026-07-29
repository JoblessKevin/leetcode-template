package problems.stack;

import java.util.LinkedList;
import java.util.Queue;

public class ImplementStackUsingQueues {
    public class DoubleQueueStack {
        private Queue<Integer> q1;
        private Queue<Integer> q2;

        public DoubleQueueStack() {
            q1 = new LinkedList<>();
            q2 = new LinkedList<>();
        }

        public void push(int x) {
            q2.offer(x);
            while (!q1.isEmpty()) {
                q2.offer(q1.poll());
            }
            Queue<Integer> temp = q1;
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
        private Queue<Integer> q;

        public OneQueueStack() {
            q = new LinkedList<>();
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

    public class QueueOfQueuesStack {
        private Queue<Object> q;

        public QueueOfQueuesStack() {
            q = null;
        }

        public void push(int x) {
            Queue<Object> newQueue = new LinkedList<>();
            newQueue.add(x);
            newQueue.add(q);
            q = newQueue;
        }

        public int pop() {
            if (q == null)
                return -1;

            int top = (int) q.poll();
            q = (Queue<Object>) q.poll();
            return top;
        }

        public int top() {
            if (q == null)
                return -1;
            return (int) q.peek();
        }

        public boolean empty() {
            return q == null;
        }
    }
}
