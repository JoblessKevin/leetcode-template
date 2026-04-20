package problems.array;

public class RemoveElement {
    public int removeElement(int[] nums, int val) {
        // k 代表「下一個非 val 元素要存放的位置」
        int k = 0;

        for (int i = 0; i < nums.length; i++) {
            // 如果目前的數字不是我們要踢掉的 val
            if (nums[i] != val) {
                // 就把這個數字搬到 k 指向的位置
                nums[k] = nums[i];
                // k 往後移一格，準備收下一個好數字
                k++;
            }
        }

        // 最後 k 的數值剛好就是不等於 val 的元素個數
        return k;
    }

    public static void main(String[] args) {
        RemoveElement solver = new RemoveElement();

        // 測試案例
        int[] nums = {3, 2, 2, 3};
        int val = 3;
        System.out.println("測試輸入: nums = " + java.util.Arrays.toString(nums) + ", val = " + val);
        System.out.println("期待結果: k = 2, nums 前兩位為 [2, 2]");
        System.out.println("-------------------------------------");

        // 執行
        int k = solver.removeElement(nums, val);
        System.out.println("實際 k: " + k);
        System.out.print("實際 nums 前 k 位: [");
        for (int i = 0; i < k; i++) {
            System.out.print(nums[i] + (i == k - 1 ? "" : ", "));
        }
        System.out.println("]");

        System.out.println("-------------------------------------");
        System.out.println("驗證完成。");
    }
}
