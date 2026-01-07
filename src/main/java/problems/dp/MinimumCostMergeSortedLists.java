package problems.dp;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Bitmask DP, Time O(3^N + L * 2^N), Space: O(L * 2^N)
 * NOT Greedy + Priority Queue
 */
public class MinimumCostMergeSortedLists {
    public long minMergeCost(int[][] lists) {
        // 1. 過濾空陣列並轉換為 List 方便處理
        List<int[]> validLists = new ArrayList<>();
        for (int[] arr : lists) {
            if (arr != null && arr.length > 0) {
                validLists.add(arr);
            }
        }
        
        int n = validLists.size();
        if (n <= 1) return 0; // 只有一個或沒有，不用合併
        
        int limit = 1 << n; // 狀態總數 2^N
        long[] dp = new long[limit];
        Arrays.fill(dp, Long.MAX_VALUE);
        
        // 用於緩存每個 mask 合併後的結果，以便計算中位數
        // 注意：如果 N 很大這會耗費記憶體，但題目既然是合併類，N 通常 <= 14
        int[][] mergedArrays = new int[limit][];
        int[] medians = new int[limit];
        int[] lengths = new int[limit];
        
        // 2. 初始化 Base Cases (單個鏈表的狀態)
        for (int i = 0; i < n; i++) {
            int mask = 1 << i;
            dp[mask] = 0; // 已經是單個鏈表，成本為 0
            mergedArrays[mask] = validLists.get(i);
            lengths[mask] = validLists.get(i).length;
            medians[mask] = getMedian(validLists.get(i));
        }
        
        // 3. 開始 Bitmask DP 迭代
        // 從小的 mask 遍歷到大的 mask
        for (int mask = 1; mask < limit; mask++) {
            // 如果是 2 的冪次 (只有一個 bit)，已經初始化過，跳過
            if ((mask & (mask - 1)) == 0) continue;
            
            // --- 步驟 A: 構建當前 mask 的合併後陣列 ---
            // 我們可以從任意一種拆分來構建陣列 (內容是一樣的)
            // 為了方便，我們取 lowest bit 和剩下的部分來合併
            int lowBit = Integer.numberOfTrailingZeros(mask);
            int prevMask = mask ^ (1 << lowBit);
            int singleMask = 1 << lowBit;
            
            // 這裡必須真實地合併陣列，因為我們需要計算新的中位數
            mergedArrays[mask] = merge(mergedArrays[prevMask], mergedArrays[singleMask]);
            lengths[mask] = mergedArrays[mask].length;
            medians[mask] = getMedian(mergedArrays[mask]);
            
            // --- 步驟 B: 計算最小成本 DP ---
            // 嘗試所有可能的子集拆分 (Submask)
            // 優化技巧：只遍歷 sub，使得 highBit 包含在 sub 中
            // 這樣可以避免重複計算 (A, B) 和 (B, A)
            int highBit = Integer.highestOneBit(mask);
            
            // 遍歷 mask 的所有子集
            for (int sub = (mask - 1) & mask; sub > 0; sub = (sub - 1) & mask) {
                // 強制 sub 包含最高位，減少一半運算量
                if ((sub & highBit) == 0) continue;
                
                int other = mask ^ sub;
                
                // 狀態轉移方程
                long currentCost = dp[sub] + dp[other] + 
                                   lengths[sub] + lengths[other] + 
                                   Math.abs(medians[sub] - medians[other]);
                
                if (currentCost < dp[mask]) {
                    dp[mask] = currentCost;
                }
            }
        }
        
        return dp[limit - 1];
    }
    
    // 輔助方法：合併兩個有序陣列
    private int[] merge(int[] a, int[] b) {
        int[] res = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) res[k++] = a[i++];
            else res[k++] = b[j++];
        }
        while (i < a.length) res[k++] = a[i++];
        while (j < b.length) res[k++] = b[j++];
        return res;
    }
    
    // 輔助方法：計算中位數
    private int getMedian(int[] arr) {
        // 題目定義：偶數長度取左邊中間
        return arr[(arr.length - 1) / 2];
    }

    // ==========================================
    // Test case
    // ==========================================
    public static void main(String[] args) {
        MinimumCostMergeSortedLists sol = new MinimumCostMergeSortedLists();
        
        int[][] lists = {{7, 10, 10}, {4}, {2, 6, 10}};
        System.out.println("Result 1: " + sol.minMergeCost(lists)); // 預期輸出 18
        
        int[][] lists2 = {{1, 3, 5}, {2, 4}, {6, 7, 8}};
        System.out.println("Result 2: " + sol.minMergeCost(lists2)); // 預期輸出 18

        int[][] lists3 = {{1, 1, 5},{1, 4, 7, 8}};
        System.out.println("Result 3: " + sol.minMergeCost(lists3)); // 預期輸出 10

        int[][] lists4 = {{1}, {3}};
        System.out.println("Result 4: " + sol.minMergeCost(lists4)); // 預期輸出 4

        int[][] lists5 = {{1}, {1}};
        System.out.println("Result 5: " + sol.minMergeCost(lists5)); // 預期輸出 2
    }
}