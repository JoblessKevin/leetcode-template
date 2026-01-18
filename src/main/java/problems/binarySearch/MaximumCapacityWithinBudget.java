package problems.binarySearch;

import java.util.Arrays;

public class MaximumCapacityWithinBudget {
    public int maxCapacity(int[] costs, int[] capacity, int budget) {
        int n = costs.length;
        // 1. 將資料打包成 Pair (cost, capacity)
        int[][] items = new int[n][2];
        for (int i = 0; i < n; i++) {
            items[i][0] = costs[i]; // cost
            items[i][1] = capacity[i]; // capacity
        }

        // 2. 依照 Cost 進行排序
        Arrays.sort(items, (a, b) -> Integer.compare(a[0], b[0]));

        // 3. 預處理 Prefix Max Capacity
        // preMax[i] 代表: items[0] 到 items[i] 之中，最大的 capacity 是多少
        long[] preMax = new long[n];
        preMax[0] = items[0][1];
        for (int i = 1; i < n; i++) {
            preMax[i] = Math.max(preMax[i - 1], items[i][1]);
        }

        long maxTotalCap = 0;

        // 4. 開始遍歷每一台機器 (嘗試選 1 台 或 2 台)
        for (int i = 0; i < n; i++) {
            long currentCost = items[i][0];
            long currentCap = items[i][1];

            // 情況 A: 只選這一台
            if (currentCost < budget) {
                maxTotalCap = Math.max(maxTotalCap, currentCap);
            } else {
                // 因為已經排序過，如果這台都買不起，後面的更買不起，直接 break
                break;
            }

            // 情況 B: 選這一台 (i) + 前面某一台 (j)
            // 我們需要找 index < i 且 items[index].cost < (budget - currentCost)
            int target = budget - items[i][0];

            // 使用二分搜尋找到「第一個 >= target」的位置，再減 1 就是我們要的範圍
            // 搜尋範圍限制在 0 到 i-1 之間
            int searchLimit = binarySearch(items, i, target);

            if (searchLimit != -1) {
                // searchLimit 是符合條件的最右邊 index，preMax[searchLimit] 就是該範圍內最大的 capacity
                maxTotalCap = Math.max(maxTotalCap, currentCap + preMax[searchLimit]);
            }
        }

        return (int) maxTotalCap;
    }

    // 尋找陣列中第一個 cost >= target 的位置，回傳該位置的前一個索引 (也就是小於 target 的最後一個位置)
    // 範圍限制在 [0, high - 1]
    private int binarySearch(int[][] items, int high, int target) {
        int l = 0, r = high - 1;
        int ans = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (items[mid][0] < target) {
                ans = mid; // 目前這個符合條件，試試看有沒有更靠右的
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }

    // ==========================================
    // Test case
    // ==========================================
    public static void main(String[] args) {
        MaximumCapacityWithinBudget solver = new MaximumCapacityWithinBudget();

        System.out.println("=== 開始測試 MaximumCapacityWithinBudget ===\n");

        // Test Case 1: LeetCode 範例 (一般情況)
        // 選 (Cost 3, Cap 5) 和 (Cost 1, Cap 7) -> Cost 4 < 13, Total Cap 12
        runTest(solver, new int[] {1, 7, 3}, new int[] {7, 3, 5}, 13, 12, "Test Case 1 (Standard)");

        // Test Case 2: 嚴格小於 Budget 的邊界測試
        // 之前卡住的測資：Cost 4+3 = 7 < 8 (合法), Cost 5+3 = 8 !< 8 (不合法)
        // 應選 (Cost 4, Cap 1) + (Cost 3, Cap 7) = Cap 8
        runTest(solver, new int[] {4, 8, 5, 3}, new int[] {1, 5, 2, 7}, 8, 8,
                                        "Test Case 2 (Boundary: Strictly Less)");

        // Test Case 3: 單選一個比選兩個好
        // 預算 10，有一個神機 Cost 9 Cap 100，其他都很爛
        // 選 (9) -> Cap 100. 如果硬湊兩個可能反而變小或買不起
        runTest(solver, new int[] {9, 1, 1}, new int[] {100, 1, 1}, 10, 100,
                                        "Test Case 3 (Single Best)");

        // Test Case 4: 什麼都買不起
        // 預算 5，最便宜的要 5 (不滿足 < 5)
        runTest(solver, new int[] {5, 10, 20}, new int[] {10, 20, 30}, 5, 0,
                                        "Test Case 4 (Too Poor)");

        // Test Case 5: 預算超多，選最強的兩個
        runTest(solver, new int[] {1, 2, 3, 4}, new int[] {10, 20, 30, 40}, 100, 70, // 30 + 40
                                        "Test Case 5 (Rich Budget)");
    }

    public static void runTest(MaximumCapacityWithinBudget solver, int[] costs, int[] capacity,
                                    int budget, int expected, String testName) {
        System.out.println("Running: " + testName);
        System.out.println("Costs: " + Arrays.toString(costs));
        System.out.println("Capacity: " + Arrays.toString(capacity));
        System.out.println("Budget: " + budget);

        long startTime = System.nanoTime();
        int result = solver.maxCapacity(costs, capacity, budget);
        long endTime = System.nanoTime();

        System.out.println("Expected: " + expected + ", Got: " + result);

        if (result == expected) {
            double timeInMs = (endTime - startTime) / 1_000_000.0;
            System.out.println("PASS (Time: " + timeInMs + " ms)");
        } else {
            System.err.println("FAIL");
        }
        System.out.println("--------------------------------------------------");
    }
}
