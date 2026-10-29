package problems.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class CarFleet {
    public static int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;

        int[][] cars = new int[n][2];
        for (int i = 0; i < n; i++) {
            cars[i][0] = position[i];
            cars[i][1] = speed[i];
        }

        Arrays.sort(cars, (a, b) -> Integer.compare(b[0], a[0]));

        Deque<Double> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            double time = (double) (target - cars[i][0]) / cars[i][1];

            if (stack.isEmpty() || time > stack.peek()) {
                stack.push(time);
            }
        }

        return stack.size();
    }

    /**
     * 不使用 Stack 去達到空間優化
     */
    public int carFleet_optimal(int target, int[] position, int[] speed) {
        int n = position.length;
        int[][] pair = new int[n][2];
        for (int i = 0; i < n; i++) {
            pair[i][0] = position[i];
            pair[i][1] = speed[i];
        }
        Arrays.sort(pair, (a, b) -> Integer.compare(b[0], a[0]));

        int fleets = 1;
        double prevTime = (double) (target - pair[0][0]) / pair[0][1];
        for (int i = 1; i < n; i++) {
            double currTime = (double) (target - pair[i][0]) / pair[i][1];
            if (currTime > prevTime) {
                fleets++;
                prevTime = currTime;
            }
        }
        return fleets;
    }

    public static void main(String[] args) {
        int target = 12;
        int[] position = {10, 8, 0, 5, 3};
        int[] speed = {2, 4, 1, 1, 3};

        System.out.println("🏁 終點位置: " + target + "\n");
        System.out.println("🚗 車隊數量: " + carFleet(target, position, speed));
    }
}
