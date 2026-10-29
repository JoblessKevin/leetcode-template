package problems.array;

import java.util.Arrays;

public class ProductsOfArrayExceptSelf {
    public static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] results = new int[n];

        // 1. 左往右掃描 (計算左邊乘積)
        results[0] = 1;
        for (int i = 1; i < n; i++) {
            results[i] = results[i - 1] * nums[i - 1];
        }

        // 2. 右往左掃描 (計算右邊乘積並合併)
        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            // result[i] 目前存的是左邊乘積，乘以右邊乘積就是答案
            results[i] = results[i] * rightProduct;

            // 更新右邊乘積，準備給下一個左邊的格子用
            rightProduct *= nums[i];
        }

        return results;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        System.out.println("輸入陣列: " + Arrays.toString(nums));
        System.out.println("-------------------------------------------");

        int[] result = productExceptSelf(nums);

        System.out.println("-------------------------------------------");
        System.out.println("最終輸出: " + Arrays.toString(result));
    }
}
