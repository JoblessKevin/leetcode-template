package problems.greedy;

public class MergeTripletsToFormTarget {
    public boolean mergeTriplets(int[][] triplets, int[] target) {
        // 紀錄我們是否已經在三個位置分別找到了 target 要求的數字
        boolean[] found = new boolean[3];

        for (int[] t : triplets) {
            // 1. 檢查這個三元組是否「安全」（沒有任何一項超過 target）
            if (t[0] <= target[0] && t[1] <= target[1] && t[2] <= target[2]) {

                // 2. 如果安全，看看它能不能幫我們湊出 target 的某個數字
                for (int i = 0; i < 3; i++) {
                    if (t[i] == target[i]) {
                        found[i] = true;
                    }
                }
            }

            // 優化：如果三個位置都找到了，直接提早收工
            if (found[0] && found[1] && found[2])
                return true;
        }

        return found[0] && found[1] && found[2];
    }

    public boolean mergeTriplets_optimized(int[][] triplets, int[] target) {
        // 1. 將 target 拆解成局部變數，讓 CPU 優先放入「暫存器 (Registers)」
        // 避開頻繁訪問 target[i] 的記憶體開銷
        int t1 = target[0], t2 = target[1], t3 = target[2];

        // 2. 用三個獨立變數記錄進度，而不是用陣列 (避免邊界檢查開銷)
        boolean found1 = false, found2 = false, found3 = false;

        for (int[] t : triplets) {
            // 3. 過濾「有毒」樣本 (只要有一維超過 target 就不可能被合併)
            if (t[0] <= t1 && t[1] <= t2 && t[2] <= t3) {

                // 4. 只在尚未達成目標時才檢查，減少不必要的賦值
                if (t[0] == t1)
                    found1 = true;
                if (t[1] == t2)
                    found2 = true;
                if (t[2] == t3)
                    found3 = true;

                // 5. 核心優化：及早停機 (Early Exit)
                // 一旦三個維度都集齊，剩下的幾萬筆數據直接不用看
                if (found1 && found2 && found3)
                    return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        MergeTripletsToFormTarget solver = new MergeTripletsToFormTarget();

        int[][] triplets1 = {{2, 5, 3}, {1, 2, 4}, {2, 1, 1}};
        int[] target1 = {2, 3, 4};
        System.out.println("Test Case 1: " + solver.mergeTriplets(triplets1, target1)); // 應為 true

        int[][] triplets2 = {{3, 4, 5}, {4, 5, 6}}; // 3,4,5 和 4,5,6
        int[] target2 = {3, 6, 7}; // 目標是 3,6,7
        System.out.println("Test Case 2: " + solver.mergeTriplets(triplets2, target2)); // 應為 false
    }
}
