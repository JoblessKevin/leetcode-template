package designs.lld;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.Arrays;

class RideSharingSystem {
    private Queue<Integer> driverQueue;
    private Queue<Integer> riderQueue;
    private Set<Integer> activeRiders;

    public RideSharingSystem() {
        driverQueue = new ArrayDeque<>();
        riderQueue = new ArrayDeque<>();
        activeRiders = new HashSet<>();
    }

    public void addRider(int riderId) {
        riderQueue.offer(riderId);
        activeRiders.add(riderId);
    }

    public void addDriver(int driverId) {
        driverQueue.offer(driverId);
    }

    public int[] matchDriverWithRider() {
        while (!riderQueue.isEmpty() && !activeRiders.contains(riderQueue.peek())) {
            riderQueue.poll();
        }

        if (driverQueue.isEmpty() || riderQueue.isEmpty()) {
            return new int[] {-1, -1};
        }

        int driverId = driverQueue.poll();
        int riderId = riderQueue.poll();

        activeRiders.remove(riderId);

        return new int[] {driverId, riderId};
    }

    public void cancelRider(int riderId) {
        if (activeRiders.contains(riderId)) {
            activeRiders.remove(riderId);
        }
    }
}


/**
 * Your RideSharingSystem object will be instantiated and called as such: RideSharingSystem obj =
 * new RideSharingSystem(); obj.addRider(riderId); obj.addDriver(driverId); int[] param_3 =
 * obj.matchDriverWithRider(); obj.cancelRider(riderId);
 */


public class DesignRideSharingSystem {

    public static void main(String[] args) {
        System.out.println("=== 開始測試 RideSharingSystem ===");

        RideSharingSystem system = new RideSharingSystem();

        // Test Case 1: 正常配對 (Basic Flow)
        // Rider 1 進來, Driver 1 進來 -> 應該配對 [1, 1]
        system.addRider(1);
        system.addDriver(1);
        int[] result1 = system.matchDriverWithRider();
        printTestResult("Test 1 (Basic Match)", result1, new int[] {1, 1});

        // Test Case 2: 取消機制 (Lazy Removal)
        // Rider 2, Rider 3 進來
        system.addRider(2);
        system.addRider(3);
        // Rider 2 取消了
        system.cancelRider(2);
        // Driver 2 進來
        system.addDriver(2);

        // 雖然 Rider 2 排在前面，但他取消了，所以 Driver 2 應該配對到 Rider 3
        int[] result2 = system.matchDriverWithRider();
        printTestResult("Test 2 (Cancel Logic)", result2, new int[] {2, 3});

        // Test Case 3: 佇列為空 (Empty Queue)
        // 目前沒有任何人排隊了
        int[] result3 = system.matchDriverWithRider();
        printTestResult("Test 3 (Empty Queue)", result3, new int[] {-1, -1});

        // Test Case 4: 取消不存在的乘客 (Edge Case)
        system.cancelRider(999); // 應該不會報錯
        System.out.println("[PASS] Test 4 (Cancel Non-existent): No Exception Thrown");

        // Test Case 5: 複雜順序 (Complex Sequence)
        // Driver 10 先到
        system.addDriver(10);
        // Rider 20 到
        system.addRider(20);
        // Rider 21 到
        system.addRider(21);
        // Rider 20 取消
        system.cancelRider(20);
        // Driver 11 到
        system.addDriver(11);

        // 第一次配對: Driver 10 還在等，Rider 20 取消了，所以 Driver 10 配 Rider 21
        int[] result5a = system.matchDriverWithRider();
        printTestResult("Test 5a (Complex Match 1)", result5a, new int[] {10, 21});

        // 第二次配對: 剩下 Driver 11，但沒有 Rider 了
        int[] result5b = system.matchDriverWithRider();
        printTestResult("Test 5b (Complex Match 2)", result5b, new int[] {-1, -1});

        System.out.println("=== 測試結束 ===");
    }

    // 輔助函式：用來比較陣列結果並印出 Pass/Fail
    private static void printTestResult(String testName, int[] actual, int[] expected) {
        if (Arrays.equals(actual, expected)) {
            System.out.println("[PASS] " + testName + " -> Output: " + Arrays.toString(actual));
        } else {
            System.out.println("[FAIL] " + testName);
            System.out.println("       Expected: " + Arrays.toString(expected));
            System.out.println("       Got:      " + Arrays.toString(actual));
        }
    }
}
