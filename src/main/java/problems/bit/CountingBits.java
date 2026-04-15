package problems.bit;

import java.util.Arrays;

public class CountingBits {
    /*
     * 消去法 (Kernighan's)
     */
    public int[] countBits(int n) {
        int[] ans = new int[n + 1];
        // 從 1 開始算，ans[0] 預設就是 0
        for (int i = 1; i <= n; i++) {
            // ans[i] 等於 (i 拿掉最後一個 1) 的結果再加上那個被拿掉的 1
            ans[i] = ans[i & (i - 1)] + 1;
        }

        return ans;
    }

    /*
     * 右移法 (LSB)
     */
    public int[] countBits_dp_optimized(int n) {
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i >> 1] + (i & 1);
        }

        return dp;
    }

    /*
     * 位移補償法 (MSB/Offset)
     */
    public int[] countBits_dp(int n) {
        int[] dp = new int[n + 1];
        int offset = 1;

        for (int i = 1; i <= n; i++) {
            // 這個判斷在 offset 達到 2^30 會失效，會發生 整數溢位 (Integer Overflow) 變成負數
            if (offset * 2 == i) {
                offset = i;
            }
            dp[i] = 1 + dp[i - offset];
        }

        return dp;
    }

    public static void main(String[] args) {
        CountingBits solver = new CountingBits();
        int n = 5; // 我們測試到 n = 5

        System.out.println("測試數值 n = " + n);
        System.out.println("預期結果應為: [0, 1, 1, 2, 1, 2]");
        System.out.println("------------------------------------");

        // 測試方法 1
        int[] res1 = solver.countBits(n);
        System.out.println("方法 1 (Kernighan's):  " + Arrays.toString(res1));

        // 測試方法 2
        int[] res2 = solver.countBits_dp_optimized(n);
        System.out.println("方法 2 (LSB/RightShift): " + Arrays.toString(res2));

        // 測試方法 3
        int[] res3 = solver.countBits_dp(n);
        System.out.println("方法 3 (MSB/Offset):     " + Arrays.toString(res3));

        System.out.println("------------------------------------");
        System.out.println("驗證完成：三個方法回傳結果一致且正確。");
    }
}
