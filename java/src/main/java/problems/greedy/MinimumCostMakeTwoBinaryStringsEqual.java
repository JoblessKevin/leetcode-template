package problems.greedy;

public class MinimumCostMakeTwoBinaryStringsEqual {
    public long minimumCost(String s, String t, int flipCost, int swapCost, int crossCost) {
        long cnt01 = 0, cnt10 = 0;
        int n = s.length();

        // 1. 簡化遍歷邏輯
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == t.charAt(i)) continue;
            if (s.charAt(i) == '0') cnt01++;
            else cnt10++;
        }
        
        // 2. 預先定義兩種配對的"最佳價格"
        // 消除 (01, 10) 的代價
        long costMix = Math.min(swapCost, 2L * flipCost);
        // 消除 (01, 01) 或 (10, 10) 的代價
        long costSame = Math.min((long)crossCost + swapCost, 2L * flipCost);

        // 3. 直接套用公式計算
        long mixPairs = Math.min(cnt01, cnt10);
        long remPairs = Math.abs(cnt01 - cnt10);

        return (mixPairs * costMix)             // 處理成對的異類
             + (remPairs / 2 * costSame)        // 處理成對的同類
             + (remPairs % 2 * flipCost);       // 處理落單的 1 個
    }

    // ==========================================
    // Test case
    // ==========================================
    public static void main(String[] args) {
        MinimumCostMakeTwoBinaryStringsEqual solver = new MinimumCostMakeTwoBinaryStringsEqual();

        System.out.println("--------------------------------------------------");

        // 【測試案例 1】LeetCode 題目範例
        // 預期結果: 16
        // 說明: 1組 MixPair (2元) + 1組 SamePair (4元) + 1個 Remainder (10元)
        // ans: 2 + 2 + 2 + 10 = 16
        test(solver, 
             "01000", "10111", 
             10, 2, 2, 
             "LeetCode Example 1");

        // 【測試案例 2】我們剛才討論的「綜合情境」
        // 預期結果: 125
        // 說明: 
        // 4個(01), 1個(10) -> 
        // 1組 MixPair (10元) + 1組 SamePair (5+10=15元) + 1個 Remainder (100元)
        test(solver, 
             "00001", "11110", 
             100, 10, 5, 
             "Three Situations Demo");

        // 【測試案例 3】全部都是同一種錯誤 (只有 01)
        // 預期結果: 115
        // 說明: 3個(01) -> 0組 Mix -> 1組 SamePair (15元) + 1個 Remainder (100元)
        test(solver, 
             "000", "111", 
             100, 10, 5, 
             "Pure Same Errors");

        // 【測試案例 4】翻轉超便宜 (Flip cost is cheap)
        // 預期結果: 2
        // 說明: 雖然可以 Swap，但 2 * flip (1+1=2) 比 swap (100) 還便宜，
        // 程式應該聰明地選擇直接翻轉。
        test(solver, 
             "01", "10", 
             1, 100, 100, 
             "Cheap Flip Strategy");
             
        System.out.println("--------------------------------------------------");
        
        // ans: 6
        test(solver, 
             "001", "110", 
             2, 100, 100, 
             "LeetCode Example 2");

        // ans: 0
        test(solver, 
             "1010", "1010", 
             5, 5, 5, 
             "LeetCode Example 3");
    }

    private static void test(MinimumCostMakeTwoBinaryStringsEqual solver,
                             String s, String t,
                             int flip, int swap, int cross,
                             String caseName) {
        System.out.println("★ Case: " + caseName);
        System.out.println("   s: " + s);
        System.out.println("   t: " + t);
        System.out.printf("   Costs -> Flip: %d, Swap: %d, Cross: %d%n", flip, swap, cross);
        
        long result = solver.minimumCost(s, t, flip, swap, cross);
        System.out.println("   >>> Minimum Cost: " + result);
        System.out.println();
    }
}
