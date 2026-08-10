package problems.binarySearch;

public class Sqrt {
    class BinarySearch {
        public int mySqrt(int x) {
            int l = 0, r = x;
            int res = 0;

            while (l <= r) {
                int mid = l + (r - l) / 2;
                if ((long) mid * mid <= x) {
                    res = mid;
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            return res;
        }
    }

    class BitManipulation {
        public int mySqrt(int x) {
            long r = x;
            while (r * r > x) {
                r = (r + x / r) >> 1;
            }
            return (int) r;
        }
    }

    class Solution {
        public int mySqrt(int x) {
            if (x == 0)
                return 0;

            long ans = 0;
            // 1 << 16 是 65536，因為 65536^2 超過了 32 位元 int 的最大值
            // 所以我們從 1 << 15 (也就是 32768) 開始嘗試是安全的
            int bit = 1 << 15;

            while (bit > 0) {
                long test = ans | bit; // 嘗試把當前的 bit 打開

                // 如果 test 的平方小於或等於 x，保留這個 bit
                if (test * test <= x) {
                    ans = test;
                }

                // 檢查下一個較小的 bit
                bit >>= 1;
            }

            return (int) ans;
        }
    }

    public static void main(String[] args) {
        Sqrt sqrt = new Sqrt();
        BinarySearch binarySearch = sqrt.new BinarySearch();
        BitManipulation bitManipulation = sqrt.new BitManipulation();

        int x = 8;
        System.out.println("Binary Search: " + binarySearch.mySqrt(x));
        System.out.println("Bit Manipulation: " + bitManipulation.mySqrt(x));
    }
}
