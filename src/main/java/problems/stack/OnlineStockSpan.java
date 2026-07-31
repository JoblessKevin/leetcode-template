package problems.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class OnlineStockSpan {
    class StockSpanner {
        private Deque<int[]> stack; // pair: [price, span]

        public StockSpanner() {
            stack = new ArrayDeque<>();
        }

        public int next(int price) {
            int span = 1;
            while (!stack.isEmpty() && stack.peek()[0] <= price) {
                span += stack.pop()[1];
            }
            stack.push(new int[] {price, span});
            return span;
        }
    }

    /**
     * Your StockSpanner object will be instantiated and called as such: StockSpanner obj = new
     * StockSpanner(); int param_1 = obj.next(price);
     */

    public static void main(String[] args) {
        // 1. 因為 StockSpanner 是非靜態內部類別，需要先建立外部類別的 instance
        OnlineStockSpan outer = new OnlineStockSpan();
        StockSpanner spanner = outer.new StockSpanner();

        // 2. 準備 LeetCode 上的經典測試資料
        int[] prices = {100, 80, 60, 70, 60, 75, 85};
        int[] expectedSpans = {1, 1, 1, 2, 1, 4, 6};

        System.out.println("開始測試 StockSpanner:");
        System.out.println("-------------------------------------------------");
        System.out.printf("%-10s | %-10s | %-10s | %s%n", "輸入價格", "實際輸出", "預期輸出", "結果");
        System.out.println("-------------------------------------------------");

        boolean allPass = true;

        // 3. 迴圈模擬每一天呼叫 next() 的過程
        for (int i = 0; i < prices.length; i++) {
            int price = prices[i];
            int actualSpan = spanner.next(price);
            int expected = expectedSpans[i];

            boolean isPass = (actualSpan == expected);
            if (!isPass)
                allPass = false;

            // 印出排版好的比較結果
            System.out.printf("%-12d | %-12d | %-12d | %s%n", price, actualSpan, expected,
                                            isPass ? "PASS" : "FAIL");
        }

        System.out.println("-------------------------------------------------");
        if (allPass) {
            System.out.println("恭喜！所有測試案例皆順利通過！");
        } else {
            System.out.println("有測試案例失敗，請檢查邏輯。");
        }
    }
}
