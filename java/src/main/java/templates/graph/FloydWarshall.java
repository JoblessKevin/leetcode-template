package templates.graph;

public class FloydWarshall {
    // 定義一個「無限大」的值來代表兩點之間目前不相通
    // 注意：不要直接用 Integer.MAX_VALUE，因為在後面相加時會導致整數溢位 (Overflow) 變成負數
    final static int INF = 99999;
    final static int V = 4; // 假設這張圖有 4 個頂點

    public void floydWarshall(int graph[][]) {
        // 建立一個新的陣列來儲存最短距離，避免直接修改到原始輸入的圖
        int dist[][] = new int[V][V];

        // 1. 初始化距離矩陣：將一開始的連線狀態複製到 dist 陣列中
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                dist[i][j] = graph[i][j];
            }
        }

        // 2. 演算法核心：就是這三個簡潔的 for 迴圈
        // 【關鍵】k 是「中繼點」，必須放在最外層！
        for (int k = 0; k < V; k++) {
            // i 是「起點」
            for (int i = 0; i < V; i++) {
                // j 是「終點」
                for (int j = 0; j < V; j++) {

                    // 如果 i 能走到 k，且 k 能走到 j
                    if (dist[i][k] != INF && dist[k][j] != INF) {
                        // 檢查「透過中繼點 k」會不會比「原本 i 到 j 的距離」更短
                        if (dist[i][k] + dist[k][j] < dist[i][j]) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                        }
                    }

                }
            }
        }

        // 3. 印出最終算出來的最短距離矩陣
        printSolution(dist);
    }

    // 輔助函式：用來印出二維陣列
    void printSolution(int dist[][]) {
        System.out.println("所有頂點對之間的最短距離矩陣如下：");
        for (int i = 0; i < V; ++i) {
            for (int j = 0; j < V; ++j) {
                if (dist[i][j] == INF)
                    System.out.print("INF ");
                else
                    System.out.print(dist[i][j] + "   ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // 建立一個測試用的圖 (Adjacency Matrix)
        // 自己到自己的距離為 0，沒有連線的地方為 INF
        int graph[][] = {{0, 5, INF, 10}, {INF, 0, 3, INF}, {INF, INF, 0, 1}, {INF, INF, INF, 0}};

        FloydWarshall fw = new FloydWarshall();
        fw.floydWarshall(graph);
    }
}
