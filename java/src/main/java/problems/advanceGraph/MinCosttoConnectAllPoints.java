package problems.advanceGraph;

import java.util.PriorityQueue;

public class MinCosttoConnectAllPoints {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;

        // PriorityQueue 陣列格式: [到達的目標節點編號, 連接的成本]
        // 依照「成本」從小到大排序
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);

        boolean[] visited = new boolean[n];
        int totalCost = 0; // 記錄總花費
        int connectedCount = 0; // 記錄已經連上的點數量

        // 隨便挑一個起點開始 (通常選 0 號點)，連到自己的成本是 0
        pq.offer(new int[] {0, 0});

        while (!pq.isEmpty() && connectedCount < n) {
            int[] current = pq.poll();
            int currNode = current[0];
            int cost = current[1];

            // 🌟 關鍵防呆：如果這個點已經被我們的樹包辦了，就跳過 (避免形成環)
            if (visited[currNode])
                continue;

            // 將這個點納入我們的最小生成樹版圖中
            visited[currNode] = true;
            totalCost += cost;
            connectedCount++;

            // 提早結束優化：如果所有點都連上了，就不用再看剩下的邊了
            if (connectedCount == n)
                break;

            // 站在這個剛連上的新點，看看其他「還沒連上」的點，把可能的牽線成本丟進 PQ 排隊
            for (int nextNode = 0; nextNode < n; nextNode++) {
                if (!visited[nextNode]) {
                    // 計算曼哈頓距離
                    int nextCost = Math.abs(points[currNode][0] - points[nextNode][0]) + Math
                                                    .abs(points[currNode][1] - points[nextNode][1]);

                    pq.offer(new int[] {nextNode, nextCost});
                }
            }
        }

        return totalCost;
    }
}
