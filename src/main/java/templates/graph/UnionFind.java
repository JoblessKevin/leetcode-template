package templates.graph;

class UnionFind {
    int[] parent; // 記錄每個點的老大是誰
    int count; // 記錄還有幾個獨立的幫派 (島嶼)

    public UnionFind(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        parent = new int[m * n];
        count = 0;

        // 初始化
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == '1') {
                    int id = r * n + c; // 二維轉一維 ID
                    parent[id] = id; // 剛開始自己是自己的老大
                    count++;
                }
            }
        }
    }

    // 核心 1: 找老大 (包含路徑壓縮)
    public int find(int i) {
        if (parent[i] != i) {
            // 這行是精髓：把沿路所有小弟直接掛在最高領袖底下
            parent[i] = find(parent[i]);
        }
        return parent[i];
    }

    // 核心 2: 合併兩個幫派
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            parent[rootX] = rootY; // X 的老大拜 Y 為師
            count--; // 兩個幫派併成一個，總數 -1
        }
    }

    public int getCount() {
        return count;
    }
}
