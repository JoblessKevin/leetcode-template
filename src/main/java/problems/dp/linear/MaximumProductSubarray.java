package problems.dp.linear;

/**
 * 找最大的連續 subarray 的乘積
 */
public class MaximumProductSubarray {
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // 初始化：當前最大值、當前最小值、全局最大值
        int currentMax = nums[0];
        int currentMin = nums[0];
        int result = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];

            // ⚡ 關鍵：如果當前數字是負數，最大值和最小值會互換
            // 例如：currentMax=6, num=-2 -> 6*-2=-12 (變小)
            // currentMin=-12, num=-2 -> -12*-2=24 (變大！)
            if (num < 0) {
                int temp = currentMax;
                currentMax = currentMin;
                currentMin = temp;
            }

            // 更新當前最大值和最小值
            // 要么從當前數字開始，要么延續之前的子陣列
            currentMax = Math.max(num, currentMax * num);
            currentMin = Math.min(num, currentMin * num);

            // 更新全局最大值
            result = Math.max(result, currentMax);
        }

        return result;
    }

    // 答案是 6 ，因為要連續，不能 2 * 4 = 8
    public static void main(String[] args) {
        MaximumProductSubarray maximumProductSubarray = new MaximumProductSubarray();
        int[] nums = {2, 3, -2, 4};
        System.out.println(maximumProductSubarray.maxProduct(nums));
    }
}
