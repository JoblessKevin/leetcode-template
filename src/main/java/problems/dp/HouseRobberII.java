package problems.dp;

import java.util.Arrays;

public class HouseRobberII {
    public int rob(int[] nums) {
        int n = nums.length;

        if (n == 0)
            return 0;

        if (n == 1)
            return nums[0];

        if (n == 2)
            return Math.max(nums[0], nums[1]);

        int maxA = robHelper(nums, 0, n - 2);
        int maxB = robHelper(nums, 1, n - 1);

        return Math.max(maxA, maxB);
    }

    private int robHelper(int[] nums, int start, int end) {
        int prev2 = 0;
        int prev1 = 0;

        // 從指定的 start 一路走到 end
        for (int i = start; i <= end; i++) {
            int current = Math.max(prev1, prev2 + nums[i]);
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }

    /**
     * @formatter:off
     * 思考方式：
     * 既然首尾不能同時偷，那就把問題拆成兩種情況：
     * 情況 A：偷第 0 間，但不偷最後一間 (範圍 0 ~ n-2)。
     * 情況 B：不偷第 0 間，但偷最後一間 (範圍 1 ~ n-1)。
     * 最後取這兩種情況的最大值。
     * 時間複雜度：O(N)
     * 空間複雜度：O(1)
     * @formatter:on
     */
    public int rob_mindSet(int[] nums) {
        int n = nums.length;
        if (n == 0)
            return 0;
        if (n == 1)
            return nums[0];

        // ==========================================
        // 情況 A：偷第 0 間 到 第 n-2 間
        // ==========================================
        int prev1_A = 0, prev2_A = 0;
        for (int i = 0; i <= n - 2; i++) {
            int current = Math.max(prev1_A, prev2_A + nums[i]);
            prev2_A = prev1_A;
            prev1_A = current;
        }
        int maxA = prev1_A; // 路線 A 的最高收益

        // ==========================================
        // 情況 B：偷第 1 間 到 第 n-1 間
        // ==========================================
        int prev1_B = 0, prev2_B = 0;
        for (int i = 1; i <= n - 1; i++) {
            int current = Math.max(prev1_B, prev2_B + nums[i]);
            prev2_B = prev1_B;
            prev1_B = current;
        }
        int maxB = prev1_B; // 路線 B 的最高收益

        // 最終對決
        return Math.max(maxA, maxB);
    }

    public static void main(String[] args) {
        HouseRobberII solution = new HouseRobberII();

        // @formatter:off
        // 準備我們的測試案例 (Test Cases)
        int[][] testCases = {
            {},                 // 邊界測試 1：空社區 (預期: 0)
            {5},                // 邊界測試 2：只有 1 間房子 (預期: 5)
            {2, 3},             // 邊界測試 3：只有 2 間房子 (預期: 3)
            {2, 3, 2},          // 經典測試 1：首尾衝突，只能偷中間 (預期: 3)
            {1, 2, 3, 1},       // 經典測試 2：避開首尾連線的陷阱 (預期: 4)
            {1, 2, 3},          // 基礎測試：(預期: 3)
            {2, 7, 9, 3, 1}     // 進階長度測試：(預期: 11)
        };
        // @formatter:on

        // 跑迴圈驗證每一個測試案例
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];

            // 讓你的兩個版本都跑跑看
            int resultHelper = solution.rob(nums);
            int resultMindSet = solution.rob_mindSet(nums);

            // 印出精美的測試報告
            System.out.println("Test Case " + (i + 1) + ": " + Arrays.toString(nums));
            System.out.println("模組化 rob() 結果: " + resultHelper);
            System.out.println("直觀版 rob_mindSet(): " + resultMindSet);

            // 驗證兩個方法算出來的答案是不是一模一樣
            if (resultHelper == resultMindSet) {
                System.out.println("狀態: 兩者一致，完美通過！");
            } else {
                System.out.println("狀態: 出現分歧，需要 Debug！");
            }
            System.out.println("--------------------------------------------------");
        }
    }
}
