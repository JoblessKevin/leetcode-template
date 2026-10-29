package problems.twoPointer;

public class ReverseStringII {
    public String reverseStr(String s, int k) {
        char[] arr = s.toCharArray();
        int n = arr.length;

        // 大迴圈：每次跳 2k
        for (int i = 0; i < n; i += 2 * k) {
            // 小工具：反轉 i 到 i + k - 1 的區段
            // 注意：要小心不要超過陣列長度
            int left = i;
            int right = Math.min(i + k - 1, n - 1);

            // 雙指標反轉 (這就是你已經會的邏輯！)
            reverse(arr, left, right);
        }

        return new String(arr);
    }

    // 你的反轉小工具
    private void reverse(char[] arr, int left, int right) {
        while (left < right) {
            char temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
}
