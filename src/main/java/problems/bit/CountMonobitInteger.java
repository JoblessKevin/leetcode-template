package problems.bit;

class Solution {
    public int countMonobit(int n) {
        int count = 1; // 先算入 0
        long current = 1; // 從 1 開始 (用 long 避免溢位)

        while (current <= n) {
            count++;
            // 產生下一個 Monobit: 1 -> 11 -> 111
            // 寫法: current * 2 + 1
            current = (current << 1) | 1;
        }

        return count;
    }

    public static void main(String[] args) {
        System.out.println("=== 開始測試 Count Monobit Integers ===");

        // 1. 邊界測試：最小輸入
        // 範圍 [0, 0]，只有 0 符合
        test(0, 1);

        // 2. 題目範例 1
        // 範圍 [0, 1]，符合的有: 0 ("0"), 1 ("1")
        test(1, 2);

        // 3. 恰好是 Monobit 的數字 (邊界檢查)
        // 範圍 [0, 3]，符合的有: 0, 1, 3 ("11")
        test(3, 3);

        // 4. 題目範例 2 (大於 Monobit 一點點)
        // 範圍 [0, 4]，符合的有: 0, 1, 3 (4 是 "100" 不符合)
        test(4, 3);

        // 5. 較大的 Monobit 邊界測試
        // 範圍 [0, 7]，符合的有: 0, 1, 3, 7 ("111") -> 共 4 個
        test(7, 4);

        // 範圍 [0, 8]，8 是 "1000"，不增加數量，還是 4 個
        test(8, 4);

        // 6. 更大的數字測試
        // 範圍包含: 0, 1, 3, 7, 15, 31 (共 6 個)
        test(31, 6);
        test(32, 6); // 32 不符合

        System.out.println("=== 測試結束 ===");
    }

    private static void test(int n, int expected) {
        Solution solution = new Solution();
        int result = solution.countMonobit(n);
        System.out.println("範圍 [0, " + n + "]，符合的有: " + result + " 個 (預期: " + expected + " 個)");
    }
}
