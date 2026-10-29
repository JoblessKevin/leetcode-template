package problems.binarySearch;

import java.util.Arrays;

public class KokoEatingBananas {
    public class BinarySearch {
        public int minEatingSpeed(int[] piles, int h) {
            int l = 1;
            int r = Arrays.stream(piles).max().getAsInt();
            int res = r;

            while (l <= r) {
                int k = (l + r) / 2;

                long totalTime = 0;
                for (int p : piles) {
                    totalTime += Math.ceil((double) p / k);
                }
                if (totalTime <= h) {
                    res = k;
                    r = k - 1;
                } else {
                    l = k + 1;
                }
            }

            return res;
        }
    }

    public class MySolution {
        public int minEatingSpeed(int[] piles, int h) {
            int l = 1;
            int r = 0;

            for (int pile : piles) {
                if (pile > r) {
                    r = pile;
                }
            }

            while (l <= r) {
                int mid = l + (r - l) / 2;
                if (count(piles, mid) <= h) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }

            }

            return l;
        }

        private long count(int[] piles, int mid) {
            long count = 0;
            for (int pile : piles) {
                if (pile % mid != 0) {
                    count += pile / mid + 1;
                } else {
                    count += pile / mid;
                }
            }

            return count;
        }
    }

    public static void main(String[] args) {
        KokoEatingBananas.BinarySearch binarySearch = new KokoEatingBananas().new BinarySearch();
        int[] piles = {3, 6, 7, 11};
        int h = 8;
        System.out.println(binarySearch.minEatingSpeed(piles, h)); // Output: 4

        KokoEatingBananas.MySolution mySolution = new KokoEatingBananas().new MySolution();
        System.out.println(mySolution.minEatingSpeed(piles, h)); // Output: 4
    }
}
