package designs.lld;

class MyHashMap {
    class Node {
        int key, value;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int SIZE = 769; // 質數有助於均勻分佈
    private Node[] buckets;

    public MyHashMap() {
        buckets = new Node[SIZE];
    }

    public void put(int key, int value) {
        int index = key % SIZE;
        if (buckets[index] == null) {
            buckets[index] = new Node(key, value);
        } else {
            Node curr = buckets[index];
            while (true) {
                if (curr.key == key) {
                    curr.value = value;
                    return;
                }
                if (curr.next == null)
                    break;
                curr = curr.next;
            }
            curr.next = new Node(key, value);
        }
    }

    public int get(int key) {
        int index = key % SIZE;
        Node curr = buckets[index];
        while (curr != null) {
            if (curr.key == key)
                return curr.value;
            curr = curr.next;
        }
        return -1;
    }

    public void remove(int key) {
        int index = key % SIZE;
        Node curr = buckets[index];
        if (curr == null)
            return;

        if (curr.key == key) {
            buckets[index] = curr.next;
            return;
        }

        while (curr.next != null) {
            if (curr.next.key == key) {
                curr.next = curr.next.next;
                return;
            }
            curr = curr.next;
        }
    }
}


public class DesignHashMap {

}
