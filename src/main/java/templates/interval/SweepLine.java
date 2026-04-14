package templates.interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @formatter:off
 * Sweep Line Algorithm (掃描線演算法) 核心模板
 * 【適用場景】
 * 1. 計算最大重疊次數
 * 2. 計算總覆蓋長度 (Total Covered Length)
 * 3. 處理點與區間的包含關係 (Points and Intervals)
 * 【演算法心法】
 * 將區間 [start, end] 拆解成兩個「事件」：一個是「進入 (+1)」，一個是「離開 (-1)」。
 * 想像一條線從左向右掃過時間軸，每遇到一個事件就更新目前的狀態 (count)。
 * 【三大步驟】
 * 1. 事件化：將 N 個區間拆成 2N 個事件點，記錄其「時間點」與「增量」。
 * 2. 排序(關鍵)：按時間排序。時間相同時，需根據業務邏輯決定誰先誰後 (Tie-breaking)。
 * 3. 掃描：遍歷排序後的事件，累積狀態值並更新結果。
 * 【時間複雜度】 O(N log N) - 主要開銷在於事件排序。
 * 【空間複雜度】 O(N) - 需要額外空間存儲 2N 個事件。
 * @formatter:on
 */
public class SweepLine {
    public int sweepLineTemplate(int[][] intervals) {
        // 1. 定義事件點 (Time, Type)
        // Type 通常定義：1 為進入 (Start), -1 為離開 (End)
        List<int[]> events = new ArrayList<>();
        for (int[] interval : intervals) {
            events.add(new int[] {interval[0], 1}); // 起點事件
            events.add(new int[] {interval[1], -1}); // 終點事件
        }

        // 2. 核心：排序邏輯 (決定掃描線的正確性)
        Collections.sort(events, (a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]); // 首先按時間排序
            }
            // 重要：當時間相同時，誰優先？
            // 如果題目規定 [1, 2] 和 [2, 3] 算重疊 -> Start(1) 優先於 End(-1)
            // 如果題目規定 [1, 2] 和 [2, 3] 不重疊 -> End(-1) 優先於 Start(1)
            return Integer.compare(b[1], a[1]);
        });

        // 3. 掃描與狀態維護
        int count = 0; // 目前重疊的區間數
        int maxOverlap = 0; // 紀錄峰值

        for (int[] event : events) {
            count += event[1]; // 進入則 +1，離開則 -1

            // 根據題目需求更新狀態
            maxOverlap = Math.max(maxOverlap, count);

            // 如果是 Merge Intervals，則在這裡判斷 count 0->1 或 1->0 的時刻
        }

        return maxOverlap;
    }
}
