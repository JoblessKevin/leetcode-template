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
}
