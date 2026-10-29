package problems.slidingWindow;

import java.util.Arrays;

public class MinimumSizeSubarraySum {
    /**
     * Sliding window solution If nums contains negative numbers, this solution will not work.
     * (Using prefix sum and binary search is a better solution)
     */
    public int minSubArrayLen(int target, int[] nums) {
        int l = 0;
        int sum = 0;
        int minLen = Integer.MAX_VALUE;

        for (int r = 0; r < nums.length; r++) {
            sum += nums[r];

            while (sum >= target) {
                minLen = Math.min(minLen, r - l + 1);
                sum -= nums[l];
                l++;
            }
        }

        return (minLen == Integer.MAX_VALUE) ? 0 : minLen;
    }

    public int minSubArrayLen_bruteForce(int target, int[] nums) {
        int res = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            int curSum = 0, j = i;
            while (j < nums.length) {
                curSum += nums[j];
                if (curSum >= target) {
                    res = Math.min(res, j - i + 1);
                    break;
                }
                j++;
            }
        }

        return res == Integer.MAX_VALUE ? 0 : res;
    }

    /**
     * @formatter:off
     */
    public static void main(String[] args) {
        MinimumSizeSubarraySum solution = new MinimumSizeSubarraySum();

        int[][] testNums = {
                {2, 3, 1, 2, 4, 3},             // 2 ([4,3])
                {1, 4, 4},                      // 1 ([4])
                {1, 1, 1, 1, 1, 1},             // 0 (null)
                {1, 2, 3, 4, 5},                // 3 ([3,4,5] >= 11)
                {5, 1, 3, 5, 10, 7, 4, 9, 2, 8} // 2 ([10,7] >= 15)
        };

        int[] targets = {7, 4, 11, 11, 15};

        for (int i = 0; i < testNums.length; i++) {
            int result = solution.minSubArrayLen(targets[i], testNums[i]);
            System.out.println("nums = " + Arrays.toString(testNums[i])
                    + ", target = " + targets[i]
                    + " -> min length = " + result);
        }
    }
}
