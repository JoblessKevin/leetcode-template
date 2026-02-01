package problems.gameTheory;

import java.util.Arrays;

public class FinalElementAfterSubarrayDeletions {
    // 這是你的 Solution 解法
    public static int finalElementAfterSubarrayDeletions(int[] nums) {
        // 邏輯:
        // Alice 作為先手，有權利刪除 nums[1...end] 只留 nums[0]
        // 或者刪除 nums[0...end-1] 只留 nums[end]
        // 因為 Bob 會盡力讓結果變小，Alice 把決定權交給 Bob 是不明智的。
        // 所以 Alice 的最佳策略就是直接在第一回合結束遊戲，拿走頭尾中最大的那個。

        if (nums == null || nums.length == 0)
            return 0; // 防禦性編程
        if (nums.length == 1)
            return nums[0];

        return Math.max(nums[0], nums[nums.length - 1]);
    }

    public static void main(String[] args) {
        System.out.println("=== Q2. Final Element After Subarray Deletions 測試 ===");

        // Case 1: 題目範例
        // Alice 選 max(1, 2) = 2
        test(new int[] {1, 5, 2}, 2);

        // Case 2: 只有一個元素 (邊界測試)
        // 遊戲直接結束，就是它自己
        test(new int[] {10}, 10);

        // Case 3: 只有兩個元素
        // Alice 只能刪掉其中一個，當然選留大的
        test(new int[] {3, 8}, 8);
        test(new int[] {9, 2}, 9);

        // Case 4: 第一個元素最大
        // Alice 直接刪掉後面全部，保留 100
        test(new int[] {100, 5, 1, 20}, 100);

        // Case 5: 最後一個元素最大
        // Alice 直接刪掉前面全部，保留 99
        test(new int[] {5, 1, 20, 99}, 99);

        // Case 6: 中間有超級大的數字 (陷阱題)
        // Alice 如果試圖留中間的 1000，Bob 會在下一輪把它刪掉。
        // Alice 只能在頭尾 (1, 3) 之間選，選 3。
        test(new int[] {1, 1000, 3}, 3);

        System.out.println("=== 測試結束 ===");
    }

    // --- 測試輔助工具 ---
    public static void test(int[] nums, int expected) {
        int result = finalElementAfterSubarrayDeletions(nums);
        String arrayStr = Arrays.toString(nums);

        if (result == expected) {
            System.out.printf("[PASS] Input: %-15s -> Output: %d%n", arrayStr, result);
        } else {
            System.out.printf("[FAIL] Input: %-15s%n", arrayStr);
            System.out.printf("       Expected: %d%n", expected);
            System.out.printf("       Got:      %d%n", result);
        }
    }
}
