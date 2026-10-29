package problems.bit;

public class ReverseBits {
    public int reverseBits(int n) {
        int res = 0;
        for (int i = 0; i < 32; i++) {
            // 1. 將結果向左移一位，騰出個位數的空位
            res <<= 1;

            // 2. 取出 n 的最後一位，並加到 res 的個位數
            // (n & 1) 取出最後一位，使用 | 運算放入 res
            res |= (n & 1);

            // 3. n 向右移一位，準備處理下一個位元
            // 注意：一定要用 >>> 無符號右移，確保負數左邊補 0
            n >>>= 1;
        }
        return res;
    }

    /**
     * Divide and Conquer, SWAR (SIMD Within A Register)
     */
    public int reverseBits_optimized(int n) {
        // 交換相鄰的 16 位
        n = (n >>> 16) | (n << 16);
        // 交換相鄰的 8 位
        n = ((n & 0xff00ff00) >>> 8) | ((n & 0x00ff00ff) << 8);
        // 交換相鄰的 4 位
        n = ((n & 0xf0f0f0f0) >>> 4) | ((n & 0x0f0f0f0f) << 4);
        // 交換相鄰的 2 位
        n = ((n & 0xcccccccc) >>> 2) | ((n & 0x33333333) << 2);
        // 最後交換相鄰的 1 位
        n = ((n & 0xaaaaaaaa) >>> 1) | ((n & 0x55555555) << 1);
        return n;
    }
}
