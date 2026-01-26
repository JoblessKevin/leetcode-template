package templates.dp.linear;

import java.util.Arrays;

/**
 * e.g. Combination Sum IV
 * 線性 DP (Linear DP) 通用模板
 * 適用場景：
 * 1. 狀態只依賴於前幾個狀態 (e.g., dp[i] from dp[i-1], dp[i-2]...)
 * 2. 問題可以被分解為子問題 (Optimal Substructure)
 * 3. 有重疊子問題 (Overlapping Subproblems)
 */
public class GeneralLinearDP {

    // ==========================================
    //  模板 1: Top-Down (Recursion + Memoization)
    //  優點：直觀、只計算必要狀態
    // ==========================================
    int[] memo;

    public int solveTopDown(int target, int[] nums) {
        // 1. 初始化備忘錄 (根據題目範圍決定大小)
        memo = new int[target + 1]; 
        Arrays.fill(memo, -1); // -1 代表未計算 (Unvisited)
        
        return helper(target, nums);
    }

    private int helper(int state, int[] nums) {
        // A. Base Cases (終止條件)
        if (state == 0) return 1; // 範例：剛好湊滿
        if (state < 0) return 0;  // 範例：不合法狀態
        
        // B. 查表 (Memoization Check)
        if (memo[state] != -1) {
            return memo[state];
        }

        // C. 狀態轉移 (State Transition) - 這部分依題目而變
        int result = 0;
        for (int num : nums) {
             // 剪枝 (Pruning) - 可選
            if (state - num < 0) break; 
            
            // 遞迴關係式
            result += helper(state - num, nums); 
        }

        // D. 填表 (Store Result)
        return memo[state] = result;
    }

    // ==========================================
    //  模板 2: Bottom-Up (Iteration / Tabulation)
    //  優點：無 Stack Overflow 風險、空間控制明確
    // ==========================================
    public int solveBottomUp(int target, int[] nums) {
        // 1. 建立 DP 表
        // dp[i] 的定義依題目而定 (e.g., 湊到 i 的方法數、到達 i 的最小成本...)
        int[] dp = new int[target + 1];
        
        // 2. Base Cases 初始化
        dp[0] = 1; 

        // 3. 狀態遍歷 (由小到大)
        for (int i = 1; i <= target; i++) {
            
            // 4. 決策/轉移 (Transition)
            for (int num : nums) {
                if (i - num < 0) break; // 剪枝
                
                // 狀態轉移方程式 (Recurrence Relation)
                // e.g., dp[i] += dp[i - num];  (求組合數/排列數)
                // e.g., dp[i] = min(dp[i], dp[i-num] + 1); (求最小值)
                // e.g., dp[i] = dp[i] || dp[i-num]; (求可行性 boolean)
                dp[i] += dp[i - num];
            }
        }

        return dp[target];
    }
}