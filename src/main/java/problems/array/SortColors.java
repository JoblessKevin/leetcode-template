package problems.array;

/**
 * Time: O(n) | Space: O(1) One-pass solution (Dutch National Flag Algorithm)
 */
public class SortColors {
    public void sortColors(int[] nums) {
        if (nums == null || nums.length <= 1)
            return;

        // 三個指針的職責：
        int low = 0; // [0, low) 區域全是 0 (紅色)
        int mid = 0; // [low, mid) 區域全是 1 (白色)
        int high = nums.length - 1; // (high, end] 區域全是 2 (藍色)

        while (mid <= high) {
            if (nums[mid] == 0) {
                // 發現紅球：把它丟到左邊邊界 low，換回來的一定是 1
                swap(nums, low, mid);
                low++;
                mid++;
            } else if (nums[mid] == 1) {
                // 發現白球：它本來就該在中間，mid 直接往前走
                mid++;
            } else { // nums[mid] == 2
                // 發現藍球：把它丟到右邊邊界 high
                swap(nums, mid, high);
                high--;
                // 【核心細節】：這裡 mid 不能加一！
                // 因為從 high 換過來的球是「未知」的，必須留在原地下一輪再檢查它
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
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
