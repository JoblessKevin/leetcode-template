package problems.greedy;

/**
 * Kadane's Algorithm: 對於任何一個位置，我都要決定：我要加入之前的隊伍，還是從我自己重新開始？
 * 
 * Time: O(n), Space: O(1)
 */
public class MaximumSubarray {
    public int maxSubArray(int[] nums) {
        int temp = 0;
        int max = Integer.MIN_VALUE;
        for (int num : nums) {
            temp += num;
            max = Math.max(max, temp);
            if (temp < 0) {
                temp = 0;
            }
        }
        return max;
    }

    public int maxSubArray_kadane(int[] nums) {
        int currentSum = 0;
        int maxSum = Integer.MIN_VALUE;

        for (int num : nums) {
            // Greedy 的核心決策：
            // 如果之前的 currentSum 是負的，我加上它只會變小，那我不如「重新開始」
            // 所以我選 (目前的數字) 和 (之前的累積 + 目前的數字) 之間較大的那個
            currentSum = Math.max(num, currentSum + num);

            // 記錄我們看過的最高紀錄
            maxSum = Math.max(maxSum, currentSum);
        }
        return maxSum;
    }

    public static void main(String[] args) {
        MaximumSubarray solver = new MaximumSubarray();

        int[] nums1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Test Case 1: " + java.util.Arrays.toString(nums1));
        System.out.println("Result: " + solver.maxSubArray(nums1)); // 應為 6

        int[] nums2 = {1};
        System.out.println("Test Case 2: " + java.util.Arrays.toString(nums2));
        System.out.println("Result: " + solver.maxSubArray(nums2)); // 應為 1

        int[] nums3 = {5, 4, -1, 7, 8};
        System.out.println("Test Case 3: " + java.util.Arrays.toString(nums3));
        System.out.println("Result: " + solver.maxSubArray(nums3)); // 應為 23
    }
}
