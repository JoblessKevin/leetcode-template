package problems.dp;

/**
 * @formatter:off
 * 硬幣在外，金額在內： 算出的是「組合數」。
 * 例如：湊 3 元只有 {1, 2} 這一種拿法。
 * 
 * 金額在外，硬幣在內： 算出的是「排列數」。
 * 例如：湊 3 元會有 {1, 2} 和 {2, 1} 兩種拿法。
 * @formatter:on
 */
public class CoinChangeII {
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }

    public static void main(String[] args) {
        CoinChangeII coinChangeII = new CoinChangeII();
        int[] coins = {1, 2, 5};
        int amount = 5;
        System.out.println(coinChangeII.change(amount, coins));
    }
}
