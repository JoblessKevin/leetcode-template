package problems.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 這題的最佳解應該是要排序，如同 CombinationSum II， 但是這題的 constraints 很小，所以下面的作法在 leetcode 測試是比較快的
 * 兩者時間複雜度相同，最壞的情況下就是完全沒有剪枝(Pruning)的機會
 */
public class CombinationSum {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();

        // 雖然這題不強制排序，但排序後可以讓剪枝更有效率 (可選)
        // Arrays.sort(candidates);

        // 初始 remain 就是 target，我們用減法來做
        backtrack(result, new ArrayList<>(), candidates, target, 0);
        return result;
    }

    private void backtrack(List<List<Integer>> result, List<Integer> tempList, int[] candidates,
                                    int remain, int start) {

        // --- Base Case 1: 失敗 (Bust!) ---
        // 剩下的目標值變負數，代表加超過了，這條路無效，回頭。
        if (remain < 0) {
            return;
        }

        // --- Base Case 2: 成功 (Bingo!) ---
        // 剩下的目標值剛好歸零，代表找到一組解。
        else if (remain == 0) {
            result.add(new ArrayList<>(tempList));
            return;
        }

        // --- 遞迴邏輯 ---
        for (int i = start; i < candidates.length; i++) {

            // 1. 做選擇
            tempList.add(candidates[i]);

            // 2. 遞迴 (關鍵點！)
            // 注意這裡傳入的是 'i'，而不是 'i + 1'
            // 這代表：下一層還可以繼續選擇「目前這個數字」(candidates[i])
            // 另外，我們直接傳入 remain - candidates[i]
            backtrack(result, tempList, candidates, remain - candidates[i], i);

            // 3. 回溯 (Undo)
            tempList.remove(tempList.size() - 1);
        }
    }

    // --- 測試用 Main 方法 ---
    public static void main(String[] args) {
        CombinationSum solution = new CombinationSum();

        // 測試案例 1: 題目範例
        int[] candidates1 = {2, 3, 6, 7};
        int target1 = 7;
        System.out.println("=== Test Case 1 ===");
        System.out.println("Candidates: " + Arrays.toString(candidates1) + ", Target: " + target1);
        System.out.println("Result: " + solution.combinationSum(candidates1, target1));
        // 預期: [[2, 2, 3], [7]]

        System.out.println();

        // 測試案例 2: 另一組數據
        int[] candidates2 = {5, 2, 3};
        int target2 = 8;
        System.out.println("=== Test Case 2 ===");
        System.out.println("Candidates: " + Arrays.toString(candidates2) + ", Target: " + target2);
        System.out.println("Result: " + solution.combinationSum(candidates2, target2));
        // 預期: [[2, 2, 2, 2], [2, 3, 3], [3, 5]]
    }
}
