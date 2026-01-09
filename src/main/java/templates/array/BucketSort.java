package templates.array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BucketSort {
    public void bucketSort(int[] nums) {
        // 0. 特殊狀況處理
        if (nums == null || nums.length <= 1)
            return;

        // 1. 找出最大值與最小值 (為了計算範圍)
        int maxVal = nums[0];
        int minVal = nums[0];
        for (int num : nums) {
            maxVal = Math.max(maxVal, num);
            minVal = Math.min(minVal, num);
        }

        // 2. 初始化桶子
        // 這裡我們設定每個桶子的「容量範圍 (Range)」
        // 例如：bucketSize = 5，代表一個桶子裝 0~4, 下個裝 5~9...
        int bucketSize = 5;

        // 計算需要幾個桶子
        // 公式：(最大 - 最小) / 每個桶的大小 + 1
        int bucketCount = (maxVal - minVal) / bucketSize + 1;

        // 建立桶子列表 (解決泛型陣列警告的標準寫法)
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        // 3. 分發 (Scatter): 把數字放入對應的桶子
        for (int num : nums) {
            // 核心公式：計算這個數字該去哪個桶子
            int bucketIndex = (num - minVal) / bucketSize;
            buckets.get(bucketIndex).add(num);
        }

        // 4. & 5. 桶內排序與收集 (Sort & Gather)
        int currentIndex = 0;
        for (List<Integer> bucket : buckets) {
            // 如果桶子是空的就跳過
            if (bucket.isEmpty())
                continue;

            // 4. 桶內排序
            // 使用 Collections.sort (TimSort) 確保最差只有 O(N log N)
            // 避免使用 Insertion Sort 導致退化成 O(N^2)
            Collections.sort(bucket);

            // 5. 收集回原陣列
            for (int num : bucket) {
                nums[currentIndex++] = num;
            }
        }
    }

    public static void main(String[] args) {
        BucketSort sorter = new BucketSort();
        int[] input = { 45, 23, 11, 89, 77, 98, 4, 28, 65, 43 };

        System.out.println("排序前:");
        for (int i : input)
            System.out.print(i + " ");

        sorter.bucketSort(input);

        System.out.println("\n排序後:");
        for (int i : input)
            System.out.print(i + " ");
    }
}
