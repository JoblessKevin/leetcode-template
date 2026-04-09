package problems.greedy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

/**
 * @formatter:off
 * 頻繁操作 TreeMap 的紅黑樹（remove 和 put）會變慢: 用 HashMap + 排序唯一鍵 + 批量處理 (Batch Processing) 
 * Time: O(N log N)
 * @formatter:on
 */
public class HandOfStraights {
    public boolean isNStraightHand(int[] hand, int groupSize) {
        if (hand.length % groupSize != 0)
            return false;

        TreeMap<Integer, Integer> countMap = new TreeMap<>();
        for (int card : hand) {
            countMap.put(card, countMap.getOrDefault(card, 0) + 1);
        }

        while (!countMap.isEmpty()) {
            int firstCard = countMap.firstKey();

            for (int i = 0; i < groupSize; i++) {
                int currentCard = firstCard + i;
                if (!countMap.containsKey(currentCard))
                    return false;

                countMap.put(currentCard, countMap.get(currentCard) - 1);
                if (countMap.get(currentCard) == 0)
                    countMap.remove(currentCard);
            }
        }

        return true;
    }

    /**
     * O(N log N)
     */
    public boolean isNStraightHand_orderedMap(int[] hand, int groupSize) {
        // 1. 基本檢查
        if (hand.length % groupSize != 0)
            return false;
        if (groupSize == 1)
            return true;

        // 2. 使用 HashMap 統計次數（比 TreeMap 快，因為不用一邊存一邊排）
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int card : hand) {
            countMap.put(card, countMap.getOrDefault(card, 0) + 1);
        }

        // 3. 取得所有不重複的牌並排序
        int[] uniqueCards = new int[countMap.size()];
        int idx = 0;
        for (int card : countMap.keySet()) {
            uniqueCards[idx++] = card;
        }
        Arrays.sort(uniqueCards);

        // 4. 批量處理
        for (int card : uniqueCards) {
            int count = countMap.get(card);

            if (count > 0) {
                // 如果這張牌還有剩，它必須作為某幾組順子的「起點」
                for (int i = 1; i < groupSize; i++) {
                    int nextCard = card + i;
                    int nextCount = countMap.getOrDefault(nextCard, 0);

                    // 如果後續的牌不夠扣，直接失敗
                    if (nextCount < count)
                        return false;

                    // 批量扣除次數
                    countMap.put(nextCard, nextCount - count);
                }
            }
        }

        return true;
    }


    /**
     * O(N log N), TreeMap 的底層是紅黑樹，會先做好排序 O(N log M), M 為不重複的牌數
     */
    public boolean isNStraightHand_queue(int[] hand, int groupSize) {
        if (hand.length % groupSize != 0)
            return false;
        if (groupSize == 1)
            return true;

        // 1. 統計次數（使用 TreeMap 確保按順序處理）
        TreeMap<Integer, Integer> counts = new TreeMap<>();
        for (int h : hand)
            counts.put(h, counts.getOrDefault(h, 0) + 1);

        // 2. Queue 儲存「每一站新開了幾組順子」
        Queue<Integer> startCounts = new LinkedList<>();
        int opened = 0; // 目前正在進行中的順子總數
        int lastCard = -1;

        for (int card : counts.keySet()) {
            int count = counts.get(card);

            // 如果中間斷掉了（不連續）且還有順子沒結尾，必敗
            if (opened > 0 && card > lastCard + 1)
                return false;

            // 如果目前的牌不夠供應「正在進行中」的順子，必敗
            if (count < opened)
                return false;

            // 計算在這一站「新開」了幾組順子
            int newStarts = count - opened;
            startCounts.add(newStarts);
            opened += newStarts;

            // 如果 Queue 長度達到 groupSize，代表最老的那批順子該結束了
            if (startCounts.size() == groupSize) {
                opened -= startCounts.poll();
            }

            lastCard = card;
        }

        // 最後看是不是所有順子都剛好結尾了
        return opened == 0;
    }

    /**
     * O(N)
     */
    public boolean isNStraightHand_optimized(int[] hand, int groupSize) {
        if (hand.length % groupSize != 0)
            return false;

        Map<Integer, Integer> count = new HashMap<>();
        for (int num : hand) {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }

        for (int num : hand) {
            int start = num;
            while (count.getOrDefault(start - 1, 0) > 0)
                start--;
            while (start <= num) {
                while (count.getOrDefault(start, 0) > 0) {
                    for (int i = start; i < start + groupSize; i++) {
                        if (count.getOrDefault(i, 0) == 0)
                            return false;
                        count.put(i, count.get(i) - 1);
                    }
                }
                start++;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        HandOfStraights solver = new HandOfStraights();

        int[] hand1 = {1, 2, 3, 6, 7, 8};
        int groupSize1 = 3;
        System.out.println("Test Case 1: hand=" + java.util.Arrays.toString(hand1) + ", groupSize="
                                        + groupSize1);
        System.out.println("Result: " + solver.isNStraightHand(hand1, groupSize1)); // 應為 true

        int[] hand2 = {1, 2, 3, 4, 5, 6};
        int groupSize2 = 4;
        System.out.println("Test Case 2: hand=" + java.util.Arrays.toString(hand2) + ", groupSize="
                                        + groupSize2);
        System.out.println("Result: " + solver.isNStraightHand(hand2, groupSize2)); // 應為 false
    }
}
