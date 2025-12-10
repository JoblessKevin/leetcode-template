package main.java.problems.linkedlist;

import java.util.HashMap;
import java.util.Map;

import main.java.problems.linkedlist.LFUCache.DoublyLinkedList;

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

class LFUCache {
    class Node {
        int key, value, freq;
        Node prev, next;
        Node(int k, int v) {
            key = k;
            value = v;
            freq = 1;
        }
    }

    class DoublyLinkedList {
        Node head, tail;
        int size;
        DoublyLinkedList() {
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }
        void addFirst(Node node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
            size++;
        }
        void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
        Node removeLast() {
            if (size == 0) return null;
            Node lastNode = tail.prev;
            remove(lastNode);
            
            return lastNode;
        }
    }

    private int capacity, minFreq;
    private Map<Integer, Node> keyMap;
    private Map<Integer, DoublyLinkedList> freqMap;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
        this.keyMap = new HashMap<>();
        this.freqMap = new HashMap<>();
    }
    
    public int get(int key) {
        if (!keyMap.containsKey(key)) return -1;
        Node node = keyMap.get(key);
        updateFreq(node);
        
        return node.value;
    }
    
    public void put(int key, int value) {
        if (capacity == 0) return;
        if (keyMap.containsKey(key)) {
            Node node = keyMap.get(key);
            node.value = value;
            updateFreq(node);
            
            return;
        }
        if (keyMap.size() >= capacity) {
            DoublyLinkedList list = freqMap.get(minFreq);
            Node toRemove = list.removeLast();
            keyMap.remove(toRemove.key);
        }
        Node newNode = new Node(key, value);
        keyMap.put(key, newNode);
        minFreq = 1;
        freqMap.computeIfAbsent(1, k -> new DoublyLinkedList())
                .addFirst(newNode);
    }

    public void updateFreq(Node node) {
        int curFreq = node.freq;
        DoublyLinkedList list
            = freqMap.get(curFreq);
        list.remove(node);
        if(curFreq == minFreq && list.size == 0) {
            minFreq++;
        }
        node.freq++;
        freqMap.computeIfAbsent(node.freq, k -> new DoublyLinkedList())
                .addFirst(node);
    }

    public static void main(String[] args) {
        System.out.println("--- 開始測試 LFU Cache (容量: 2) ---");
        
        // 1. 初始化容量為 2 的 LFU Cache
        LFUCache cache = new LFUCache(2);

        // 2. 放入兩個數據 (頻率都為 1)
        System.out.println("put(1, 1)");
        cache.put(1, 1);
        System.out.println("put(2, 2)");
        cache.put(2, 2);

        // 3. 訪問 Key 1 -> Key 1 的頻率變為 2
        System.out.println("get(1) -> 預期 1, 實際: " + cache.get(1));
        
        // 此時狀態：
        // Key 1: freq=2
        // Key 2: freq=1 (minFreq)

        // 4. 放入 Key 3 -> 容量已滿，需要淘汰 minFreq (即 Key 2)
        System.out.println("put(3, 3) -> 應該淘汰 Key 2");
        cache.put(3, 3);

        // 5. 驗證 Key 2 是否被淘汰
        System.out.println("get(2) -> 預期 -1 (未找到), 實際: " + cache.get(2));
        
        // 6. 驗證 Key 3 是否存在
        System.out.println("get(3) -> 預期 3, 實際: " + cache.get(3));
        
        // 此時狀態：
        // Key 1: freq=2
        // Key 3: freq=2 (剛被 get 過，所以 freq 變 2)
        // 兩者頻率相同，但 Key 3 是剛被訪問的 (最新的)，Key 1 是較舊的。
        
        // 7. 放入 Key 4 -> 容量滿，頻率都是 2。根據 LFU 內的 LRU 規則，應淘汰較久沒用的 Key 1
        System.out.println("put(4, 4) -> 頻率同為2，應該淘汰較舊的 Key 1");
        cache.put(4, 4);

        // 8. 最終驗證
        System.out.println("get(1) -> 預期 -1 (被淘汰), 實際: " + cache.get(1));
        System.out.println("get(3) -> 預期 3, 實際: " + cache.get(3));
        System.out.println("get(4) -> 預期 4, 實際: " + cache.get(4));
    }
}