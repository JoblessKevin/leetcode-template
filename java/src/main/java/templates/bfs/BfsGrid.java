package templates.bfs;

/** Shortest Path in a 4-Directional Grid (Except 0-1 Weights) */
public class BfsGrid {
    static final int[][] DIR = {{1,0},{-1,0},{0,1},{0,-1}};
    public static int shortest(int[][] grid, int sr, int sc, int tr, int tc) {
        int n = grid.length, m = grid[0].length;
        int[][] dist = new int[n][m];
        for (int[] d : dist) java.util.Arrays.fill(d, -1);
        java.util.ArrayDeque<int[]> q = new java.util.ArrayDeque<>();
        q.offer(new int[]{sr, sc}); dist[sr][sc] = 0;
        while (!q.isEmpty()) {
            var cur = q.poll();
            int r = cur[0], c = cur[1];
            if (r == tr && c == tc) return dist[r][c];
            for (int[] d : DIR) {
                int nr = r + d[0], nc = c + d[1];
                if (0 <= nr && nr < n && 0 <= nc && nc < m && grid[nr][nc] == 0 && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[r][c] + 1;
                    q.offer(new int[]{nr, nc});
                }
            }
        }
        return -1;
    }
}
