package problems.bit;

import java.util.Arrays;

public class MissingNumber {
    public int missingNumber_XOR(int[] nums) {
        int n = nums.length;
        // 初始化為 n，因為迴圈索引 i 只到 n-1
        int res = n;

        for (int i = 0; i < n; i++) {
            // 同時對索引 i 和數值 nums[i] 進行 XOR
            res ^= i ^ nums[i];
        }
        return res;
    }

    public int missingNumber_Math(int[] nums) {
        int n = nums.length;
        // 理論上 0 到 n 的總和
        int expectedSum = n * (n + 1) / 2;

        int actualSum = 0;
        for (int num : nums) {
            actualSum += num;
        }

        return expectedSum - actualSum;
    }

    public int missingNumber_CyclicSort(int[] nums) {
        int n = nums.length;
        int i = 0;
        while (i < n) {
            // 如果當前數字不是 n，且它不在正確的位置上，就交換
            if (nums[i] < n && nums[i] != i) {
                int temp = nums[nums[i]];
                nums[nums[i]] = nums[i];
                nums[i] = temp;
            } else {
                i++;
            }
        }

        // 最後掃描一遍，哪個人的位置不對，那個索引就是缺少的數字
        for (int j = 0; j < n; j++) {
            if (nums[j] != j)
                return j;
        }

        return n; // 如果前面都對，那缺的就是最後一個數 n
    }

    public static void main(String[] args) {
        MissingNumber solver = new MissingNumber();

        // 測試案例
        int[] testInput = {9, 6, 4, 2, 3, 5, 7, 0, 1};
        System.out.println("測試輸入陣列: " + Arrays.toString(testInput));
        System.out.println("期待結果: 8");
        System.out.println("------------------------------------");

        // 測試 XOR
        System.out.println("方法 1 (XOR) 結果:      " + solver.missingNumber_XOR(testInput));

        // 測試 Math
        System.out.println("方法 2 (Math) 結果:     " + solver.missingNumber_Math(testInput));

        // 測試 Cyclic Sort (因為會改動陣列，我們傳入副本)
        int[] copyOfInput = testInput.clone();
        System.out.println("方法 3 (Cyclic) 結果:   " + solver.missingNumber_CyclicSort(copyOfInput));

        System.out.println("------------------------------------");
        System.out.println("驗證完成。");
    }
}
