package problems.greedy;

public class BestTimeToBuyAndSellStockII {
    public int maxProfit(int[] prices) {
        int ans = 0;

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                ans += prices[i] - prices[i - 1];
            }
        }

        return ans;
    }

    public int maxProfit_dp(int[] prices) {
        int n = prices.length;
        if (n == 0)
            return 0;

        int hold = -prices[0];
        int notHold = 0;

        for (int i = 1; i < n; i++) {
            int prevHold = hold;

            hold = Math.max(hold, notHold - prices[i]);
            notHold = Math.max(notHold, prevHold + prices[i]);
        }

        return notHold;
    }
}
