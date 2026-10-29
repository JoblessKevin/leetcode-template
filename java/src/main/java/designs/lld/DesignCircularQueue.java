package designs.lld;

public class DesignCircularQueue {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    public static class LinkedListSolution {
        public static class CircularQueue {
            private int space;
            private ListNode left;
            private ListNode right;

            public CircularQueue(int k) {
                this.space = k;
                this.left = new ListNode(0);
                this.right = this.left;
            }

            public boolean enQueue(int value) {
                if (isFull())
                    return false;

                ListNode cur = new ListNode(value);
                if (isEmpty()) {
                    this.left.next = cur;
                    this.right = cur;
                } else {
                    this.right.next = cur;
                    this.right = cur;
                }

                this.space--;
                return true;
            }

            public boolean deQueue() {
                if (isEmpty())
                    return false;

                this.left.next = this.left.next.next;
                if (this.left.next == null) {
                    this.right = this.left;
                }

                this.space++;
                return true;
            }

            public int Front() {
                return isEmpty() ? -1 : this.left.next.val;
            }

            public int Rear() {
                return isEmpty() ? -1 : this.right.val;
            }

            public boolean isEmpty() {
                return this.left.next == null;
            }

            public boolean isFull() {
                return this.space == 0;
            }
        }
    }

    /** Optimal */
    public static class ArraySolution {
        public static class MyCircularQueue {
            private int[] data;
            private int head;
            private int tail;
            private int size;
            private int capacity;

            public MyCircularQueue(int k) {
                capacity = k;
                data = new int[k];
                head = 0;
                tail = -1;
                size = 0;
            }

            public boolean enQueue(int value) {
                if (isFull()) {
                    return false;
                }
                tail = (tail + 1) % capacity;
                data[tail] = value;
                size++;
                return true;
            }

            public boolean deQueue() {
                if (isEmpty()) {
                    return false;
                }
                head = (head + 1) % capacity;
                size--;
                return true;
            }

            public int Front() {
                if (isEmpty()) {
                    return -1;
                }
                return data[head];
            }

            public int Rear() {
                if (isEmpty()) {
                    return -1;
                }
                return data[tail];
            }

            public boolean isEmpty() {
                return size == 0;
            }

            public boolean isFull() {
                return size == capacity;
            }
        }
    }

    /**
     * Your MyCircularQueue object will be instantiated and called as such: MyCircularQueue obj =
     * new MyCircularQueue(k); boolean param_1 = obj.enQueue(value); boolean param_2 =
     * obj.deQueue(); int param_3 = obj.Front(); int param_4 = obj.Rear(); boolean param_5 =
     * obj.isEmpty(); boolean param_6 = obj.isFull();
     */
}
