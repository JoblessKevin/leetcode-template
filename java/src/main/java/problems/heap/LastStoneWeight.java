package problems.heap;

import java.util.Collections;
import java.util.PriorityQueue;

public class LastStoneWeight {
    /**
     * 方法一：MaxHeap (正規推薦寫法)
     * 邏輯直觀：直接由大到小排，取出最重的兩顆互撞。
     */
    public int lastStoneWeightViaMaxHeap(int[] stones) {
        // 使用 Collections.reverseOrder() 讓 PriorityQueue 變成由大到小
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        // 直接存正數
        for (int s : stones) {
            maxHeap.offer(s);
        }

        while (maxHeap.size() > 1) {
            int y = maxHeap.poll(); // 第一重的石頭
            int x = maxHeap.poll(); // 第二重的石頭

            // 如果兩顆不一樣重，剩下的重量放回去
            if (y > x) {
                maxHeap.offer(y - x);
            }
            // 如果 y == x，兩顆抵銷，甚麼都不用做
        }

        // 如果 Heap 是空的(全抵銷)回傳 0，否則回傳剩下的那一顆
        return maxHeap.isEmpty() ? 0 : maxHeap.peek();
    }

    /**
     * 方法二：MinHeap (負數技巧)
     * 邏輯：存負數，利用 "越小的負數其實絕對值越大" 的特性來模擬 MaxHeap。
     */
    public int lastStoneWeightViaMinHeap(int[] stones) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int s : stones) {
            minHeap.offer(-s); // 存負數
        }

        while (minHeap.size() > 1) {
            int y = minHeap.poll(); // 拿到 "最負" 的 (例如 -8)
            int x = minHeap.poll(); // 拿到 "次負" 的 (例如 -7)

            // 這裡邏輯要小心：因為是負數，-8 < -7 是 True
            // 只要不相等，就相減
            if (y != x) {
                // -8 - (-7) = -1，剛好把剩下的重量放回去
                minHeap.offer(y - x);
            }
        }

        // 最後如果沒東西了回傳 0
        return minHeap.isEmpty() ? 0 : Math.abs(minHeap.peek());
    }

    // ==========================================
    // Test cases
    // ==========================================
    public static void main(String[] args) {
        LastStoneWeight solution = new LastStoneWeight();
        int[] stones = { 2, 7, 4, 1, 8, 1 };
        // 過程預演:
        // [2,7,4,1,8,1]
        // 1. 拿出 8, 7 -> 剩 1 -> 放回 -> [2,4,1,1,1]
        // 2. 拿出 4, 2 -> 剩 2 -> 放回 -> [2,1,1,1]
        // 3. 拿出 2, 1 -> 剩 1 -> 放回 -> [1,1,1]
        // 4. 拿出 1, 1 -> 抵銷 -> [1]
        // 結果應為 1

        System.out.println("MaxHeap Result: " + solution.lastStoneWeightViaMaxHeap(stones));
        System.out.println("MinHeap Result: " + solution.lastStoneWeightViaMinHeap(stones));
    }
}
