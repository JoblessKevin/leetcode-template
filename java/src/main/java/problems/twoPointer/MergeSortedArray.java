package problems.twoPointer;

import java.util.Arrays;

public class MergeSortedArray {
    public void merge_sort(int[] nums1, int m, int[] nums2, int n) {
        for (int i = 0; i < n; i++) {
            nums1[i + m] = nums2[i];
        }

        Arrays.sort(nums1);
    }

    public void merge_copy_arrays(int[] nums1, int m, int[] nums2, int n) {
        int[] nums1Copy = Arrays.copyOf(nums1, m);
        int idx = 0, i = 0, j = 0;

        while (idx < m + n) {
            if (j >= n || (i < m && nums1Copy[i] <= nums2[j])) {
                nums1[idx++] = nums1Copy[i++];
            } else {
                nums1[idx++] = nums2[j++];
            }
        }
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;
        int p2 = n - 1;
        int p = m + n - 1;

        while (p1 >= 0 && p2 >= 0) {
            if (nums1[p1] > nums2[p2]) {
                nums1[p] = nums1[p1];
                p1--;
            } else {
                nums1[p] = nums2[p2];
                p2--;
            }
            p--;
        }

        while (p2 >= 0) {
            nums1[p] = nums2[p2];
            p2--;
            p--;
        }
    }

    public void merge_optimized(int[] nums1, int m, int[] nums2, int n) {
        int last = m + n - 1;
        int i = m - 1, j = n - 1;

        while (j >= 0) {
            if (i >= 0 && nums1[i] > nums2[j]) {
                nums1[last--] = nums1[i--];
            } else {
                nums1[last--] = nums2[j--];
            }
        }
    }

    public static void main(String[] args) {
        MergeSortedArray sol = new MergeSortedArray();

        // 測試案例 1: 一般情況
        int[] nums1_1 = {1, 2, 3, 0, 0, 0};
        int[] nums2_1 = {2, 5, 6};
        sol.merge(nums1_1, 3, nums2_1, 3);
        System.out.println("Test 1: " + Arrays.toString(nums1_1)); // 預期: [1, 2, 2, 3, 5, 6]

        // 測試案例 2: nums1 為空 (m=0)
        int[] nums1_2 = {0};
        int[] nums2_2 = {1};
        sol.merge(nums1_2, 0, nums2_2, 1);
        System.out.println("Test 2: " + Arrays.toString(nums1_2)); // 預期: [1]

        // 測試案例 3: nums2 為空 (n=0)
        int[] nums1_3 = {1};
        int[] nums2_3 = {};
        sol.merge(nums1_3, 1, nums2_3, 0);
        System.out.println("Test 3: " + Arrays.toString(nums1_3)); // 預期: [1]

        // 測試案例 4: nums2 的元素都比 nums1 小
        int[] nums1_4 = {4, 5, 6, 0, 0, 0};
        int[] nums2_4 = {1, 2, 3};
        sol.merge(nums1_4, 3, nums2_4, 3);
        System.out.println("Test 4: " + Arrays.toString(nums1_4)); // 預期: [1, 2, 3, 4, 5, 6]
    }
}
