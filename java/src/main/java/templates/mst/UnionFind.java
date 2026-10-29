package templates.mst;

/**
 * 如果實戰中使用 rank 的寫法，就只能知道樹的高度，無法知道集合的大小
 * 
 * 實戰中建議使用 size 的寫法，也就是 DSU.java
 */
class UnionFind {
    private int[] parent; // 紀錄每個節點的父節點
    private int[] rank; // 紀錄每棵樹的深度 (秩)

    // 初始化：一開始每個元素都是自己獨立的一個集合
    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i; // 自己的父節點是自己
            rank[i] = 1; // 初始深度為 1
        }
    }

    // Find 操作 (包含路徑壓縮)
    public int find(int i) {
        if (parent[i] == i) {
            return i;
        }
        // 遞迴尋找根節點，並將當前節點直接連到根節點 (路徑壓縮)
        return parent[i] = find(parent[i]);
    }

    // Union 操作 (包含按秩合併)
    public void union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);

        if (rootI != rootJ) {
            // 將深度小的樹接到深度大的樹底下
            if (rank[rootI] < rank[rootJ]) {
                parent[rootI] = rootJ;
            } else if (rank[rootI] > rank[rootJ]) {
                parent[rootJ] = rootI;
            } else {
                // 如果深度一樣，隨便接，但要把新的根節點深度 + 1
                parent[rootJ] = rootI;
                rank[rootI]++;
            }
        }
    }

    // 檢查兩個元素是否在同一個集合
    public boolean connected(int i, int j) {
        return find(i) == find(j);
    }
}
