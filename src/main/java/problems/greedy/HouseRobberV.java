package problems.greedy;

/**
 * @formatter:off
 * 思考方式 (區塊拆解 + 1D DP)：
 * 1. 題目規則暗示：只要顏色不同，相鄰房子就不受「互斥」規則限制。
 * 2. 因此，整條街道可以根據「連續的相同顏色」被切分成多個互相獨立的子區塊。
 * 3. 我們用一個 while 迴圈找出這些連續區塊的 start 和 end。
 * 4. 對每一個區塊，套用經典的 House Robber I (滾動變數優化版) 算出該區塊的最大收益。
 * 5. 把所有區塊的收益加總起來 (記得用 long 避免溢位)。
 * 時間複雜度：O(N) 
 * - 外層 while 和內層 while 總共只會把整個陣列走過一遍 (雙指標概念)。
 * - robHelper 也只是把陣列走過一遍。
 * - 總結依然是完美的線性時間 O(N)。
 * 空間複雜度：O(1)
 * - 只使用了 start, end, prev1, prev2 等常數個變數。
 * @formatter:on
 */
public class HouseRobberV {
    public long rob(int[] nums, int[] colors) {
        long totalMaxProfit = 0; // 用 long 防禦溢位
        int n = nums.length;
        int i = 0;

        // 雙指標概念：用來切分「顏色連續相同」的區塊
        while (i < n) {
            int start = i;

            // 只要下一間房子顏色一樣，我們就把 end 往後推
            while (i + 1 < n && colors[i] == colors[i + 1]) {
                i++;
            }
            int end = i;

            // 把這個獨立區塊 [start, end] 丟給小弟去算最大收益
            totalMaxProfit += robHelper(nums, start, end);

            // 推進到下一個全新顏色的區塊
            i++;
        }

        return totalMaxProfit;
    }

    // 我們的祖傳神兵：House Robber I (指定範圍版)
    private long robHelper(int[] nums, int start, int end) {
        long prev2 = 0;
        long prev1 = 0;

        for (int i = start; i <= end; i++) {
            long current = Math.max(prev1, prev2 + nums[i]);
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }

    public static void main(String[] args) {
        HouseRobberV solution = new HouseRobberV();

        // --------------------------------------------------
        // 測試案例 1：我們剛才推演的彩色漏洞
        // nums: [3, 1, 2, 4]
        // colors: [2, 3, 2, 2]
        // 區塊劃分： [3] (顏色2), [1] (顏色3), [2, 4] (顏色2)
        // 預期結果： 3 + 1 + 4 = 8
        // --------------------------------------------------
        int[] nums1 = {3, 1, 2, 4};
        int[] colors1 = {2, 3, 2, 2};
        System.out.println("Test Case 1:");
        System.out.println("  答案: " + solution.rob(nums1, colors1));
        System.out.println("  狀態: " + (solution.rob(nums1, colors1) == 8 ? "正確" : "錯誤"));
        System.out.println("--------------------------------------------------");

        // --------------------------------------------------
        // 測試案例 2：全部同顏色 (退化成最經典的 House Robber I)
        // nums: [2, 7, 9, 3, 1]
        // colors: [1, 1, 1, 1, 1]
        // 預期結果： 2 + 9 + 1 = 12
        // --------------------------------------------------
        int[] nums2 = {2, 7, 9, 3, 1};
        int[] colors2 = {1, 1, 1, 1, 1};
        System.out.println("Test Case 2 (全部同色):");
        System.out.println("  答案: " + solution.rob(nums2, colors2));
        System.out.println("  狀態: " + (solution.rob(nums2, colors2) == 12 ? "正確" : "錯誤"));
        System.out.println("--------------------------------------------------");

        // --------------------------------------------------
        // 測試案例 3：全部不同色 (可以無腦全偷！)
        // nums: [10, 20, 30]
        // colors: [1, 2, 3]
        // 預期結果： 10 + 20 + 30 = 60
        // --------------------------------------------------
        int[] nums3 = {10, 20, 30};
        int[] colors3 = {1, 2, 3};
        System.out.println("Test Case 3 (全部不同色):");
        System.out.println("  答案: " + solution.rob(nums3, colors3));
        System.out.println("  狀態: " + (solution.rob(nums3, colors3) == 60 ? "正確" : "錯誤"));
        System.out.println("--------------------------------------------------");
    }
}
