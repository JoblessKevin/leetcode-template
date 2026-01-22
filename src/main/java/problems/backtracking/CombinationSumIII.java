package problems.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilize the pruning strategy to reduce the number of recursive calls
 */
public class CombinationSumIII {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), k, n, 1);

        return result;
    }

    private void backtrack(List<List<Integer>> result, List<Integer> tempList, int k, int remain,
                                    int start) {
        if (tempList.size() > k || remain < 0)
            return;
        if (tempList.size() == k && remain == 0) {
            result.add(new ArrayList<>(tempList));
            return;
        }

        for (int i = start; i <= 9 - (k - tempList.size()) + 1; i++) {
            if (remain - i < 0)
                break;

            tempList.add(i);
            backtrack(result, tempList, k, remain - i, i + 1);
            tempList.remove(tempList.size() - 1);
        }
    }

    // ==========================================
    // Test Case
    // ==========================================
    public static void main(String[] args) {
        CombinationSumIII solver = new CombinationSumIII();

        // Test Case 1: 題目範例 1
        // 解釋: 1+2+4=7
        int k1 = 3, n1 = 7;
        System.out.println("\nTest Case 1 (k=" + k1 + ", n=" + n1 + ")");
        System.out.println("預期結果: [[1, 2, 4]]");
        System.out.println("實際輸出: " + solver.combinationSum3(k1, n1));

        // Test Case 2: 題目範例 2
        // 解釋: 1+2+6=9, 1+3+5=9, 2+3+4=9
        int k2 = 3, n2 = 9;
        System.out.println("\nTest Case 2 (k=" + k2 + ", n=" + n2 + ")");
        System.out.println("預期結果: [[1, 2, 6], [1, 3, 5], [2, 3, 4]]");
        System.out.println("實際輸出: " + solver.combinationSum3(k2, n2));

        // Test Case 3: 題目範例 3 (無解)
        // 解釋: 4個數字最小總和是 1+2+3+4=10，所以 n=1 不可能達成
        int k3 = 4, n3 = 1;
        System.out.println("\nTest Case 3 (k=" + k3 + ", n=" + n3 + ")");
        System.out.println("預期結果: []");
        System.out.println("實際輸出: " + solver.combinationSum3(k3, n3));

        // Test Case 4: 極限值 (全選)
        // 解釋: 1+2+3+4+5+6+7+8+9 = 45
        int k4 = 9, n4 = 45;
        System.out.println("\nTest Case 4 (k=" + k4 + ", n=" + n4 + ")");
        System.out.println("預期結果: [[1, 2, 3, 4, 5, 6, 7, 8, 9]]");
        System.out.println("實際輸出: " + solver.combinationSum3(k4, n4));

        // Test Case 5: 驗證 Loop 優化邏輯
        // 這是為了確認 `i <= 9 - (k - size) + 1` 沒有把正確答案切掉
        // k=3, n=24 -> [7,8,9]
        // 如果優化寫錯，可能會漏掉 7 開頭的組合
        int k5 = 3, n5 = 24;
        System.out.println("\nTest Case 5 (k=" + k5 + ", n=" + n5 + ")");
        System.out.println("預期結果: [[7, 8, 9]]");
        System.out.println("實際輸出: " + solver.combinationSum3(k5, n5));
    }
}
