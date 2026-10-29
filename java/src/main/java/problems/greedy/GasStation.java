package problems.greedy;

public class GasStation {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int totalTank = 0;
        int currentTank = 0;
        int startStation = 0;

        for (int i = 0; i < gas.length; i++) {
            totalTank += gas[i] - cost[i];
            currentTank += gas[i] - cost[i];

            // 如果 currentTank < 0，表示從 startStation 出發到 i 站會沒油
            // 所以下一站 (i + 1) 可能是新的起點
            if (currentTank < 0) {
                startStation = i + 1;
                currentTank = 0;
            }
        }

        // 如果 totalTank < 0，表示總油量不足，不可能完成
        return totalTank >= 0 ? startStation : -1;
    }

    public static void main(String[] args) {
        GasStation solver = new GasStation();

        int[] gas1 = {1, 2, 3, 4, 5};
        int[] cost1 = {3, 4, 5, 1, 2};
        System.out.println("Test Case 1: gas=" + java.util.Arrays.toString(gas1) + ", cost="
                                        + java.util.Arrays.toString(cost1));
        System.out.println("Result: " + solver.canCompleteCircuit(gas1, cost1)); // 應為 3

        int[] gas2 = {2, 3, 4};
        int[] cost2 = {3, 4, 3};
        System.out.println("Test Case 2: gas=" + java.util.Arrays.toString(gas2) + ", cost="
                                        + java.util.Arrays.toString(cost2));
        System.out.println("Result: " + solver.canCompleteCircuit(gas2, cost2)); // 應為 -1
    }
}
