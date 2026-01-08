package problems.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Constraints: You may return the answer in any order.
 * 1. Standard Solution: MinHeap -> 適合一般通用情況，適用於 Stream, Time: O(N log k), Space: O(N)
 * 2. Optimal Solution: Bucket Sort -> 適合此題 (頻率有限制) 的最佳解, Time: O(N), Space: O(N)
 */
public class TopKFrequentElements {

    /**
     * 解法一：Standard Solution (MinHeap)
     * 核心思路：維護一個大小為 k 的最小堆積，踢掉頻率最低的元素。
     */
    public int[] topKFrequentViaHeap(int[] nums, int k) {
        // 1. 統計頻率
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        // 2. 建立 Min-Heap (依據頻率由小到大排)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((n1, n2) -> countMap.get(n1) - countMap.get(n2));

        // 3. 維護 Top K
        for (int num : countMap.keySet()) {
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
    }

    /**
     * 解法二：Optimal Solution (Bucket Sort)
     * 核心思路：因為頻率不可能超過 N，所以可以用陣列索引代表頻率，直接放入對應的桶子。
     */
    public int[] topKFrequentViaBucketSort(int[] nums, int k) {
        // 1. 統計頻率
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        // 2. 建立桶子 (Index = 頻率, Value = 該頻率的數字 List)
        // 頻率最多就是 nums.length (全部都一樣)，所以大小設為 length + 1
        List<Integer>[] buckets = new List[nums.length + 1];

        for (int num : countMap.keySet()) {
            int freq = countMap.get(num);
            if (buckets[freq] == null) {
                buckets[freq] = new ArrayList<>();
            }
            buckets[freq].add(num);
        }

        // 3. 從後往前 (高頻 -> 低頻) 倒出 K 個
        List<Integer> resultList = new ArrayList<>();
        for (int i = buckets.length - 1; i >= 0 && resultList.size() < k; i--) {
            if (buckets[i] != null) {
                resultList.addAll(buckets[i]);
            }
        }

        // 4. 轉成 int[]
        return resultList.stream().mapToInt(i -> i).toArray();
    }

    /**
     * 解法二：Optimal Solution (Bucket Sort) -- 好理解版
     */
    public int[] topKFrequentViaBucketSort_betterUnderstanding(int[] nums, int k) {
        // 1. 統計頻率
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        // 2. 建立桶子 (Index = 頻率, Value = 該頻率的數字 List)
        // 頻率最多就是 nums.length (全部都一樣)，所以大小設為 length + 1
        List<List<Integer>> buckets = new ArrayList<>();

        for (int i = 0; i < nums.length + 1; i++) {
            buckets.add(new ArrayList<>());
        }

        for (int num : countMap.keySet()) {
            int freq = countMap.get(num);
            buckets.get(freq).add(num);
        }

        // 3. 從後往前 (高頻 -> 低頻) 倒出 K 個
        List<Integer> resultList = new ArrayList<>();
        for (int i = buckets.size() - 1; i >= 0 && resultList.size() < k; i--) {
            if (buckets.get(i) != null) {
                resultList.addAll(buckets.get(i));
            }
        }

        // 4. 轉成 int[]
        return resultList.stream().mapToInt(i -> i).toArray();
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
        int[] resHeap = solution.topKFrequentViaHeap(nums, k);
        System.out.println("Heap Result: " + Arrays.toString(resHeap));

        // 測試 Bucket Sort 解法
        int[] resBucket = solution.topKFrequentViaBucketSort(nums, k);
        System.out.println("Bucket Sort Result: " + Arrays.toString(resBucket));

        // 測試 Bucket Sort 解法 (好理解版)
        int[] resBucket_betterUnderstanding = solution.topKFrequentViaBucketSort_betterUnderstanding(nums, k);
        System.out.println("Bucket Sort Better Understanding Result: " + Arrays.toString(resBucket_betterUnderstanding));
    }
}