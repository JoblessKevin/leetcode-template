package problems.slidingWindow;

import java.util.Arrays;

public class BestTimetoBuyandSellStock {
    /** Sliding Window using two pointers */
    public int maxProfit(int[] prices) {
        int l = 0, r = 1;
        int maxProfit = 0;

        while (r < prices.length) {
            if (prices[l] < prices[r]) {
                int profit = prices[r] - prices[l];
                maxProfit = Math.max(maxProfit, profit);
            } else {
                l = r;
            }
            r++;
        }

        return maxProfit;
    }

    /** Greedy + Dynamic Programming */
    public int maxProfit_dp(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;
            } else {
                maxProfit = Math.max(maxProfit, price - minPrice);
            }
        }

        return maxProfit;
    }

    public static void main(String[] args) {

        BestTimetoBuyandSellStock solution = new BestTimetoBuyandSellStock();

        int[][] testCases = {{7, 1, 5, 3, 6, 4}, // 5
                                        {7, 6, 4, 3, 1}, // 0
                                        {1, 2, 3, 4, 5}, // 4
                                        {2, 4, 1}, // 2
                                        {3, 3, 3, 3, 3}, // 0
                                        {2, 1, 2, 0, 1}, // 1
                                        {1}, // 0
                                        {1, 2}, // 1
                                        {2, 1} // 0
        };

        for (int[] prices : testCases) {
            int profit1 = solution.maxProfit(prices);
            int profit2 = solution.maxProfit_dp(prices);


            System.out.println("Prices: " + Arrays.toString(prices));
            System.out.println("Sliding Window Profit:      " + profit1);
            System.out.println("Dynamic Programming Profit: " + profit2);

            System.out.println("-------------------------");
        }
    }
}
