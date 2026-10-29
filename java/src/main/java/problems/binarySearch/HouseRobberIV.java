package problems.binarySearch;

/**
 * @formatter:off
 * 使用二分搜尋法尋找最小的通緝熱度，並用貪心演算法進行驗證。
 * 時間複雜度: O(N log M)
 * - N 為 nums 的長度
 * - M 為搜尋範圍 (最大房價 - 最小房價，或 10^9)
 * 空間複雜度: O(1)
 * @formatter:on
 */
public class HouseRobberIV {
    public int minCapability(int[] nums, int k) {
        int left = 0;
        int right = 1_000_000_000;
        int ans = right;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canRob(nums, k, mid)) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

    private boolean canRob(int[] nums, int k, int maxVal) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= maxVal) {
                count++;

                if (count >= k) {
                    return true;
                }

                i++;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        HouseRobberIV solution = new HouseRobberIV();

        // --------------------------------------------------
        // 測試案例 1：經典範例 (偷 2 和 4)
        // --------------------------------------------------
        int[] nums1 = {2, 3, 5, 7, 4, 2};
        int k1 = 2;
        int result1 = solution.minCapability(nums1, k1);
        System.out.println("Test Case 1 (偷 2 和 4):");
        System.out.println("  你的答案: " + result1);
        System.out.println("  狀態: " + (result1 == 2 ? "正確" : "錯誤"));
        System.out.println("--------------------------------------------------");

        // --------------------------------------------------
        // 測試案例 2：需要偷 1000 (極限值)
        // --------------------------------------------------
        int[] nums2 = {2, 7, 9, 3, 1, 1000};
        int k2 = 2;
        int result2 = solution.minCapability(nums2, k2);
        System.out.println("Test Case 2 (需要偷 1000):");
        System.out.println("  你的答案: " + result2);
        System.out.println("  狀態: " + (result2 == 1000 ? "正確" : "錯誤"));
        System.out.println("--------------------------------------------------");

        // --------------------------------------------------
        // 測試案例 3：邊界條件 (只有一間別墅)
        // --------------------------------------------------
        int[] nums3 = {10};
        int k3 = 1;
        int result3 = solution.minCapability(nums3, k3);
        System.out.println("Test Case 3 (只有一間別墅):");
        System.out.println("  你的答案: " + result3);
        System.out.println("  狀態: " + (result3 == 10 ? "正確" : "錯誤"));
        System.out.println("--------------------------------------------------");
    }
}
