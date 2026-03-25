package problems.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InverseCoinChange {
    public List<Integer> findCoins(int[] numWays) {
        int n = numWays.length;
        int[] dp = new int[n + 1];

        // 1. 處理位移：把 1-indexed 的 numWays 塞進 0-indexed 的 dp
        dp[0] = 1;
        for (int i = 0; i < n; i++) {
            dp[i + 1] = numWays[i];
        }

        // 這裡直接宣告我們要回傳的 List
        List<Integer> coins = new ArrayList<>();

        // 2. 逆向剝皮：從左到右掃描金額
        for (int i = 1; i <= n; i++) {
            if (dp[i] > 0) {
                coins.add(i); // 抓到硬幣！

                // 🌟 最關鍵的逆向消除：從右往左扣
                for (int j = n; j >= i; j--) {
                    dp[j] -= dp[j - i];
                }
            } else if (dp[i] < 0) {
                // 如果扣到變負數，代表這個組合不合法，回傳空 List
                return new ArrayList<>();
            }
        }

        // 3. 終極驗證：合法的 numWays 剝到最後，dp 應該全部歸零 (除了 dp[0])
        for (int i = 1; i <= n; i++) {
            if (dp[i] != 0) {
                return new ArrayList<>(); // 還有殘留，代表無解
            }
        }

        // 4. 直接回傳 List！(比剛才更簡潔)
        return coins;
    }

    public static void main(String[] args) {
        InverseCoinChange solver = new InverseCoinChange();

        // --------------------------------------------------
        // 測試案例 1：經典組合
        // 假設未知的硬幣是 [1, 2]，湊 1~5 元的方法數為 [1, 2, 2, 3, 3]
        // 預期結果：[1, 2]
        // --------------------------------------------------
        int[] numWays1 = {1, 2, 2, 3, 3};
        System.out.println("Test Case 1: numWays = " + Arrays.toString(numWays1));
        System.out.println(" 找出的硬幣: " + solver.findCoins(numWays1));
        System.out.println("--------------------------------------------------");

        // --------------------------------------------------
        // 測試案例 2：跳躍面額
        // 假設未知的硬幣只有 [2]，湊 1~4 元的方法數為 [0, 1, 0, 1]
        // (湊 1、3 元無解，湊 2、4 元各 1 種)
        // 預期結果：[2]
        // --------------------------------------------------
        int[] numWays2 = {0, 1, 0, 1};
        System.out.println("Test Case 2: numWays = " + Arrays.toString(numWays2));
        System.out.println(" 找出的硬幣: " + solver.findCoins(numWays2));
        System.out.println("--------------------------------------------------");
    }
}
