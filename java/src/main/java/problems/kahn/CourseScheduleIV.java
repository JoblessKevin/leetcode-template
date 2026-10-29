package problems.kahn;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class CourseScheduleIV {
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites,
                                    int[][] queries) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }
        int[] inDegree = new int[numCourses];

        for (int[] pre : prerequisites) {
            adj.get(pre[0]).add(pre[1]);
            inDegree[pre[1]]++;
        }

        List<Set<Integer>> ancestors = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            ancestors.add(new HashSet<>());
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int nextCourse : adj.get(current)) {
                ancestors.get(nextCourse).add(current);
                ancestors.get(nextCourse).addAll(ancestors.get(current));

                inDegree[nextCourse]--;
                if (inDegree[nextCourse] == 0) {
                    queue.offer(nextCourse);
                }
            }
        }

        List<Boolean> result = new ArrayList<>();
        for (int[] query : queries) {
            int u = query[0];
            int v = query[1];

            result.add(ancestors.get(v).contains(u));
        }

        return result;
    }
}


/**
 * Floyd-Warshall
 */
class Solution {
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites,
                                    int[][] queries) {
        // 建立一個二維陣列，isReachable[i][j] 代表 i 是否能走到 j (i 是否為 j 的先修課)
        boolean[][] isReachable = new boolean[numCourses][numCourses];

        // 初始化：把直接相連的先修課標記為 true
        for (int[] pre : prerequisites) {
            isReachable[pre[0]][pre[1]] = true;
        }

        // 👇 Floyd-Warshall 的靈魂 5 行程式碼
        for (int k = 0; k < numCourses; k++) { // k 是中繼站
            for (int i = 0; i < numCourses; i++) { // i 是起點
                for (int j = 0; j < numCourses; j++) { // j 是終點
                    // 如果 i 能到 k，且 k 能到 j，那 i 就能到 j
                    if (isReachable[i][k] && isReachable[k][j]) {
                        isReachable[i][j] = true;
                    }
                }
            }
        }

        // 處理查詢，直接查表 O(1)
        List<Boolean> result = new ArrayList<>();
        for (int[] query : queries) {
            result.add(isReachable[query[0]][query[1]]);
        }

        return result;
    }
}
