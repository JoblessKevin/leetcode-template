package problems.intervals;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MeetingRoomsIII {
    public int mostBooked(int n, int[][] meetings) {
        // 1. 基礎排序：所有會議按「原始開始時間」排序
        Arrays.sort(meetings, (a, b) -> Integer.compare(a[0], b[0]));

        // 2. 雙 Heap 管理
        // freeRooms: 存儲目前空閒的房間 ID (Min-Heap, ID 小優先)
        PriorityQueue<Integer> freeRooms = new PriorityQueue<>();
        for (int i = 0; i < n; i++)
            freeRooms.offer(i);

        // busyRooms: 存儲使用中的房間 [結束時間, 房間 ID] (Min-Heap)
        // 注意：結束時間可能很大，建議用 long 避免溢位
        PriorityQueue<long[]> busyRooms = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0])
                return Long.compare(a[0], b[0]); // 結束時間早優先
            return Long.compare(a[1], b[1]); // 結束時間同，ID 小優先
        });

        // 3. 記帳本：紀錄每個房間舉辦的會議次數
        int[] count = new int[n];

        // 4. 開始調度
        for (int[] meeting : meetings) {
            long start = meeting[0];
            long end = meeting[1];
            long duration = end - start;

            // A. 狀態轉移：將「在此會議開始前」已經結束的房間，從 Busy 移回 Free
            while (!busyRooms.isEmpty() && busyRooms.peek()[0] <= start) {
                freeRooms.offer((int) busyRooms.poll()[1]);
            }

            // B. 決策分配
            if (!freeRooms.isEmpty()) {
                // 情境 1：有空房！選 ID 最小的那間
                int roomId = freeRooms.poll();
                count[roomId]++;
                busyRooms.offer(new long[] {end, roomId});
            } else {
                // 情境 2：沒空房 (需排隊)！等最快空出來的那間
                long[] soonestRoom = busyRooms.poll();
                long availableTime = soonestRoom[0];
                int roomId = (int) soonestRoom[1];

                count[roomId]++;
                // 重點：新結束時間 = 房間空出的時間 + 會議原本的持續時間
                busyRooms.offer(new long[] {availableTime + duration, roomId});
            }
        }

        // 5. 統計結果
        int maxMeetings = -1;
        int resultRoom = 0;
        for (int i = 0; i < n; i++) {
            if (count[i] > maxMeetings) {
                maxMeetings = count[i];
                resultRoom = i;
            }
        }

        return resultRoom;
    }

    // @formatter:off
    public static void main(String[] args) {
        MeetingRoomsIII solver = new MeetingRoomsIII();

        // --- Test Case 1: 基本情況 (無延遲) ---
        // 房間充足，且會議時間錯開。
        int n1 = 2;
        int[][] meetings1 = {{0, 10}, {1, 5}, {2, 7}};
        // 會議 0 -> 房 0 [0, 10]
        // 會議 1 -> 房 1 [1, 5]
        // 會議 2 -> 房 1 [2, 7] 被延遲至 [5, 10] (因為房 1 較早空)
        System.out.println("Test Case 1 Result: " + solver.mostBooked(n1, meetings1)); // 預期輸出: 1

        // --- Test Case 2: 經典延遲 (LeetCode 範例) ---
        // 只有 2 間房，但有 4 個重疊嚴重的會議。
        int n2 = 2;
        int[][] meetings2 = {{0, 10}, {1, 2}, {1, 2}, {1, 2}};
        // [0, 10] -> 房 0
        // [1, 2]  -> 房 1
        // [1, 2]  -> 沒房了，等房 1 在時間 2 空出，變 [2, 3] -> 房 1
        // [1, 2]  -> 沒房了，等房 1 在時間 3 空出，變 [3, 4] -> 房 1
        System.out.println("Test Case 2 Result: " + solver.mostBooked(n2, meetings2)); // 預期輸出: 1

        // --- Test Case 3: 最小 ID 優先權 ---
        // 當 0, 1, 2 號房都空閒時，必須選 0 號。
        int n3 = 3;
        int[][] meetings3 = {{1, 20}, {2, 10}, {3, 5}, {4, 9}, {6, 8}};
        System.out.println("Test Case 3 Result: " + solver.mostBooked(n3, meetings3)); // 預期輸出: 1 (根據實際分配次數)

        // --- Test Case 4: 大數據時間戳與延遲 (long 溢位測試) ---
        // 測試延遲後時間是否正確累加。
        int n4 = 2;
        int[][] meetings4 = {{0, 5}, {1, 2}, {1, 10}};
        // [0, 5] -> 房 0
        // [1, 2] -> 房 1
        // [1, 10] -> 房 1 在時間 2 空出，延遲後變成 [2, 11] -> 房 1
        System.out.println("Test Case 4 Result: " + solver.mostBooked(n4, meetings4)); // 預期輸出: 1

        // --- Test Case 5: 絕對領先 (Room 0 承接所有會議) ---
        // 雖然有 3 個房間，但會議完全沒重疊。
        // 因為 Room 0 每次都最早空出來，且 ID 最小，所以它會包辦所有會議。
        int n5 = 3;
        int[][] meetings5 = {{0, 5}, {6, 10}, {11, 15}};
        // [0, 5] -> Room 0
        // [6, 10] -> Room 0 (0 號房空閒且 ID 最小)
        // [11, 15] -> Room 0
        System.out.println("Test Case 5 Result: " + solver.mostBooked(n5, meetings5)); 
        // 預期輸出: 0 (Room 0 辦了 3 次，其他 0 次)

        // --- Test Case 6: 平手規則 (Tie-breaker) ---
        // 2 個房間，4 場會議。最後 Room 0 和 Room 1 都舉辦 2 次。
        int n6 = 2;
        int[][] meetings6 = {{0, 10}, {1, 5}, {11, 15}, {12, 14}};
        // 1. [0, 10] -> Room 0
        // 2. [1, 5]  -> Room 1
        // 3. [11, 15] -> 此時 0, 1 號房都空了，選 ID 小的 -> Room 0
        // 4. [12, 14] -> 只有 Room 1 空閒 -> Room 1
        // 結果：Room 0 (2次), Room 1 (2次)。平手取 ID 小的。
        System.out.println("Test Case 6 Result: " + solver.mostBooked(n6, meetings6)); 
        // 預期輸出: 0
    }
}
