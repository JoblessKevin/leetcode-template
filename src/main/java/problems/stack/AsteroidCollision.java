package problems.stack;

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
}
