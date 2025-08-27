package templates.kadane;

public class Kadane {
    int maxSubArray(int[] nums) {
        int maxSum = nums[0], curSum = 0;
        for (int num : nums) {
            curSum = Math.max(num, curSum + num);
            maxSum = Math.max(maxSum, curSum);
        }
        return maxSum;
    }
}
