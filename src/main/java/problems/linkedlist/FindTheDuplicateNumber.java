package problems.linkedlist;

public class FindTheDuplicateNumber {
    public class LinkedListCycle {
        public int findDuplicate(int[] nums) {
            int slow = 0, fast = 0;
            while (true) {
                slow = nums[slow];
                fast = nums[nums[fast]];
                if (slow == fast) {
                    break;
                }
            }

            int slow2 = 0;
            while (true) {
                slow = nums[slow];
                slow2 = nums[slow2];
                if (slow == slow2) {
                    return slow;
                }
            }
        }
    }

    public class BinarySearch {
        public int findDuplicate(int[] nums) {
            int n = nums.length;
            int low = 1;
            int high = n - 1;
            while (low < high) {
                int mid = low + (high - low) / 2;
                int lessOrEqual = 0;
                for (int i = 0; i < n; i++) {
                    if (nums[i] <= mid) {
                        lessOrEqual++;
                    }
                }

                if (lessOrEqual <= mid) {
                    low = mid + 1;
                } else {
                    high = mid;
                }
            }

            return low;
        }
    }

    public class BitManipulation {
        public int findDuplicate(int[] nums) {
            int n = nums.length;
            int res = 0;
            for (int b = 0; b < 32; b++) {
                int x = 0, y = 0;
                int mask = 1 << b;
                for (int num : nums) {
                    if ((num & mask) != 0) {
                        x++;
                    }
                }
                for (int num = 1; num < n; num++) {
                    if ((num & mask) != 0) {
                        y++;
                    }
                }
                if (x > y) {
                    res |= mask;
                }
            }
            return res;
        }
    }

    public class NegativeMarking {
        public int findDuplicate(int[] nums) {
            for (int num : nums) {
                int idx = Math.abs(num) - 1;
                if (nums[idx] < 0) {
                    return Math.abs(num);
                }
                nums[idx] *= -1;
            }
            return -1;
        }
    }

    public static void main(String[] args) {
        FindTheDuplicateNumber findTheDuplicateNumber = new FindTheDuplicateNumber();
        int[] nums = {1, 3, 4, 2, 2};
        System.out.println(findTheDuplicateNumber.new LinkedListCycle().findDuplicate(nums));
        System.out.println(findTheDuplicateNumber.new BinarySearch().findDuplicate(nums));
        System.out.println(findTheDuplicateNumber.new BitManipulation().findDuplicate(nums));
        System.out.println(findTheDuplicateNumber.new NegativeMarking().findDuplicate(nums));
    }
}
