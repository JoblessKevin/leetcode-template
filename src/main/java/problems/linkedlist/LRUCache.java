package main.java.problems.linkedlist;

import java.util.ArrayDeque;
import java.util.Queue;

class LRUCache {

    private Queue<Integer> que;

    private int capacity;
    private int sz;

    private int[] keyVal;
    private int[] freq;

    public LRUCache(int cap) {
        this.capacity = cap;
        this.sz = 0;
        this.que = new ArrayDeque<>();

        // 假設 key 範圍是 0 ~ 10000
        this.keyVal = new int[10001];
        this.freq = new int[10001];

        // 初始化為 -1 表示不存在
        for (int i = 0; i < keyVal.length; i++) {
            keyVal[i] = -1;
        }
    }

    public int get(int key) {
        if (key < 0 || key >= keyVal.length) {
            return -1;
        }

        if (keyVal[key] == -1) {
            return -1;
        }

        que.offer(key);
        freq[key]++;

        return keyVal[key];
    }

    public void put(int key, int value) {
        if (key < 0 || key >= keyVal.length) {
            return;
        }

        // key 原本不存在
        if (keyVal[key] == -1) {
            // 如果容量已滿，要清掉最久不用的
            while (sz >= capacity) {
                int tp = que.poll(); // 等同 que.pop()

                if (freq[tp] == 1) {
                    keyVal[tp] = -1;
                    sz--;
                }

                freq[tp]--;
            }

            keyVal[key] = value;
            que.offer(key);
            freq[key] = 1;
            sz++;
        } else {
            // key 已存在，只更新 value，並視為最新使用
            keyVal[key] = value;
            que.offer(key);
            freq[key]++;
        }
    }
}
