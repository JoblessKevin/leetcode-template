package problems.kahn;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class CourseScheduler {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adj.add(new ArrayList<>());
        }
        int[] inDegree = new int[numCourses];

        for (int[] pre : prerequisites) {
            int course = pre[0];
            int prerequisite = pre[1];

            adj.get(prerequisite).add(course);
            inDegree[course]++;
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        int finishedCourses = 0;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            finishedCourses++;

            for (int nextCourse : adj.get(current)) {
                inDegree[nextCourse]--;

                if (inDegree[nextCourse] == 0) {
                    queue.offer(nextCourse);
                }
            }
        }

        return finishedCourses == numCourses;
    }
}
