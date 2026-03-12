package templates.graph;

public class BellmanFord {

    // 定義「邊」的結構
    class Edge {
        int src, dest, weight;

        Edge() {
            src = dest = weight = 0;
        }
    }

    int V, E;
    Edge edge[]; // 儲存圖中所有邊的陣列

    // 建構子：初始化頂點數與邊數
    BellmanFord(int v, int e) {
        V = v;
        E = e;
        edge = new Edge[e];
        for (int i = 0; i < e; ++i) {
            edge[i] = new Edge();
        }
    }

    // 執行 Bellman-Ford 演算法，src 是起點
    void runBellmanFord(int src) {
        int dist[] = new int[V];

        // 1. 初始化距離陣列：起點設為 0，其餘設為無限大
        for (int i = 0; i < V; ++i) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[src] = 0;

        // 2. 核心迴圈：對所有邊進行 V-1 次的「鬆弛」
        for (int i = 1; i < V; ++i) {
            for (int j = 0; j < E; ++j) {
                int u = edge[j].src;
                int v = edge[j].dest;
                int weight = edge[j].weight;

                // 如果起點能走到 u，且透過 u 走到 v 的距離比原本紀錄的還要短
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                }
            }
        }

        // 3. 第 V 次檢查：偵測是否存在「負權重迴圈」
        for (int j = 0; j < E; ++j) {
            int u = edge[j].src;
            int v = edge[j].dest;
            int weight = edge[j].weight;

            if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                System.out.println("警告：圖中包含負權重迴圈！最短路徑無法收斂。");
                return; // 直接中斷，因為結果已經沒有意義了
            }
        }

        // 4. 印出結果
        printSolution(dist);
    }

    // 輔助函式：印出結果
    void printSolution(int dist[]) {
        System.out.println("從起點出發到各頂點的最短距離：");
        for (int i = 0; i < V; ++i) {
            System.out.println("到頂點 " + i + " 的距離為: " + dist[i]);
        }
    }

    public static void main(String[] args) {
        int V = 5; // 5 個頂點
        int E = 8; // 8 條邊
        BellmanFord graph = new BellmanFord(V, E);

        // 建立圖的邊 (A=0, B=1, C=2, D=3, E=4)
        // 這裡已經補上了,... 等陣列索引
        graph.edge[0].src = 0;
        graph.edge[0].dest = 1;
        graph.edge[0].weight = -1;

        graph.edge[1].src = 0;
        graph.edge[1].dest = 2;
        graph.edge[1].weight = 4;

        graph.edge[2].src = 1;
        graph.edge[2].dest = 2;
        graph.edge[2].weight = 3;

        graph.edge[3].src = 1;
        graph.edge[3].dest = 3;
        graph.edge[3].weight = 2;

        graph.edge[4].src = 1;
        graph.edge[4].dest = 4;
        graph.edge[4].weight = 2;

        graph.edge[5].src = 3;
        graph.edge[5].dest = 2;
        graph.edge[5].weight = 5;

        graph.edge[6].src = 3;
        graph.edge[6].dest = 1;
        graph.edge[6].weight = 1;

        graph.edge[7].src = 4;
        graph.edge[7].dest = 3;
        graph.edge[7].weight = -3;

        // 從頂點 0 開始執行
        graph.runBellmanFord(0);
    }
}
