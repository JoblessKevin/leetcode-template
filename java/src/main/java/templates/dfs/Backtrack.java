package templates.dfs;

public class Backtrack {
    public static java.util.List<java.util.List<Integer>> permute(int[] nums) {
        var res = new java.util.ArrayList<java.util.List<Integer>>();
        boolean[] used = new boolean[nums.length];
        dfs(nums, new java.util.ArrayList<>(), used, res);
        return res;
    }
    private static void dfs(int[] a, java.util.List<Integer> path, boolean[] used,
                            java.util.List<java.util.List<Integer>> res) {
        if (path.size() == a.length) { res.add(new java.util.ArrayList<>(path)); return; }
        for (int i = 0; i < a.length; i++) {
            if (used[i]) continue;
            used[i] = true; path.add(a[i]);
            dfs(a, path, used, res);
            path.remove(path.size()-1); used[i] = false;
        }
    }
}
