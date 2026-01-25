package problems.array;

import java.util.ArrayList;
import java.util.List;

public class RotateNonNegativeElements {
    public int[] rotateNonNegative(int[] nums, int k) {
        // 1. 收集所有非負數
        List<Integer> temp = new ArrayList<>();
        for (int x : nums) {
            if (x >= 0)
                temp.add(x);
        }

        // 如果沒有非負數，直接回傳原陣列
        if (temp.isEmpty())
            return nums;

        int m = temp.size();
        k = k % m; // 避免 k 比陣列長度還大

        // 2. 直接用索引取值，不需要額外建立 rotated List
        // 左轉 k 次，代表原本在 index k 的元素會跑到第一位
        // 所以我們從 index k 開始拿，拿完後面就繞回頭 (Modulo %)
        int currentIndex = k;

        for (int i = 0; i < nums.length; i++) {
            // 只有遇到非負數的位置才要填寫
            if (nums[i] >= 0) {
                nums[i] = temp.get(currentIndex);

                // 往後移動一格，如果超過長度就繞回 0
                currentIndex = (currentIndex + 1) % m;
            }
        }

        return nums;
    }
}
