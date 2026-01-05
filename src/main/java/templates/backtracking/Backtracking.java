package templates.backtracking;

import java.util.ArrayList;
import java.util.List;

public class Backtracking {
    // 存放所有合法的答案
    List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> solve(int[] nums) {
        // 存放當前正在嘗試的路徑 (例如：目前走到的迷宮格子，或是目前選的數字)
        List<Integer> currentPath = new ArrayList<>();

        // 開始遞迴
        backtrack(nums, currentPath);
        return result;
    }

    /**
     * @param choices     所有可選的選項 (Input)
     * @param currentPath 目前已經走過的路徑 (State)
     */
    private void backtrack(int[] choices, List<Integer> currentPath) {

        // 1. 終止條件 (Base Case)
        // 如果路徑已經滿足題目要求 (例如：選滿了 k 個數字，或走到了出口)
        if (isGoalReached(currentPath)) {
            // 【重要】必須 new 一個新的 ArrayList 拷貝一份
            // 因為 currentPath 在後續的操作中會被修改
            result.add(new ArrayList<>(currentPath));
            return;
        }

        // 2. 遍歷所有選擇 (Iterate)
        for (int i = 0; i < choices.length; i++) {
            int choice = choices[i];

            // 3. 剪枝 (Pruning) - 如果這個選擇不合法，直接跳過
            if (!isValid(choice, currentPath)) {
                continue;
            }

            // --- 核心步驟開始 ---

            // 4. 做選擇 (Choose)
            currentPath.add(choice);

            // 5. 遞迴進入下一層 (Explore)
            backtrack(choices, currentPath);

            // 6. 撤銷選擇 (Un-choose / Backtrack)
            // 這一行最重要！回到上一層之前，要把剛剛加進去的東西拿出來
            // 這樣才能保持 currentPath 乾淨，讓下一次迴圈嘗試別的選項
            currentPath.remove(currentPath.size() - 1);

            // --- 核心步驟結束 ---
        }
    }

    // 輔助函式 (示意用)
    private boolean isGoalReached(List<Integer> path) {
        // 判斷是否結束，例如長度是否足夠
        return path.size() == 3;
    }

    private boolean isValid(int choice, List<Integer> path) {
        // 判斷當前選擇是否合法，例如是否重複選過
        return !path.contains(choice);
    }
}
