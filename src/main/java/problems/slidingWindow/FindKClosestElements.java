package problems.slidingWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FindKClosestElements {

    /**
     * Optimal Solution: Binary Search + Two Pointers
     * Binary search solution
     * @formatter:off
     * Time Complexity: O(log(n-k) + k)
     * Space Complexity: O(k)
     * @formatter:on
     */
    public List<Integer> findClosestElements_optimal(int[] arr, int k, int x) {
        // 尋找目標：大小為 k 的視窗的「起始索引」
        int l = 0;
        int r = arr.length - k;

        while (l < r) {
            int mid = l + (r - l) / 2;

            // 讓當前視窗的第一個元素，跟視窗外的下一個元素 PK
            if (x - arr[mid] > arr[mid + k] - x) {
                // 右邊的備取元素離 x 更近，視窗必須往右滑
                l = mid + 1;
            } else {
                // 左邊的元素比較近（或一樣近，但數值較小），視窗往左靠
                r = mid;
            }
        }

        // 迴圈結束時，left 就是最佳視窗的起始點
        List<Integer> res = new ArrayList<>();
        for (int i = l; i < l + k; i++) {
            res.add(arr[i]);
        }

        return res;
    }

    /**
     * Sliding window solution
     * @formatter:off
     * Time Complexity: O(n)
     * Space Complexity: O(k)
     * @formatter:on
     */
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int l = 0;
        int r = arr.length - 1;

        // 當視窗內的數字超過 k 個時，我們就要繼續「淘汰」
        while (r - l + 1 > k) {

            // 比較左右兩端，誰距離 x 比較遠？
            if (Math.abs(arr[l] - x) > Math.abs(arr[r] - x)) {
                l++; // 左邊比較遠，淘汰左邊 (左指針向右縮)
            } else {
                r--; // 右邊比較遠 (或距離一樣)，淘汰右邊 (右指針向左縮)
            }
        }

        // 當迴圈結束時，left 到 right 之間剛好就是我們要的 k 個數字
        // 而且因為原本陣列就排好序了，這裡拿出來直接就是由小到大！
        List<Integer> res = new ArrayList<>();
        for (int i = l; i <= r; i++) {
            res.add(arr[i]);
        }

        return res;
    }

    /**
     * @formatter:off
     * 這個解法沒有用到陣列本身已經排好序的條件
     * Time Complexity: O(nlogn) + O(klogk)
     * @formatter:on
     */
    public List<Integer> findClosestElements_bruteForce(int[] arr, int k, int x) {
        List<Integer> list = new ArrayList<>();
        for (int num : arr) {
            list.add(num);
        }

        list.sort((a, b) -> {
            int diff = Math.abs(a - x) - Math.abs(b - x);
            return diff == 0 ? Integer.compare(a, b) : diff;
        });

        List<Integer> res = list.subList(0, k);
        Collections.sort(res);

        return res;
    }

    public static void main(String[] args) {
        FindKClosestElements solution = new FindKClosestElements();

        int[][] testArrs = {{1, 2, 3, 4, 5}, // [1,2,3,4]
                                        {1, 2, 3, 4, 5}, // [2,3,4]
                                        {1, 2, 3, 4, 5}, // [1,2]
                                        {1, 2, 3, 4, 5}, // [3]
                                        {1, 2, 3, 4, 5}, // [1]
                                        {1}, // [1]
                                        {1}, // [1]
        };
        int[][] testKs = {{4}, {3}, {2}, {1}, {1}, {1}, {1}};
        int[][] testXs = {{3}, {3}, {3}, {3}, {3}, {1}, {1}};

        for (int i = 0; i < testArrs.length; i++) {
            System.out.println("Input: " + Arrays.toString(testArrs[i]) + ", k: " + testKs[i][0]
                                            + ", x: " + testXs[i][0]);
            System.out.println("Output: " + solution.findClosestElements_optimal(testArrs[i],
                                            testKs[i][0], testXs[i][0]));
        }
    }
}
