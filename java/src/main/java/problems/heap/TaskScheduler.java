package problems.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 數學公式法：直接算出 「最後的總長度」。 填空法：算出 「還缺多少發呆時間 (Idle)」，然後加回任務總數。
 */
public class TaskScheduler {
    /**
     * Greedy Solution (Optimal) Time complexity: O(N) Space complexity: O(1)
     */
    public int leastInterval(char[] tasks, int n) {
        // 1. 統計每個任務的頻率
        int[] counts = new int[26];
        for (char task : tasks) {
            counts[task - 'A']++;
        }

        Arrays.sort(counts);

        int maxFreq = counts[25];

        int idle = (maxFreq - 1) * n;

        for (int i = 24; i >= 0; i--) {
            idle -= Math.min(maxFreq - 1, counts[i]);
        }

        return Math.max(0, idle) + tasks.length;
    }

    /**
     * Greedy Solution: Math Time complexity: O(N) Space complexity: O(1) Formula: (maxFreq - 1) *
     * (n + 1) + maxCount
     */
    public int leastInterval_math(char[] tasks, int n) {
        int[] counts = new int[26];
        for (char task : tasks) {
            counts[task - 'A']++;
        }

        Arrays.sort(counts);
        int maxFreq = counts[25];

        int maxCount = 0;
        for (int i = 25; i >= 0; i--) {
            if (counts[i] == maxFreq) {
                maxCount++;
            } else {
                break;
            }
        }

        int formulaResult = (maxFreq - 1) * (n + 1) + maxCount;

        return Math.max(tasks.length, formulaResult);
    }

    /**
     * Heap Solution Time complexity: O(NlogN) Space complexity: O(1)
     */
    public int leastInterval_heap(char[] tasks, int n) {
        // 1. 統計頻率
        int[] map = new int[26];
        for (char task : tasks) {
            map[task - 'A']++;
        }

        // 2. 建立 Max-Heap，把頻率丟進去
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int count : map) {
            if (count > 0) {
                maxHeap.offer(count);
            }
        }

        int time = 0;

        // 3. 開始模擬 CPU 運作
        while (!maxHeap.isEmpty()) {
            // 用來暫存這一個週期內執行過的任務 (還在冷卻，不能馬上放回 Heap)
            List<Integer> temp = new ArrayList<>();

            // 一個週期的長度是 n + 1 (執行 1 + 冷卻 n)
            int cycle = n + 1;

            // 在這個週期內，盡量從 Heap 拿任務出來做
            while (cycle > 0 && !maxHeap.isEmpty()) {
                int freq = maxHeap.poll(); // 拿出最頻繁的任務
                if (freq > 1) {
                    temp.add(freq - 1); // 做完了，次數 -1，先放暫存區
                }
                time++; // 時間過了 1 單位
                cycle--; // 週期剩餘空間 -1
            }

            // 4. 把暫存區的任務放回 Heap (它們冷卻完畢，下一輪可以再被選)
            maxHeap.addAll(temp);

            // 5. 處理 IDLE (發呆時間)
            // 如果 Heap 空了，代表所有任務都做完了，不需要再發呆，直接結束。
            if (maxHeap.isEmpty()) {
                break;
            }

            // 如果 Heap 還有任務，但這個週期 (n+1) 還沒填滿 (cycle > 0)
            // 代表我們無事可做，必須發呆補滿這個週期
            time += cycle;
        }

        return time;
    }

    // ==========================================
    // Test cases
    // ==========================================
    public static void main(String[] args) {
        TaskScheduler solution = new TaskScheduler();

        System.out.println("=== Task Scheduler Tests ===");

        // Test Case 1: 標準情況 (Standard Case)
        // A 出現 3 次，n=2 -> A _ _ A _ _ A -> 中間填 B
        char[] tasks1 = {'A', 'A', 'A', 'B', 'B', 'B'};
        int n1 = 2;
        System.out.println("\nCase 1: Standard (n=2)");
        System.out.println("Input: " + Arrays.toString(tasks1));
        System.out.println("Expected: 8 (A->B->idle->A->B->idle->A->B)");
        System.out.println("Greedy Result: " + solution.leastInterval(tasks1, n1));
        System.out.println("Math Result:   " + solution.leastInterval_math(tasks1, n1));
        System.out.println("Heap Result:   " + solution.leastInterval_heap(tasks1, n1));

        // Test Case 2: 無冷卻 (No Cooling)
        // n=0，應該等於任務總長度
        char[] tasks2 = {'A', 'A', 'A', 'B', 'B', 'B'};
        int n2 = 0;
        System.out.println("\nCase 2: No Cooling (n=0)");
        System.out.println("Input: " + Arrays.toString(tasks2));
        System.out.println("Expected: 6");
        System.out.println("Greedy Result: " + solution.leastInterval(tasks2, n2));
        System.out.println("Math Result:   " + solution.leastInterval_math(tasks2, n2));
        System.out.println("Heap Result:   " + solution.leastInterval_heap(tasks2, n2));

        // Test Case 3: 任務溢出 (Overflow / Saturation)
        // 雜魚任務太多，把空位填滿了還不夠放
        // A:3, B:3, C:3, D:2, E:1. Total = 12
        // 公式算出的骨架: (3-1)*(2+1) + 3(A,B,C並列) = 9
        // 但實際任務有 12 個，所以答案應該是 12
        char[] tasks3 = {'A', 'A', 'A', 'B', 'B', 'B', 'C', 'C', 'C', 'D', 'D', 'E'};
        int n3 = 2;
        System.out.println("\nCase 3: Overflow (Many tasks)");
        System.out.println("Input: A:3, B:3, C:3, D:2, E:1 (Total 12)");
        System.out.println("Expected: 12 (No idle needed)");
        System.out.println("Greedy Result: " + solution.leastInterval(tasks3, n3));
        System.out.println("Math Result:   " + solution.leastInterval_math(tasks3, n3));
        System.out.println("Heap Result:   " + solution.leastInterval_heap(tasks3, n3));
    }
}
