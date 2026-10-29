package problems.advanceGraph;

class MinCostConnectPoints {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;

        // minDist[i] 記錄：第 i 個點距離「目前最小生成樹」的最短距離
        int[] minDist = new int[n];
        boolean[] visited = new boolean[n];

        // 一開始，所有點的距離都預設為無限大
        for (int i = 0; i < n; i++) {
            minDist[i] = Integer.MAX_VALUE;
        }

        // 從第 0 個點開始發芽，抵達自己的成本是 0
        minDist[0] = 0;

        int mstWeight = 0;

        // 總共有 n 個點，所以要擴張 n 次
        for (int i = 0; i < n; i++) {

            // 步驟 A：在所有「未造訪」的點中，尋找 minDist 最小的點 u
            int u = -1;
            int minVal = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && minDist[j] < minVal) {
                    minVal = minDist[j];
                    u = j;
                }
            }

            // 步驟 B：將找到的最便宜的點 u 納入最小生成樹
            visited[u] = true;
            mstWeight += minVal;

            // 步驟 C：站在剛佔領的點 u 上，更新它到所有「未造訪點 v」的距離
            for (int v = 0; v < n; v++) {
                if (!visited[v]) {
                    // 邊走邊算曼哈頓距離，省下建圖的記憶體！
                    int dist = Math.abs(points[u][0] - points[v][0])
                                                    + Math.abs(points[u][1] - points[v][1]);

                    // 如果這條新路徑比舊路徑更便宜，就更新它
                    if (dist < minDist[v]) {
                        minDist[v] = dist;
                    }
                }
            }
        }

        return mstWeight;
    }

    // --- 本機測試區 ---
    public static void main(String[] args) {
        // 建立物件實例來呼叫方法
        MinCostConnectPoints solution = new MinCostConnectPoints();

        // 測試用例 1：LeetCode 上的經典範例
        int[][] points1 = {{0, 0}, {2, 2}, {3, 10}, {5, 2}, {7, 0}};
        int result1 = solution.minCostConnectPoints(points1);
        System.out.println("測試用例 1:");
        System.out.println("預期輸出: 20");
        System.out.println("實際輸出: " + result1);
        System.out.println("結果: " + (result1 == 20 ? " 通過" : " 失敗"));
        System.out.println("------------------------");

        // 測試用例 2：包含負數座標的範例
        int[][] points2 = {{3, 12}, {-2, 5}, {-4, 1}};
        int result2 = solution.minCostConnectPoints(points2);
        System.out.println("測試用例 2:");
        System.out.println("預期輸出: 18");
        System.out.println("實際輸出: " + result2);
        System.out.println("結果: " + (result2 == 18 ? " 通過" : " 失敗"));

        // 測試用例 3：邏輯驗證失敗 (故意寫錯預期結果)
        // 點 (0,0) 到 (1,1) 的曼哈頓距離是 2。所以正確答案應該是 2。
        int[][] points3 = {{0, 0}, {1, 1}};
        int result3 = solution.minCostConnectPoints(points3);
        int wrongExpected = 99; // 故意給一個錯誤的預期答案
        System.out.println("------------------------");
        System.out.println("測試用例 3 (模擬結果不如預期):");
        System.out.println("預期輸出: " + wrongExpected);
        System.out.println("實際輸出: " + result3);
        System.out.println("結果: " + (result3 == wrongExpected ? " 通過" : " 失敗 (抓到錯誤了！)"));

        // 測試用例 4：極端邊界條件導致的程式崩潰 (Runtime Error)
        // 實戰中如果傳入「空陣列」，原本的演算法會當機！我們用 try-catch 把它捕捉起來看錯誤訊息。
        System.out.println("------------------------");
        System.out.println("測試用例 4 (模擬極端條件導致當機):");
        int[][] points4 = {}; // 空陣列
        try {
            int result4 = solution.minCostConnectPoints(points4);
            System.out.println("實際輸出: " + result4);
        } catch (Exception e) {
            System.out.println("結果:  失敗！程式發生崩潰 (Exception)");
            System.out.println("錯誤類型: " + e.toString());
            System.out.println("崩潰原因: 當陣列長度為 0 時，第 18 行的 minDist[0] = 0; 會找不到第 0 個元素！");
        }
    }
}
