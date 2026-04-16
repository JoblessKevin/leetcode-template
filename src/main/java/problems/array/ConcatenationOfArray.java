package problems.array;

import java.util.Arrays;

public class ConcatenationOfArray {
    public int[] getConcatenation(int[] nums) {
        int n = nums.length;
        int[] res = new int[n * 2];
        for (int i = 0; i < n; i++) {
            res[i] = nums[i];
            res[i + n] = nums[i];
        }

        return res;
    }

    public int[] getConcatenation_optimized(int[] nums) {
        int n = nums.length;
        int[] res = new int[2 * n];

        System.arraycopy(nums, 0, res, 0, n);
        System.arraycopy(nums, 0, res, n, n);

        return res;
    }

    public static void main(String[] args) {
        ConcatenationOfArray solver = new ConcatenationOfArray();

        // 測試案例
        int[] testInput = {1, 2, 1};
        System.out.println("測試輸入陣列: " + Arrays.toString(testInput));
        System.out.println("期待結果: [1, 2, 1, 1, 2, 1]");
        System.out.println("-------------------------------------");

        // 測試
        int[] result = solver.getConcatenation(testInput);
        System.out.println("方法 1 (Bitwise) 結果: " + Arrays.toString(result));

        System.out.println("-------------------------------------");
        System.out.println("驗證完成。");
    }
}
