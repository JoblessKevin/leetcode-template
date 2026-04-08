package problems.dp.interval;

/**
 * @formatter:off
 * 這題非常困難，無法壓縮，DP 關係碎片化
 * 區間型 DP 的經典題型 (Interval DP)
 * 核心技巧：從「最後一刀」反推
 * 正常思路：
 * 想像我們從左到右打氣，當打到第 i 個氣球時，它會影響到 i+1 和 i-1，這會導致狀態很難轉移。
 * 逆向思路 (反推)：
 * 想像我們從右到左把氣球戳破。當我們決定戳破第 k 個氣球時，我們必須確保 k 左右兩邊的氣球都已經被戳破了。
 * 這樣一來，k 左右兩邊的氣球就不會再影響 k 的分數了，狀態轉移就變得非常乾淨。
 * @formatter:on
 */
public class BurstBalloons {
    /**
     * Time: O(n^3), Space: O(n^2)
     */
    public int maxCoins(int[] nums) {
        int n = nums.length;

        // 1. 建立邊界 (Padding)
        // 為了方便處理邊界情況，我們在頭尾各補上 1
        int[] padded = new int[n + 2];
        padded[0] = 1;
        padded[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            padded[i + 1] = nums[i];
        }

        // dp[i][j] 代表戳破 i+1 到 j-1 這些氣球所能獲得的最大分數
        // 注意：i 和 j 是「邊界」索引，不包含在內
        int[][] dp = new int[n + 2][n + 2];

        // 2. 遍歷長度 (Length)
        // l 代表區間長度 (從 1 開始，因為長度為 1 的區間 (i+1, i+1) 沒氣球，分數為 0)
        for (int l = 2; l <= n + 1; l++) {
            // 3. 遍歷起點 (Start)
            // i 是左邊界，j 是右邊界 (j = i + l)
            for (int i = 0; i <= n + 1 - l; i++) {
                int j = i + l;

                // 4. 遍歷「最後一刀」 (k)
                // k 是在 i 和 j 之間最後被戳破的氣球
                for (int k = i + 1; k < j; k++) {
                    // 分數 = 左邊區間 + 右邊區間 + 戳破 k 時獲得的分數
                    int coins = dp[i][k] + dp[k][j] + padded[i] * padded[k] * padded[j];
                    dp[i][j] = Math.max(dp[i][j], coins);
                }
            }
        }

        // 結果是戳破 0 到 n+1 之間的氣球 (也就是全部)
        return dp[0][n + 1];
    }

    public static void main(String[] args) {
        BurstBalloons solver = new BurstBalloons();

        int[] nums1 = {3, 1, 5, 8};
        System.out.println("Test Case 1: " + java.util.Arrays.toString(nums1));
        System.out.println("Result: " + solver.maxCoins(nums1)); // 應為 167

        int[] nums2 = {1, 5};
        System.out.println("Test Case 2: " + java.util.Arrays.toString(nums2));
        System.out.println("Result: " + solver.maxCoins(nums2)); // 應為 10
    }
}
