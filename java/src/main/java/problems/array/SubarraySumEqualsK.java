package problems.array;

import java.util.HashMap;
import java.util.Map;

// Time: O(n), Space: O(n)
// 關鍵: 根本不需要「區分」或是「清空」累加值，因為我們只關心「前綴和」的出現次數
public class SubarraySumEqualsK {
    public static int subarraySum(int[] nums, int k) {
        // map 用來記錄 {前綴和: 出現次數}
        Map<Integer, Integer> map = new HashMap<>();

        // 初始化：當前綴和正好等於 k 時，需要減去 0，所以先放入 {0: 1}
        map.put(0, 1);

        int sum = 0; // 當前的累積總和
        int result = 0; // 符合條件的子陣列數量

        for (int num : nums) {
            sum += num; // 累加當前數字

            // 如果 (sum - k) 存在於 map 中，代表中間那段總和剛好是 k
            if (map.containsKey(sum - k)) {
                result += map.get(sum - k);
            }

            // 把當前的 sum 加入 map，供後續查找
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return result;
    }

    public static int subarraySum_bruteForce(int[] nums, int k) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum == k)
                    res++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        // 定義測試案例
        // @formatter:off
        int[][] testNums = 
            {
            {1, 1, 1}, // 基礎測試
            {1, 2, 3}, // 無符合情況
            {1, -1, 1}, // 包含負數
            {0, 0, 0}, // 全部為 0
            {3, 4, 7, 2, -3, 1, 4, 2} // 複雜案例
            };
        // @formatter:on

        int[] kValues = {2, 3, 1, 0, 7};

        for (int i = 0; i < testNums.length; i++) {
            int[] nums = testNums[i];
            int k = kValues[i];

            int resultOptimized = subarraySum(nums, k);
            int resultBrute = subarraySum_bruteForce(nums, k);

            System.out.println("測試案例 " + (i + 1) + ": " + java.util.Arrays.toString(nums) + ", k="
                                            + k);
            System.out.println("最佳解輸出: " + resultOptimized);
            System.out.println("暴力解輸出: " + resultBrute);

            // 驗證兩者是否相等
            if (resultOptimized == resultBrute) {
                System.out.println("狀態: passed 測試通過");
            } else {
                System.out.println("狀態: failed 測試失敗");
            }
            System.out.println("-------------------------------------------");
        }
    }
}
