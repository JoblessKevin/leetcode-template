package problems.linkedlist;

import java.util.HashMap;
import java.util.Map;

class LRUCache {
    class Node {
        int key, value;
        Node prev, next;

        Node(int k, int v) {
            key = k;
            value = v;
        }
    }

    private int capacity;
    private Map<Integer, Node> map;
    private Node head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!map.containsKey(key))
            return -1;
        Node node = map.get(key);
        removeNode(node);
        addToHead(node);

        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            removeNode(node);
            addToHead(node);
        } else {
            if (map.size() == capacity) {
                Node lru = tail.prev;
                map.remove(lru.key);
                removeNode(lru);
            }

            Node newNode = new Node(key, value);
            map.put(key, newNode);
            addToHead(newNode);
        }
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addToHead(Node node) {
        Node nextNode = head.next;
        head.next = node;
        node.prev = head;
        node.next = nextNode;
        nextNode.prev = node;
    }

    /**
     * @fomatter:off 
     * 很好的寫法，但是不好理解
    private void addToHead(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }
     * @formatter:on
     */

    public static void main(String[] args) {
        System.out.println("==== Test 1: Basic Put/Get ====");
        LRUCache cache1 = new LRUCache(2);
        cache1.put(1, 1);
        cache1.put(2, 2);
        printResult(cache1.get(1), 1);
        printResult(cache1.get(2), 2);

        System.out.println("\n==== Test 2: Overwrite Existing Key ====");
        LRUCache cache2 = new LRUCache(2);
        cache2.put(1, 1);
        cache2.put(1, 10);
        printResult(cache2.get(1), 10);

        cache2.put(2, 2);
        cache2.put(3, 3); // 應該 evict 掉 key=2
        printResult(cache2.get(2), -1);
        printResult(cache2.get(1), 10);
        printResult(cache2.get(3), 3);

        System.out.println("\n==== Test 3: Eviction Order ====");
        LRUCache cache3 = new LRUCache(2);
        cache3.put(1, 1);
        cache3.put(2, 2);
        cache3.get(1); // 使用 1 -> 1 becomes MRU
        cache3.put(3, 3); // 應該 evict 掉 key=2

        printResult(cache3.get(2), -1);
        printResult(cache3.get(1), 1);
        printResult(cache3.get(3), 3);

        System.out.println("\n==== Test 4: Capacity One Cache ====");
        LRUCache cache4 = new LRUCache(1);
        cache4.put(1, 1);
        printResult(cache4.get(1), 1);

        cache4.put(2, 2); // evict 1
        printResult(cache4.get(1), -1);
        printResult(cache4.get(2), 2);

        cache4.put(3, 3); // evict 2
        printResult(cache4.get(2), -1);
        printResult(cache4.get(3), 3);

        System.out.println("\n==== Test 5: Get Non-existing ====");
        LRUCache cache5 = new LRUCache(2);
        printResult(cache5.get(100), -1);
        cache5.put(1, 1);
        printResult(cache5.get(2), -1);

        System.out.println("\n==== Test 6: LeetCode Example ====");
        LRUCache cache6 = new LRUCache(2);
        cache6.put(1, 1);
        cache6.put(2, 2);
        printResult(cache6.get(1), 1); // -> return 1
        cache6.put(3, 3); // evicts 2
        printResult(cache6.get(2), -1);
        cache6.put(4, 4); // evicts 1
        printResult(cache6.get(1), -1);
        printResult(cache6.get(3), 3);
        printResult(cache6.get(4), 4);
    }

    private static void printResult(int result, int expected) {
        System.out.printf("Result: %-3d | Expected: %-3d | %s\n", result, expected,
                                        (result == expected ? "PASS" : "FAIL"));
    }
}
