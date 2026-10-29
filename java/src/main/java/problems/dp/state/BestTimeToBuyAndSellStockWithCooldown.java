package problems.dp.state;

public class BestTimeToBuyAndSellStockWithCooldown {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1)
            return 0;
        int n = prices.length;

        int[] hold = new int[n];
        int[] sold = new int[n];
        int[] rest = new int[n];

        // 初始狀態 (第 0 天)
        hold[0] = -prices[0]; // 買入股票，利潤為負
        sold[0] = 0; // 第 0 天不可能賣出
        rest[0] = 0; // 觀望中

        for (int i = 1; i < n; i++) {
            // 今天結束仍持股：可以是昨天就持股，或是今天剛從 rest 狀態買入
            hold[i] = Math.max(hold[i - 1], rest[i - 1] - prices[i]);

            // 今天結束剛賣出：唯一來源是昨天持股，今天賣掉
            sold[i] = hold[i - 1] + prices[i];

            // 今天結束在休息：可以是昨天剛賣完今天強迫休息，或是昨天就在休息今天繼續觀望
            rest[i] = Math.max(rest[i - 1], sold[i - 1]);
        }

        // 最終最大利潤一定是在「沒股票」的狀態下 (sold 或 rest)
        return Math.max(sold[n - 1], rest[n - 1]);
    }

    public int maxProfit_optimized(int[] prices) {
        if (prices == null || prices.length <= 1)
            return 0;

        // 初始狀態
        int hold = -prices[0];
        int sold = 0;
        int rest = 0;

        for (int i = 1; i < prices.length; i++) {
            int prevHold = hold;
            int prevSold = sold;
            int prevRest = rest;

            // 根據昨天的狀態更新今天的
            hold = Math.max(prevHold, prevRest - prices[i]);
            sold = prevHold + prices[i];
            rest = Math.max(prevRest, prevSold);
        }

        // 最後回傳沒持股狀態下的最大值
        return Math.max(sold, rest);
    }

    public static void main(String[] args) {
        BestTimeToBuyAndSellStockWithCooldown solver = new BestTimeToBuyAndSellStockWithCooldown();

        // 測試案例：經典案例 [1, 2, 3, 0, 2]
        int[] prices = {1, 2, 3, 0, 2};

        System.out.println("=== 股票交易 (含冷凍期) 狀態演化表 ===");
        System.out.println("Prices: " + java.util.Arrays.toString(prices));
        System.out.println();

        // 為了讓你看清過程，我們手動跑一次並印出來
        visualizeStates(prices);

        System.out.println("------------------------------------");
        System.out.println("Standard DP Result: " + solver.maxProfit(prices));
        System.out.println("Optimized DP Result: " + solver.maxProfit_optimized(prices));
    }

    /**
     * 輔助方法：視覺化每一天的狀態機變化
     */
    private static void visualizeStates(int[] prices) {
        int n = prices.length;
        if (n == 0)
            return;

        // 為了對齊列印，我們複寫一下邏輯
        int hold = -prices[0];
        int sold = 0;
        int rest = 0;

        System.out.printf("%-8s | %-6s | %-8s | %-8s | %-8s%n", "Day", "Price", "Hold", "Sold",
                                        "Rest");
        System.out.println("-".repeat(50));

        // Day 0
        System.out.printf("Day %-4d | %-6d | %-8d | %-8d | %-8d%n", 0, prices[0], hold, sold, rest);

        for (int i = 1; i < n; i++) {
            int prevHold = hold;
            int prevSold = sold;
            int prevRest = rest;

            // 狀態機轉換
            hold = Math.max(prevHold, prevRest - prices[i]);
            sold = prevHold + prices[i];
            rest = Math.max(prevRest, prevSold);

            System.out.printf("Day %-4d | %-6d | %-8d | %-8d | %-8d%n", i, prices[i], hold, sold,
                                            rest);
        }
        System.out.println("-".repeat(50));
        System.out.println("Final Profit: " + Math.max(sold, rest));
    }
}
