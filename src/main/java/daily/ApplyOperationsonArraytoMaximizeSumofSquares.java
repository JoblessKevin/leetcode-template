package daily;

import java.util.List;

public class ApplyOperationsonArraytoMaximizeSumofSquares {
    public int maxSum(List<Integer> nums, int k) {
        // 1. 位元統計：計算所有數字在每個二進位位元上出現 1 的總次數
        int[] count = new int[32];
        for (int num : nums) {
            for (int i = 0; i < 32; i++) {
                // 如果 num 的第 i 位是 1，則對應的 count[i] 增加
                if ((num & (1 << i)) != 0) {
                    count[i]++;
                }
            }
        }

        long ans = 0;
        int mod = 1000000007; // 10^9 + 7

        // 2. 貪心組裝：組裝 k 個盡可能大的數字
        for (int j = 0; j < k; j++) {
            long val = 0;
            // 每次都嘗試從第 0 位到第 31 位把可用的 1 拿來組數字
            for (int i = 0; i < 32; i++) {
                if (count[i] > 0) {
                    // 將第 i 位設為 1
                    val |= (1L << i);
                    // 消耗掉一個 1
                    count[i]--;
                }
            }
            // 3. 計算平方並累加，記得取餘數
            // 注意 val * val 可能會超過 long 範圍（最高可能近 10^18），但 java long 可以容納
            ans = (ans + (val * val) % mod) % mod;
        }

        return (int) ans;
    }

    public static void main(String[] args) {
        ApplyOperationsonArraytoMaximizeSumofSquares solution =
                                        new ApplyOperationsonArraytoMaximizeSumofSquares();
        List<Integer> nums = List.of(2, 6, 5, 8);
        int k = 2;
        int result = solution.maxSum(nums, k); // 261
        System.out.println("1. Max Sum of Squares: " + result);

        List<Integer> nums2 = List.of(2, 6, 5, 8);
        int k2 = 2;
        int result2 = solution.maxSum(nums2, k2); // 90
        System.out.println("2. Max Sum of Squares: " + result2);
    }
}
