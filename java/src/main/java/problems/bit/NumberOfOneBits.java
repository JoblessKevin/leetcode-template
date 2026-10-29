package problems.bit;

/**
 * @formatter:off
 * 題目要求： 給定一個整數，計算其二進位表示中 1 的個數 (Hamming Weight)。
 * * 核心概念：
 * - Kernighan's Algorithm：利用 `n & (n - 1)` 消除二進位中最右邊的 1。
 * - 位元操作：
 * - 檢查第 k 位是否為 1：`(n & (1 << k)) != 0`
 * - 將第 k 位設為 1 (Set bit)：`n = n | (1 << k)`
 * * 解題思路：
 * 1. 當 n 不為 0 時，執行迴圈。
 * 2. 使用 `n &= (n - 1)`，這會精準地把二進位中最右邊的 1 變成 0。
 * 3. 每消除一個 1，計數器 count 增加 1。
 * 4. 迴圈結束後，count 即為所求。
 * * 複雜度分析：
 * - 時間複雜度：O(m)，m 是 1 的個數。比起遍歷 32 位元的 O(32) 更高效。
 * - 空間複雜度：O(1)，僅使用常數空間。
 */
public class NumberOfOneBits {

    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            // 精準打擊：每執行一次，就消掉一個 1
            n &= (n - 1);
            count++;
        }
        return count;
    }

    public int hammingWeight_cpu(int n) {
        return Integer.bitCount(n);
    }

    public static void main(String[] args) {
        NumberOfOneBits solver = new NumberOfOneBits();

        // Test Case 1: 11 (二進位: 1011)
        int n1 = 11;
        int result1 = solver.hammingWeight(n1);
        System.out.println("Test Case 1: 11 (1011) -> 1 bits: " + result1);
        // Expected: 3

        // Test Case 2: 128 (二進位: 10000000)
        int n2 = 128;
        int result2 = solver.hammingWeight(n2);
        System.out.println("Test Case 2: 128 (10000000) -> 1 bits: " + result2);
        // Expected: 1

        // Test Case 3: 0 (二進位: 0)
        int n3 = 0;
        int result3 = solver.hammingWeight(n3);
        System.out.println("Test Case 3: 0 -> 1 bits: " + result3);
        // Expected: 0

        // Test Case 4: -1 (Java 中 -1 是全 1, 11111111...1111)
        int n4 = -1;
        int result4 = solver.hammingWeight(n4);
        System.out.println("Test Case 4: -1 (all 1s) -> 1 bits: " + result4);
        // Expected: 32
    }
}