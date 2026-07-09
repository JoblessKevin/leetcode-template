package problems.twoPointer;

import java.util.Arrays;

/**@formatter:off */
public class BoatsToSavePeople {
    /**
     * Time complexity: O(nlogn) for sorting + O(n) for two pointers Space complexity: O(1)
     */
    public int numRescueBoats_twoPointers(int[] people, int limit) {
        Arrays.sort(people);

        int res = 0;
        int left = 0;
        int right = people.length - 1;

        while (left <= right) {
            res++;

            if (people[left] + people[right] <= limit) {
                left++;
            }

            right--;
        }

        return res;
    }

    /**
     * Time complexity: O(n + m) where m is the max weight in people. Space complexity: O(m)
     */
    public int numRescueBoats_countingSort(int[] people, int limit) {
        int m = Arrays.stream(people).max().getAsInt();
        /**
        int m = people[0];
        for (int i = 1; i < people.length; i++) {
            if (people[i] > m) {
                m = people[i];
            }
        }        
         */
        int[] count = new int[m + 1];
        for (int p : people) {
            count[p]++;
        }

        // 將統計結果還原成有序的陣列
        int idx = 0, i = 1;
        while (idx < people.length) {
            while (count[i] == 0) {
                i++;
            }
            people[idx++] = i;
            count[i]--;
        }

        // 使用雙指針計算最少的船數
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

    public static void main(String[] args) {
        BoatsToSavePeople solver = new BoatsToSavePeople();

        // 測試案例
        int[] people = {3, 2, 2, 1};
        int limit = 3;

        System.out.println("人員體重: " + Arrays.toString(people));
        System.out.println("船限重: " + limit);
        System.out.println("----------------------------");

        // 測試雙指標解法
        // 注意：Arrays.sort 會修改原陣列，所以這裡我們傳入副本或重新初始化
        int res1 = solver.numRescueBoats_twoPointers(people.clone(), limit);
        System.out.println("雙指標解法結果: " + res1 + " 艘船");

        // 測試 Counting Sort 解法
        int res2 = solver.numRescueBoats_countingSort(people.clone(), limit);
        System.out.println("計數排序解法結果: " + res2 + " 艘船");
    }
}
