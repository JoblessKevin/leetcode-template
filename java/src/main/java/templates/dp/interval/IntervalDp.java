package templates.dp.interval;

/**
 * 區間 DP (Interval DP) 通用模板
 * -------------------------------------------------------
 * 核心思想：大區間的最優解依賴於小區間的最優解。
 * 狀態定義：dp[i][j] = 處理區間 [i, j] 的最優代價。
 * 複雜度：
 * - 時間：O(N^3) (三層迴圈)
 * - 空間：O(N^2) (二維陣列)
 */
public class IntervalDp {

    /**
     * 通用框架範例：以「石子合併 (Merge Stones)」為例
     * @param stones 輸入陣列 (可以是石子重量、矩陣維度、切點位置等)
     * @return 區間 [0, n-1] 的最優解
     */
    public int solve(int[] stones) {
        int n = stones.length;
        if (n == 0) return 0;

        // dp[i][j] 表示區間 i 到 j 的最優解
        int[][] dp = new int[n][n];

        // [Optional] 預處理前綴和 (Prefix Sum)
        // 僅適用於「合併類」題目，用於 O(1) 計算區間和
        int[] prefixSum = new int[n + 1];
        for (int i = 0; i < n; i++) prefixSum[i + 1] = prefixSum[i] + stones[i];

        // ==========================================
        // 核心迴圈：長度 -> 起點 -> 分割點
        // ==========================================
        
        // 1. 第一層：枚舉區間長度 len (從 2 開始，因為長度 1 通常是 base case)
        for (int len = 2; len <= n; len++) {
            
            // 2. 第二層：枚舉左端點 i
            for (int i = 0; i <= n - len; i++) {
                
                int j = i + len - 1; // 自動推導右端點 j
                
                dp[i][j] = Integer.MAX_VALUE; // 初始化找最小值

                // 3. 第三層：枚舉分割點 k
                // 將 [i...j] 切割成 [i...k] 和 [k+1...j]
                // k 的範圍通常是 [i, j-1]
                for (int k = i; k < j; k++) {
                    
                    // ---------------------------------------------------
                    // ★★★ 重點：根據題目類型修改 Cost 計算公式 ★★★
                    // ---------------------------------------------------

                    // 【變體 1：合併類 (Merge Type)】
                    // 如：石子合併 (Merge Stones)
                    // 特徵：最後一步合併會產生代價，代價通常是區間總和。
                    // 公式：left + right + sum(i, j)
                    int costMerge = dp[i][k] + dp[k+1][j] + getRangeSum(prefixSum, i, j);

                    /* // 【變體 2：矩陣連乘 (Matrix Chain Multiplication)】
                    // 如：給定矩陣維度 arr[]，求最小乘法次數
                    // 特徵：代價取決於分割點 k 的維度乘積。
                    // 假設矩陣 Mi 的維度是 arr[i] x arr[i+1]
                    // 公式：left + right + (arr[i] * arr[k+1] * arr[j+1])
                    int costMatrix = dp[i][k] + dp[k+1][j] + (arr[i] * arr[k+1] * arr[j+1]);
                    */

                    /*
                    // 【變體 3：切分/搜尋類 (Optimal BST / Cutting Sticks)】
                    // 如：切棍子 (Minimum Cost to Cut a Stick)
                    // 特徵：切下去的成本是當前棍子的總長度，加上左右兩段的成本。
                    // 公式：left + right + (sticks[j] - sticks[i])
                    // 注意：這類題目通常需要先對切點排序，並處理邊界座標。
                    int costCut = dp[i][k] + dp[k+1][j] + (coordinate[j] - coordinate[i]);
                    */

                    // 取當前分割點 k 的最小值
                    if (costMerge < dp[i][j]) {
                        dp[i][j] = costMerge;
                    }
                }
            }
        }

        return dp[0][n - 1];
    }

    /**
     * 輔助方法：O(1) 取得區間和
     */
    private int getRangeSum(int[] prefixSum, int i, int j) {
        return prefixSum[j + 1] - prefixSum[i];
    }

    public static void main(String[] args) {
        IntervalDp solver = new IntervalDp();
        int[] stones = {1, 2, 3};
        System.out.println("Merge Stones Cost: " + solver.solve(stones)); // Expected: 9
    }
}