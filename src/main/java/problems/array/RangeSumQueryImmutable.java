package problems.array;

/**
 * Your NumArray object will be instantiated and called as such: NumArray obj = new NumArray(nums);
 * int param_1 = obj.sumRange(left,right);
 */
public class RangeSumQueryImmutable {
    public static class NumArray {
        private int[] prefixSum;

        public NumArray(int[] nums) {
            prefixSum = new int[nums.length + 1];

            for (int i = 0; i < nums.length; i++) {
                prefixSum[i + 1] = prefixSum[i] + nums[i];
            }
        }

        public int sumRange(int left, int right) {
            return prefixSum[right + 1] - prefixSum[left];
        }
    }

    public static void main(String[] args) {
        // 準備測試資料
        int[] nums = {-2, 0, 3, -5, 2, -1};

        // 初始化物件
        NumArray obj = new NumArray(nums);

        // 測試案例 1: index 0 到 2 (-2 + 0 + 3 = 1)
        System.out.println("Sum range(0, 2): " + obj.sumRange(0, 2)); // 預期: 1

        // 測試案例 2: index 2 到 5 (3 + -5 + 2 + -1 = -1)
        System.out.println("Sum range(2, 5): " + obj.sumRange(2, 5)); // 預期: -1

        // 測試案例 3: index 0 到 5 (全部相加 = -3)
        System.out.println("Sum range(0, 5): " + obj.sumRange(0, 5)); // 預期: -3

        // 測試案例 4: 單一元素 (index 3, 值為 -5)
        System.out.println("Sum range(3, 3): " + obj.sumRange(3, 3)); // 預期: -5
    }
}
