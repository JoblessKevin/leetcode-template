package problems.array;

import java.util.HashSet;
import java.util.Set;

// Reqirement: O(n) time and O(1) space (ignoring the input array)
public class FirstMissingPositive {
    // Time: O(n), Space: O(n)
    public int firstMissingPositive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int n = nums.length;

        for (int num : nums) {
            if (num > 0 && num <= n) {
                set.add(num);
            }
        }

        for (int i = 1; i <= n + 1; i++) {
            if (!set.contains(i)) {
                return i;
            }
        }

        return 1;
    }

    // Cyclic Sort: Time: O(n), Space: O(1)
    public int firstMissingPositive_cyclicSort(int[] nums) {
        int n = nums.length;

        // 1. 第一階段：物歸原位
        for (int i = 0; i < n; i++) {
            // 當前數字 nums[i] 必須滿足三個條件：
            // 1. 是正數 (nums[i] > 0)
            // 2. 在陣列範圍內 (nums[i] <= n)
            // 3. 還沒在正確的位置上 (nums[nums[i] - 1] != nums[i])
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                // 交換位置：將 nums[i] 換到它應該在的地方
                int targetIndex = nums[i] - 1;
                int temp = nums[i];
                nums[i] = nums[targetIndex];
                nums[targetIndex] = temp;
            }
        }

        // 2. 第二階段：掃描陣列，找第一個「名不符實」的位置
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1; // 找到了！這就是缺失的第一個正整數
            }
        }

        // 3. 如果所有數字都歸位了，代表陣列是 [1, 2, ..., n]，缺失的就是 n + 1
        return n + 1;
    }
}
