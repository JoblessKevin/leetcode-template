package problems.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Constraints: You may return the answer in any order. 1. Standard Solution: MinHeap ->
 * 適合一般通用情況，適用於 Stream, Time: O(N log k), Space: O(N) 2. Optimal Solution: Bucket Sort -> 適合此題
 * (頻率有限制) 的最佳解, Time: O(N), Space: O(N)
 */
public class TopKFrequentElements {

    /**
     * 解法一：Standard Solution (MinHeap) 核心思路：維護一個大小為 k 的最小堆積，踢掉頻率最低的元素。
     */
    public int[] topKFrequent(int[] nums, int k) {
        // 1. 統計頻率
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // 2. 建立 Min-Heap (依據頻率由小到大排)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> map.get(a) - map.get(b));

        // PriorityQueue<Map.Entry<Integer, Integer>> minHeap =
        // new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());

        // 3. 維護 Top K
        for (int num : map.keySet()) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll(); // 踢掉頻率最小的
            }
        }

        // 4. 轉成 Array
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = minHeap.poll();
        }
        return result;
        // return minHeap.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * 解法二：Optimal Solution (Bucket Sort) 核心思路：因為頻率不可能超過 N，所以可以用陣列索引代表頻率，直接放入對應的桶子。
     */
    public int[] topKFrequent_bucketSort(int[] nums, int k) {
        // 1. 統計頻率（你的第一步，寫得很棒！）
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // 2. 建立桶子陣列，長度必須是 nums.length + 1，多一個第一格放 frequecy 為 0 的數字（雖然不會有，但為了對齊索引）
        List<List<Integer>> freq = new ArrayList<>(nums.length + 1);
        for (int i = 0; i <= nums.length; i++) {
            freq.add(new ArrayList<>()); // 提早把裡面的小 List 都 new 好
        }

        // 3. 把資料分發到桶子裡。這裡必須同時拿到「數字(Key)」和「次數(Value)」
        for (var entry : map.entrySet()) {
            int num = entry.getKey();
            int count = entry.getValue();

            freq.get(count).add(num);
        }

        // 4. 準備收割答案（修正原本的型態與迴圈範圍）
        int[] results = new int[k]; // 修正型態為 int[]
        int j = 0;

        // 核心修正：必須從桶子的最後一格（最高頻率）開始往前撈！
        for (int i = freq.size() - 1; i >= 0; i--) {
            for (int num : freq.get(i)) {
                results[j] = num;
                j++;

                if (j == k) {
                    return results;
                }
            }
        }

        return results;
    }

    public int[] topKFrequent_sort_bruteforce(int[] nums, int k) {
        // Step 1: 用 HashMap 統計每個數字出現的次數
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        // Step 2: 把 Map 裡的所有數字（Key）拿出來，放進一個 List 準備排序
        List<Integer> keys = new ArrayList<>(map.keySet());

        // Step 3: 根據他們在 Map 裡對應的「次數」進行降序排序（大到小）
        // (a, b) -> countMap.get(b) - countMap.get(a) 代表次數多的排前面
        keys.sort((a, b) -> map.get(b) - map.get(a));
        // uniqueNums.sort((a, b) -> Integer.compare(countMap.get(b), countMap.get(a)));

        // Step 4: 把排在前 k 名的數字撈出來，塞進結果陣列中
        int[] results = new int[k];
        for (int i = 0; i < k; i++) {
            results[i] = keys.get(i);
        }

        return results;
    }

    // ==========================================
    // Test cases
    // ==========================================
    public static void main(String[] args) {
        TopKFrequentElements solution = new TopKFrequentElements();

        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = 2;
        System.out.println("Input: nums = " + Arrays.toString(nums) + ", k = " + k);

        // 測試 Heap 解法
        int[] resHeap = solution.topKFrequent(nums, k);
        System.out.println("Heap Result: " + Arrays.toString(resHeap));

        // 測試 Bucket Sort 解法
        int[] resBucket = solution.topKFrequent_bucketSort(nums, k);
        System.out.println("Bucket Sort Result: " + Arrays.toString(resBucket));
    }
}
