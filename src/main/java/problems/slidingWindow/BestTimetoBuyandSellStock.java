package problems.slidingWindow;

import java.util.Arrays;

public class BestTimetoBuyandSellStock {
    /** Sliding Window */
    public int maxProfit_slidingWindow(int[] prices) {
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

    /** Kadane’s Algorithm */
    public int maxProfit_kadane(int[] prices) {
        int curMax = 0, maxProfit = 0;

        for (int i = 1; i < prices.length; i++) {
            int gain = prices[i] - prices[i - 1];
            curMax = Math.max(gain, curMax + gain);
            maxProfit = Math.max(maxProfit, curMax);
        }

        return maxProfit;
    }

    /** Kadane’s Algorithm Example */
    int maxSubArray(int[] nums) {
        int maxSum = nums[0], curSum = 0;
        for (int num : nums) {
            curSum = Math.max(num, curSum + num);
            maxSum = Math.max(maxSum, curSum);
        }
        return maxSum;
    }

    public static void main(String[] args) {

        BestTimetoBuyandSellStock solution = new BestTimetoBuyandSellStock();

        int[][] testCases = {
                {7,1,5,3,6,4},      // 5
                {7,6,4,3,1},        // 0
                {1,2,3,4,5},        // 4
                {2,4,1},            // 2
                {3,3,3,3,3},        // 0
                {2,1,2,0,1},        // 1
                {1},                // 0
                {1,2},              // 1
                {2,1}               // 0
        };

        for (int[] prices : testCases) {
            int profit1 = solution.maxProfit_slidingWindow(prices);
            int profit2 = solution.maxProfit_kadane(prices);

            System.out.println("Prices: " + Arrays.toString(prices));
            System.out.println("Sliding Window Profit: " + profit1);
            System.out.println("Kadane Profit:        " + profit2);
            System.out.println("-------------------------");
        }
    }
}
