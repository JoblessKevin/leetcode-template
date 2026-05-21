package problems.array;

public class ProductsOfArrayExceptSelf {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        // 1. 左往右掃描 (計算左邊乘積)
        result[0] = 1;
        for (int i = 1; i < n; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }

        // 2. 右往左掃描 (計算右邊乘積並合併)
        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            // result[i] 目前存的是左邊乘積，乘以右邊乘積就是答案
            result[i] = result[i] * rightProduct;

            // 更新右邊乘積，準備給下一個左邊的格子用
            rightProduct *= nums[i];
        }

        return result;
    }
}
