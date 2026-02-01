package problems.dp;

import java.util.Arrays;

public class LongestAlternatingSubarrayAfterRemovingAtMostOneElement {
    public int longestAlternating(int[] nums) {
        int n = nums.length;
        if (n == 1)
            return 1;

        // dp[i][0] : 以 i 結尾，且 nums[i] < nums[i-1] (下降) 的長度
        // dp[i][1] : 以 i 結尾，且 nums[i] > nums[i-1] (上升) 的長度
        int[][] left = new int[n][2];

        // 初始化：每個數字自己本身長度至少是 1
        for (int i = 0; i < n; i++) {
            left[i][0] = 1;
            left[i][1] = 1;
        }

        // 1. 計算左邊過來的長度 (Left Pass)
        int maxLen = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[i - 1]) {
                left[i][0] = left[i - 1][1] + 1; // 這次下降，上次就要是上升
            } else if (nums[i] > nums[i - 1]) {
                left[i][1] = left[i - 1][0] + 1; // 這次上升，上次就要是下降
            }
            maxLen = Math.max(maxLen, Math.max(left[i][0], left[i][1]));
        }

        // 2. 計算右邊過來的長度 (Right Pass)
        // right[i][0] : 從 i 開始，且 nums[i] > nums[i+1] (下一步是下降)
        // right[i][1] : 從 i 開始，且 nums[i] < nums[i+1] (下一步是上升)
        int[][] right = new int[n][2];
        for (int i = 0; i < n; i++) {
            right[i][0] = 1;
            right[i][1] = 1;
        }

        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] > nums[i + 1]) { // 下一步是下降 (當前 > 下一個)
                right[i][0] = right[i + 1][1] + 1;
            } else if (nums[i] < nums[i + 1]) { // 下一步是上升 (當前 < 下一個)
                right[i][1] = right[i + 1][0] + 1;
            }
        }

        // 3. 嘗試刪除每個位置 i (枚舉橋樑)
        // 我們檢查 nums[i-1] 和 nums[i+1] 能不能接起來
        for (int i = 1; i < n - 1; i++) {
            // 情況 A: 刪除 i 後，變成 ... (i-1) < (i+1) ... (模擬上升)
            // 所以 i-1 前面必須是下降結尾，i+1 後面必須是下降開頭
            if (nums[i - 1] < nums[i + 1]) {
                maxLen = Math.max(maxLen, left[i - 1][0] + right[i + 1][0]);
            }

            // 情況 B: 刪除 i 後，變成 ... (i-1) > (i+1) ... (模擬下降)
            // 所以 i-1 前面必須是上升結尾，i+1 後面必須是上升開頭
            if (nums[i - 1] > nums[i + 1]) {
                maxLen = Math.max(maxLen, left[i - 1][1] + right[i + 1][1]);
            }
        }

        return maxLen;
    }

    public static void main(String[] args) {
        System.out.println("=== Q4. Longest Alternating Subarray Test ===");

        LongestAlternatingSubarrayAfterRemovingAtMostOneElement sol =
                                        new LongestAlternatingSubarrayAfterRemovingAtMostOneElement();

        // Case 1: 題目範例 (不需要刪除也能達到最長，或者刪除沒幫助)
        // [2, 1, 3, 2] -> 下, 上, 下 -> 本身就是完美的長度 4
        test(sol, new int[] {2, 1, 3, 2}, 4);

        // Case 2: 單純遞增 (Strictly Increasing)
        // [1, 2, 3, 4] -> 最長交替只有 [1, 2] 或 [2, 3] ... 長度為 2
        // 刪除任何一個變成 [1, 3, 4] 還是遞增，無法變長
        test(sol, new int[] {1, 2, 3, 4}, 2);

        // Case 3: 透過刪除連起來 (The Bridge Case)
        // [10, 20, 5, 22, 25, 20]
        // 原本左邊: [10, 20, 5] (上, 下) -> 長度 3
        // 原本右邊: [25, 20] (下) -> 長度 2
        // 中間的 22 破壞了節奏 (5 -> 22 是上, 22 -> 25 也是上)
        // 刪除 22 後: [10, 20, 5, 25, 20]
        // 接縫處: 5 < 25 (上)
        // 驗證: 10(上)20(下)5(上)25(下)20 -> 完美連接！長度 5
        test(sol, new int[] {10, 20, 5, 22, 25, 20}, 5);

        // Case 4: 只需要刪除，不需要合併 (邊界刪除)
        // [100, 1, 2, 1]
        // 這裡 100 是老鼠屎。刪除 100 後變成 [1, 2, 1] -> 長度 3
        // 或者原本 [1, 2, 1] 就是 3。
        test(sol, new int[] {100, 1, 2, 1}, 3);

        // Case 5: 平坦的數字 (Flat)
        // [5, 5, 5] -> 交替必須是嚴格大於或小於，不能等於。
        // 最長只能是 1 (單個元素)
        test(sol, new int[] {5, 5, 5}, 1);

        // Case 6: 只有兩個元素
        test(sol, new int[] {1, 10}, 2);

        // Case 7: 峰谷連接測試 (Merge Logic Check)
        // [50, 10, 50, 888, 12, 50]
        // 刪除 888
        // 左邊 [50, 10, 50] (結尾是上 10->50) -> left[2][1]
        // 右邊 [12, 50] (開頭是上 12->50) -> right[4][1]
        // 接縫: 50 > 12 (模擬下降)
        // 檢查: 左邊結尾是上(OK), 右邊開頭是上(OK)
        // 結果: 50, 10, 50, 12, 50 -> 長度 5
        test(sol, new int[] {50, 10, 50, 888, 12, 50}, 5);

        System.out.println("=== 測試結束 ===");
    }

    // --- 測試輔助工具 ---
    private static void test(LongestAlternatingSubarrayAfterRemovingAtMostOneElement sol,
                                    int[] nums, int expected) {
        int result = sol.longestAlternating(nums);
        String arrayStr = Arrays.toString(nums);
        if (arrayStr.length() > 50)
            arrayStr = arrayStr.substring(0, 47) + "...]";

        if (result == expected) {
            System.out.printf("[PASS] Output: %d | Input: %s%n", result, arrayStr);
        } else {
            System.out.printf("[FAIL] Expected: %d, Got: %d | Input: %s%n", expected, result,
                                            arrayStr);
        }
    }
}
