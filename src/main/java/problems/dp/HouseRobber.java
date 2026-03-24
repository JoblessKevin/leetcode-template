package problems.dp;

/**
 * @formatter:off
 * 198. House Robber
 * 題目描述：
 * 你是一位專業的竊賊，計劃在同一條街上搶劫房屋。然而，相鄰的房屋裝有防盜系統，如果兩間相鄰的房屋同時被撬開，系統會報警。
 * 給定一個代表每個房屋金額的非負整數陣列 nums，計算你最高可以搶劫到的總金額，且不能搶劫相鄰的房屋。
 * * 核心概念：
 * 這是一個典型的「動態規劃 (DP)」問題，屬於「選擇與不選擇」的決策問題。
 * * 思考方式：
 * 考慮到第 i 間房屋時，我們面臨兩個選擇：
 * 選擇 A：搶劫第 i 間房屋。 則不能搶劫第 i-1 間，總金額 = nums[i] + 到達第 i-2 間的最大金額。
 * 選擇 B：不搶劫第 i 間房屋。 則總金額 = 到達第 i-1 間的最大金額。
 * 遞迴關係式：dp[i] = max(nums[i] + dp[i-2], dp[i-1])
 * * 演算法 (迭代優化)：
 * 1. 定義 Base Cases：
 * dp[0] = nums[0] (只有第一間，搶就對了)
 * dp[1] = max(nums[0], nums[1]) (兩間選比較有錢的)
 * 2. 迭代計算：從 i = 2 開始，利用前兩項的值計算當前項，直到 i = n-1。
 * 3. 空間優化：由於 dp[i] 只依賴於前兩項，我們不需要整個陣列，只需要兩個變數 (prev2, prev1) 即可，將空間複雜度從 O(N) 降為 O(1)。
 * * 時間複雜度：O(N)。我們只需要從 0 掃描到 n-1 一次。
 * 空間複雜度：O(1)。只使用固定數量的變數。
 * @formatter:on
 */
public class HouseRobber {
    public int rob_optimized_extreme(int[] nums) {
        int rob1 = 0;
        int rob2 = 0;
        for (int num : nums) {
            int temp = Math.max(num + rob1, rob2);
            rob1 = rob2;
            rob2 = temp;
        }
        return rob2;
    }

    /**
     * 最直觀的寫法 時間複雜度：O(N) 空間複雜度：O(N)
     */
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 0)
            return 0;

        if (n == 1)
            return nums[0];

        int[] dp = new int[n];

        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }

        return dp[n - 1];
    }

    /**
     * 保留緩衝區的寫法 時間複雜度：O(N) 空間複雜度：O(N)
     */
    public int rob_buffer(int[] nums) {
        int n = nums.length;

        if (n == 0)
            return 0;

        int[] dp = new int[n + 1];

        dp[0] = 0;
        dp[1] = nums[0];

        for (int i = 2; i <= n; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
        }

        return dp[n];
    }

    /**
     * 動態規劃 (Space Optimized) 💡 說明：觀察 dp[i] = max(nums[i] + dp[i-2], dp[i-1])，我們發現只需要前兩項的值，不需要整個陣列。
     * 這是最推薦的解法，因為它既快又省記憶體。 時間複雜度：O(N) 空間複雜度：O(1)
     */
    public int rob_optimized(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        if (nums.length == 1)
            return nums[0];

        // prev2 代表 dp[i-2]，初始是 dp[0]
        int prev2 = nums[0];
        // prev1 代表 dp[i-1]，初始是 dp[1]
        int prev1 = Math.max(nums[0], nums[1]);

        for (int i = 2; i < nums.length; i++) {
            // current = max(搶這間 + 兩間前的總和, 不搶這間 = 前一間的總和)
            int current = Math.max(prev1, nums[i] + prev2);

            // 狀態往前滾動
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }

    public static void main(String[] args) {
        HouseRobber solution = new HouseRobber();

        System.out.println("=== 測試案例 1：標準情況 ===");
        // 說明：
        // 1. 搶第 1 間 (2) + 第 3 間 (3) = 5
        // 2. 搶第 2 間 (4) = 4
        // 最大為 5
        int[] nums1 = {2, 7, 9, 3, 1};
        System.out.println("動態規劃: " + solution.rob(nums1) + " (預期: 12)");

        System.out.println("=== 測試案例 2：標準情況 ===");
        // 說明：
        // 1. 搶第 1 間 (1) + 第 3 間 (3) = 4
        // 2. 搶第 2 間 (4) = 4
        // 最大為 4
        int[] nums2 = {1, 2, 3, 1};
        System.out.println("動態規劃: " + solution.rob(nums2) + " (預期: 4)");

        System.out.println("=== 測試案例 3：邊界情況 (只有 1 間) ===");
        int[] nums3 = {10};
        System.out.println("動態規劃: " + solution.rob(nums3) + " (預期: 10)");

        System.out.println("=== 測試案例 4：邊界情況 (只有 2 間) ===");
        int[] nums4 = {2, 1};
        System.out.println("動態規劃: " + solution.rob(nums4) + " (預期: 2)");

        System.out.println("=== 測試案例 5：較長數字 ===");
        int[] nums5 = {2, 7, 9, 3, 1};
        System.out.println("動態規劃: " + solution.rob(nums5) + " (預期: 12)");
    }
}
