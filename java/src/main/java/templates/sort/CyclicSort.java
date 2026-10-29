package templates.sort;

public class CyclicSort {
    public void cyclicSort(int[] nums) {
        int i = 0;
        while (i < nums.length) {
            // 計算該數字應該要去的「家」(索引位置)
            // 假設數字從 1 開始，所以數字 x 應該在索引 x - 1
            int targetIndex = nums[i] - 1;

            // 檢查數字是否在正確的位置上
            // 1. nums[i] != nums[targetIndex]: 如果兩者相等，代表已經在位或重複，跳過
            // 2. nums[i] > 0 && nums[i] <= nums.length: 確保數字在我們關心的範圍內
            if (nums[i] > 0 && nums[i] <= nums.length && nums[i] != nums[targetIndex]) {
                // 交換 (Swap)
                int temp = nums[i];
                nums[i] = nums[targetIndex];
                nums[targetIndex] = temp;
            } else {
                // 該數字已經歸位，或者不屬於該處理範圍，換下一個位置
                i++;
            }
        }
    }
}
