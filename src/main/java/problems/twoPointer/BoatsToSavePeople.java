package problems.twoPointer;

import java.util.Arrays;

public class BoatsToSavePeople {
    /**
     * Time complexity: O(nlogn) for sorting + O(n) for two pointers Space complexity: O(1)
     */
    public int numRescueBoats_twoPointers(int[] people, int limit) {
        Arrays.sort(people);

        int boats = 0;
        int left = 0;
        int right = people.length - 1;

        while (left <= right) {
            boats++;

            if (people[left] + people[right] <= limit) {
                left++;
            }

            right--;
        }

        return boats;
    }

    /**
     * Time complexity: O(n + m) where m is the max weight in people. Space complexity: O(m)
     */
    public int numRescueBoats_countingSort(int[] people, int limit) {
        int m = Arrays.stream(people).max().getAsInt();
        int[] count = new int[m + 1];
        for (int p : people) {
            count[p]++;
        }

        int idx = 0, i = 1;
        while (idx < people.length) {
            while (count[i] == 0) {
                i++;
            }
            people[idx++] = i;
            count[i]--;
        }

        int res = 0, l = 0, r = people.length - 1;
        while (l <= r) {
            int remain = limit - people[r--];
            res++;
            if (l <= r && remain >= people[l]) {
                l++;
            }
        }
        return res;
    }
}
