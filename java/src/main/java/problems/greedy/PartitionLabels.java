package problems.greedy;

import java.util.ArrayList;
import java.util.List;

/**
 * DP 需要去考慮未來，Greedy 只看當下，這題需要遇到相同字母就當機立斷的切，所以可以用 greedy 去優化 DP
 */
public class PartitionLabels {
    public List<Integer> partitionLabels(String s) {
        // 1. 預處理：記錄每個字母最後出現的位置
        int[] lastIndex = new int[26];
        for (int i = 0; i < s.length(); i++) {
            lastIndex[s.charAt(i) - 'a'] = i;
        }

        List<Integer> result = new ArrayList<>();
        int start = 0; // 當前 partition 的起點
        int end = 0; // 當前 partition 必須延伸到的最遠點

        for (int i = 0; i < s.length(); i++) {
            // 更新當前 partition 的最遠邊界
            end = Math.max(end, lastIndex[s.charAt(i) - 'a']);

            // 如果當前指針 i 達到了最遠邊界，表示這個 partition 可以結束了
            if (i == end) {
                result.add(end - start + 1);
                start = i + 1; // 下一個 partition 從這裡開始
            }
        }

        return result;
    }

    public static void main(String[] args) {
        PartitionLabels solver = new PartitionLabels();

        String s1 = "ababcbacadefegdehijhklij";
        System.out.println("Test Case 1: " + s1);
        System.out.println("Result: " + solver.partitionLabels(s1)); // 應為 [9, 7, 8]

        String s2 = "eccbbbbdec";
        System.out.println("Test Case 2: " + s2);
        System.out.println("Result: " + solver.partitionLabels(s2)); // 應為 [10]
    }
}
