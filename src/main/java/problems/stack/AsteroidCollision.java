package problems.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class AsteroidCollision {
    public int[] asteroidCollision(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i : asteroids) {
            while (!stack.isEmpty() && i < 0 && stack.peek() > 0) {
                int temp = stack.peek() + i;
                if (temp < 0) {
                    stack.pop();
                } else if (temp > 0) {
                    i = 0;
                } else {
                    i = 0;
                    stack.pop();
                }
            }
            if (i != 0) {
                stack.push(i);
            }
        }

        int[] res = new int[stack.size()];
        for (int i = res.length - 1; i >= 0; i--) {
            res[i] = stack.pop();
        }

        return res;
    }

    public int[] asteroidCollision_inPlace(int[] asteroids) {
        int j = -1;

        for (int a : asteroids) {
            while (j >= 0 && asteroids[j] > 0 && a < 0) {
                if (asteroids[j] > Math.abs(a)) {
                    a = 0;
                    break;
                } else if (asteroids[j] == Math.abs(a)) {
                    j--;
                    a = 0;
                    break;
                } else {
                    j--;
                }
            }
            if (a != 0) {
                asteroids[++j] = a;
            }
        }

        return Arrays.copyOfRange(asteroids, 0, j + 1);
    }
}
