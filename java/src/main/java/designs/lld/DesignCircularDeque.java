package designs.lld;

public class DesignCircularDeque {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    public static class ArraySolution {
        class MyCircularDeque {
            private int[] data;
            private int front;
            private int rear;
            private int size;
            private int capacity;

            // 1. 建構子：初始化容量
            public MyCircularDeque(int k) {
                capacity = k;
                data = new int[k];
                front = 0;
                rear = 0;
                size = 0;
            }

            // 2. 從前端插入元素
            public boolean insertFront(int value) {
                if (isFull()) {
                    return false;
                }
                if (isEmpty()) {
                    front = 0;
                    rear = 0;
                    data[front] = value;
                } else {
                    // 逆時針移動 front，加上 capacity 防止負數取餘數出錯
                    front = (front - 1 + capacity) % capacity;
                    data[front] = value;
                }
                size++;
                return true;
            }

            // 3. 從尾端插入元素
            public boolean insertLast(int value) {
                if (isFull()) {
                    return false;
                }
                if (isEmpty()) {
                    front = 0;
                    rear = 0;
                    data[rear] = value;
                } else {
                    // 順時針移動 rear
                    rear = (rear + 1) % capacity;
                    data[rear] = value;
                }
                size++;
                return true;
            }

            // 4. 從前端刪除元素
            public boolean deleteFront() {
                if (isEmpty()) {
                    return false;
                }
                front = (front + 1) % capacity;
                size--;
                return true;
            }

            // 5. 從尾端刪除元素
            public boolean deleteLast() {
                if (isEmpty()) {
                    return false;
                }
                rear = (rear - 1 + capacity) % capacity;
                size--;
                return true;
            }

            // 6. 取得前端元素
            public int getFront() {
                if (isEmpty()) {
                    return -1;
                }
                return data[front];
            }

            // 7. 取得尾端元素
            public int getRear() {
                if (isEmpty()) {
                    return -1;
                }
                return data[rear];
            }

            // 8. 檢查是否為空
            public boolean isEmpty() {
                return size == 0;
            }

            // 9. 檢查是否已滿
            public boolean isFull() {
                return size == capacity;
            }
        }
    }

    public static class DoublyLinkedListSolution {
        class MyCircularDeque {
            // 定義雙向鏈結串列的節點
            private class Node {
                int val;
                Node prev, next;

                Node(int val) {
                    this.val = val;
                }
            }

            private Node head; // dummy head
            private Node tail; // dummy tail
            private int size;
            private int capacity;

            public MyCircularDeque(int k) {
                capacity = k;
                size = 0;
                // 建立 dummy 頭尾節點，互相牽手
                head = new Node(0);
                tail = new Node(0);
                head.next = tail;
                tail.prev = head;
            }

            public boolean insertFront(int value) {
                if (isFull())
                    return false;

                Node newNode = new Node(value);
                Node nextNode = head.next; // 原本的第一個節點

                // 插入在 head 與 nextNode 之間
                head.next = newNode;
                newNode.prev = head;
                newNode.next = nextNode;
                nextNode.prev = newNode;

                size++;
                return true;
            }

            public boolean insertLast(int value) {
                if (isFull())
                    return false;

                Node newNode = new Node(value);
                Node prevNode = tail.prev; // 原本的最後一個節點

                // 插入在 prevNode 與 tail 之間
                prevNode.next = newNode;
                newNode.prev = prevNode;
                newNode.next = tail;
                tail.prev = newNode;

                size++;
                return true;
            }

            public boolean deleteFront() {
                if (isEmpty())
                    return false;

                Node target = head.next; // 要被刪除的第一個節點
                Node nextNode = target.next;

                // 拔掉 target
                head.next = nextNode;
                nextNode.prev = head;

                size--;
                return true;
            }

            public boolean deleteLast() {
                if (isEmpty())
                    return false;

                Node target = tail.prev; // 要被刪除的最後一個節點
                Node prevNode = target.prev;

                // 拔掉 target
                tail.prev = prevNode;
                prevNode.next = tail;

                size--;
                return true;
            }

            public int getFront() {
                if (isEmpty())
                    return -1;
                return head.next.val;
            }

            public int getRear() {
                if (isEmpty())
                    return -1;
                return tail.prev.val;
            }

            public boolean isEmpty() {
                return size == 0;
            }

            public boolean isFull() {
                return size == capacity;
            }
        }
    }
}
