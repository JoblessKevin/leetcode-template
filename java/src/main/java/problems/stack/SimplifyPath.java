package problems.stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class SimplifyPath {
    public String simplifyPath(String path) {
        Deque<String> stack = new ArrayDeque<>();
        String[] strs = path.split("/");
        for (String str : strs) {
            if (str.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else if (!str.equals(".") && !str.equals("")) {
                stack.push(str);
            }
        }
        if (stack.isEmpty()) {
            return "/";
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append("/").append(stack.pollLast());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SimplifyPath sp = new SimplifyPath();
        String path = "/a/./b/../../c/";
        String simplifiedPath = sp.simplifyPath(path);
        System.out.println("原始路徑: " + path);
        System.out.println("簡化後的路徑: " + simplifiedPath);
    }
}
