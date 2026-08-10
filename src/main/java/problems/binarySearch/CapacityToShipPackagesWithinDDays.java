package problems.binarySearch;

public class CapacityToShipPackagesWithinDDays {
    /**
     * @formatter:off
     * Time: O(n * log(max(weights) - sum(weights))) where n is the length of weights array, max(weights) is the maximum weight in the array,
     *       and sum(weights) is the sum of all weights in the array.
     * Space: O(1)
     * @formatter:on
     */
    class BinarySearch {
        public int shipWithinDays(int[] weights, int days) {
            int l = 0;
            int r = 0;

            for (int weight : weights) {
                l = Math.max(l, weight);
                r += weight;
            }

            while (l < r) {
                int mid = l + (r - l) / 2;

                if (canShip(weights, days, mid)) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }

            return l;
        }

        private boolean canShip(int[] weights, int days, int capacity) {
            int day = 1;
            int currentWeight = 0;

            for (int weight : weights) {
                if (currentWeight + weight > capacity) {
                    day++;
                    currentWeight = 0;
                }
                currentWeight += weight;
            }

            return day <= days;
        }
    }

    /**
     * @formatter:off
     * Time: O(n * max(weights)) where n is the length of weights array and max(weights) is the maximum weight in the array.
     * Space: O(1)
     * @formatter:on
     */
    public class BruteForce {
        public int shipWithinDays(int[] weights, int days) {
            int res = 0;
            for (int weight : weights) {
                res = Math.max(res, weight);
            }
            while (true) {
                int ships = 1;
                int cap = res;
                for (int weight : weights) {
                    if (cap - weight < 0) {
                        ships++;
                        cap = res;
                    }
                    cap -= weight;
                }
                if (ships <= days) {
                    return res;
                }
                res++;
            }
        }
    }

    public static void main(String[] args) {
        CapacityToShipPackagesWithinDDays capacityToShipPackagesWithinDDays =
                                        new CapacityToShipPackagesWithinDDays();
        BinarySearch binarySearch = capacityToShipPackagesWithinDDays.new BinarySearch();
        BruteForce bruteForce = capacityToShipPackagesWithinDDays.new BruteForce();
        int[] weights = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int days = 5;
        System.out.println(binarySearch.shipWithinDays(weights, days));
        System.out.println(bruteForce.shipWithinDays(weights, days));
    }
}
