package problems.array;

import java.util.HashMap;

/**
 * Time: O(n) | Space: O(1) One-pass solution (Dutch National Flag Algorithm)
 */
public class SortColors {
    public void sortColors(int[] nums) {
        if (nums == null || nums.length <= 1)
            return;

        // 三個指針的職責：
        int left = 0; // [0, left) 區域全是 0 (紅色)
        int curr = 0; // [left, curr) 區域全是 1 (白色)
        int right = nums.length - 1; // (right, end] 區域全是 2 (藍色)

        while (curr <= right) {
            if (nums[curr] == 0) {
                // 發現紅球：把它丟到左邊邊界 left，換回來的一定是 1
                swap(nums, left, curr);
                left++;
                curr++;
            } else if (nums[curr] == 1) {
                // 發現白球：它本來就該在中間，curr 直接往前走
                curr++;
            } else { // nums[curr] == 2
                // 發現藍球：把它丟到右邊邊界 right
                swap(nums, curr, right);
                right--;
                // 【核心細節】：這裡 curr 不能加一！
                // 因為從 right 換過來的球是「未知」的，必須留在原地下一輪再檢查它
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public void sortColors_bruteforce(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for (int i = 0; i < nums.length; i++) {
            if (i < map.getOrDefault(0, 0)) {
                nums[i] = 0;
            } else if (i < map.getOrDefault(0, 0) + map.getOrDefault(1, 0)) {
                nums[i] = 1;
            } else {
                nums[i] = 2;
            }
        }
    }

    public static void main(String[] args) {
        SortColors sol = new SortColors();

        // 測試案例 1: 標準隨機分佈 (LeetCode 範例)
        int[] case1 = {2, 0, 2, 1, 1, 0};
        runTest("標準分佈", case1, sol);

        // 測試案例 2: 已經排好序 (測試穩定性)
        int[] case2 = {0, 1, 2};
        runTest("已經排序", case2, sol);

        // 測試案例 3: 完全倒序 (測試 high 的連續交換)
        int[] case3 = {2, 1, 0};
        runTest("完全倒序", case3, sol);

        // 測試案例 4: 只有單一顏色
        int[] case4 = {1, 1, 1};
        runTest("單一顏色", case4, sol);

        // 測試案例 5: 空陣列或單一元素 (邊界檢查)
        int[] case5 = {0};
        runTest("單一元素", case5, sol);
    }

    private static void runTest(String name, int[] nums, SortColors sol) {
        System.out.println("--- 測試: " + name + " ---");
        System.out.print("排序前: " + java.util.Arrays.toString(nums));
        sol.sortColors(nums);
        System.out.println(" -> 排序後: " + java.util.Arrays.toString(nums));

        // 簡單驗證結果是否正確
        boolean isSorted = true;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1])
                isSorted = false;
        }
        System.out.println("結果驗證: " + (isSorted ? "通過" : "失敗"));
        System.out.println();
    }
}
