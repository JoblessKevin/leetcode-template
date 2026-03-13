package problems.advanceGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class NetworkDelayTime {
    public int networkDelayTime(int[][] times, int n, int k) {
        // 1. 建立圖 (Adjacency List)
        // Map<起點, List<int[]{終點, 延遲時間}>>
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int i = 1; i <= n; i++) {
            graph.put(i, new ArrayList<>());
        }
        for (int[] edge : times) {
            int u = edge[0]; // 起點
            int v = edge[1]; // 終點
            int w = edge[2]; // 權重 (時間)
            graph.get(u).add(new int[] {v, w});
        }

        // 2. 準備 PriorityQueue (Min-Heap)
        // 陣列格式: [節點編號, 到達該節點的累積時間]
        // 依照「累積時間」從小到大排序 (誰時間短，誰優先出列)
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[] {k, 0}); // 將起點加入，累積時間為 0

        // 記錄哪些節點已經找到了最短路徑
        Set<Integer> visited = new HashSet<>();
        int maxTime = 0; // 記錄訊號傳到最後一個節點所花的時間

        // 3. 開始 Dijkstra (加權 BFS)
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currNode = current[0];
            int currTime = current[1];

            // 如果這個節點已經被確定過最短路徑了，就跳過 (避免重複計算)
            if (visited.contains(currNode))
                continue;

            // 🌟 關鍵：當一個節點從 PQ 被 poll 出來的那一刻，
            // 代表我們已經找到了從起點到它的「絕對最短路徑」！
            visited.add(currNode);
            maxTime = Math.max(maxTime, currTime); // 更新最大時間

            // 提早結束優化：如果所有節點都收到了，就不用再找了
            if (visited.size() == n)
                return maxTime;

            // 遍歷所有相鄰的節點
            for (int[] neighbor : graph.get(currNode)) {
                int nextNode = neighbor[0];
                int weight = neighbor[1];

                // 只有還沒確定最短路徑的鄰居，才需要放進去排隊試試看
                if (!visited.contains(nextNode)) {
                    pq.offer(new int[] {nextNode, currTime + weight});
                }
            }
        }

        // 如果走完迴圈，visited 的數量還是小於 n，代表有節點是孤島，訊號傳不到！
        return visited.size() == n ? maxTime : -1;
    }

    public int networkDelayTime_extremeFast(int[][] times, int n, int k) {
        // 1. 拋棄 HashMap，改用 2D 陣列 (Adjacency Matrix) 存圖
        int[][] graph = new int[n + 1][n + 1];

        // 初始化圖，把距離設為無限大 (代表不通)
        for (int i = 1; i <= n; i++) {
            Arrays.fill(graph[i], Integer.MAX_VALUE);
        }

        // 填入邊的權重
        for (int[] edge : times) {
            graph[edge[0]][edge[1]] = edge[2];
        }

        // 2. 距離陣列 (記錄起點 k 到各點的最短時間)
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0; // 起點到自己是 0

        // 記錄哪些點已經確定最短路徑了
        boolean[] visited = new boolean[n + 1];

        // 3. 開始 O(V^2) 的樸素 Dijkstra
        for (int i = 1; i <= n; i++) {
            // (a) 尋找目前「尚未拜訪」且「距離起點最近」的節點
            int minNode = -1;
            int minDist = Integer.MAX_VALUE;

            // 🔥 這裡就是取代 PriorityQueue 的地方！
            // 因為 n 最多才 100，這個 for 迴圈跑起來比 PQ 拿物件還要快得多！
            for (int j = 1; j <= n; j++) {
                if (!visited[j] && dist[j] < minDist) {
                    minDist = dist[j];
                    minNode = j;
                }
            }

            // 如果找不到點，代表剩下的點根本連不到 (孤島)，提早結束
            if (minNode == -1)
                break;

            // 標記該點已確定最短路徑
            visited[minNode] = true;

            // (b) 用這個剛確定的點，去更新它所有鄰居的距離
            for (int j = 1; j <= n; j++) {
                // 如果兩點之間有路，且可以讓原本的距離變短
                if (graph[minNode][j] != Integer.MAX_VALUE) {
                    dist[j] = Math.min(dist[j], dist[minNode] + graph[minNode][j]);
                }
            }
        }

        // 4. 尋找全部節點中，花費時間最長的那個
        int maxTime = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE)
                return -1; // 還有點沒收到訊號
            maxTime = Math.max(maxTime, dist[i]);
        }

        return maxTime;
    }

    public static void main(String[] args) {
        NetworkDelayTime solution = new NetworkDelayTime();

        System.out.println("=== 測試案例 1：標準網路 (LeetCode 經典範例) ===");
        // 說明：從 2 出發，到 1 花 1秒，到 3 花 1秒。再從 3 到 4 花 1秒。
        // 總共需要等最遠的節點 4 收到訊號，也就是 1 + 1 = 2 秒。
        // 預期輸出: 2
        int[][] times1 = {{2, 1, 1}, {2, 3, 1}, {3, 4, 1}};
        int n1 = 4;
        int k1 = 2;
        System.out.println("標準版 PQ 結果: " + solution.networkDelayTime(times1, n1, k1));
        System.out.println("極速版陣列結果: " + solution.networkDelayTime_extremeFast(times1, n1, k1)
                                        + "\n");

        System.out.println("=== 測試案例 2：有孤島的網路 (傳不到) ===");
        // 說明：1 可以傳給 2，但是節點 3 根本沒有連線，永遠收不到訊號。
        // 預期輸出: -1
        int[][] times2 = {{1, 2, 1}};
        int n2 = 3;
        int k2 = 1;
        System.out.println("標準版 PQ 結果: " + solution.networkDelayTime(times2, n2, k2));
        System.out.println("極速版陣列結果: " + solution.networkDelayTime_extremeFast(times2, n2, k2)
                                        + "\n");

        System.out.println("=== 測試案例 3：權重陷阱 (Dijkstra 的精華) ===");
        // 說明：從 1 到 2 有兩條路。
        // 直達車：1 -> 2 (只過 1 條邊，但要花 100 秒)
        // 轉車：1 -> 3 -> 2 (要過 2 條邊，但總共只花 5 + 5 = 10 秒)
        // 標準 BFS 會傻傻選直達車，但 Dijkstra 會聰明地選轉車！
        // 預期輸出: 10
        int[][] times3 = {{1, 2, 100}, {1, 3, 5}, {3, 2, 5}};
        int n3 = 3;
        int k3 = 1;
        System.out.println("標準版 PQ 結果: " + solution.networkDelayTime(times3, n3, k3));
        System.out.println("極速版陣列結果: " + solution.networkDelayTime_extremeFast(times3, n3, k3)
                                        + "\n");
    }
}
