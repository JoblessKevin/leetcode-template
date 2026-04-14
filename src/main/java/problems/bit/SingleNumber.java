package problems.bit;

/**
 * @formatter:off
 * 題目要求： 給定一個整數陣列，其中每個元素都出現兩次，只有一個元素只出現一次，找出那個單一的元素。
 * 
 * 核心概念：
 * - 異或 (XOR) 運算具有交換律和結合律：a ^ b ^ a = b。
 * - 任何數與 0 異或等於自身：a ^ 0 = a。
 * - 任何數與自身異或等於 0：a ^ a = 0。
 * 
 * 解題思路：
 * 1. 初始化一個變數 result 為 0。
 * 2. 遍歷陣列中的每個元素，將其與 result 進行異或運算並更新 result。
 * 3. 由於所有出現兩次的元素最終會相互抵消 (a ^ a = 0)，最後 result 中剩下的就是那個只出現一次的元素。
 * 
 * 複雜度分析：
 * - 時間複雜度：O(n)，只需要遍歷一次陣列。
 * - 空間複雜度：O(1)，只需要常數空間來儲存 result 變數。
 */
public class SingleNumber {
    /**
     * 找出陣列中唯一出現一次的元素
     * 
     * @param nums 輸入的整數陣列，其中每個元素都出現兩次，只有一個元素只出現一次
     * @return 唯一出現一次的元素
     */
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }

    public static void main(String[] args) {
        SingleNumber solver = new SingleNumber();

        // Test Case 1: 範例中的測試
        int[] nums1 = {2, 2, 1};
        int result1 = solver.singleNumber(nums1);
        System.out.println("Test Case 1: [2, 2, 1] -> Single Number: " + result1);
        // Expected: 1

        // Test Case 2: 另一個範例
        int[] nums2 = {4, 1, 2, 1, 2};
        int result2 = solver.singleNumber(nums2);
        System.out.println("Test Case 2: [4, 1, 2, 1, 2] -> Single Number: " + result2);
        // Expected: 4

        // Test Case 3: 只有一個元素
        int[] nums3 = {1};
        int result3 = solver.singleNumber(nums3);
        System.out.println("Test Case 3: [1] -> Single Number: " + result3);
        // Expected: 1

        // Test Case 4: 負數
        int[] nums4 = {-1, -1, -2};
        int result4 = solver.singleNumber(nums4);
        System.out.println("Test Case 4: [-1, -1, -2] -> Single Number: " + result4);
        // Expected: -2

        // Test Case 5: 包含 0
        int[] nums5 = {0, 1, 0, 1, 2};
        int result5 = solver.singleNumber(nums5);
        System.out.println("Test Case 5: [0, 1, 0, 1, 2] -> Single Number: " + result5);
        // Expected: 2
    }
}
