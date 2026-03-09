package problems.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }

    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }

    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}


class Solution {
    public Node cloneGraph(Node node) {
        if (node == null)
            return null;

        Map<Node, Node> visited = new HashMap<>();

        return dfs(node, visited);
    }

    private Node dfs(Node node, Map<Node, Node> visited) {
        if (visited.containsKey(node)) {
            return visited.get(node);
        }

        Node cloneNode = new Node(node.val, new ArrayList<>());
        visited.put(node, cloneNode);

        for (Node neighbor : node.neighbors) {
            cloneNode.neighbors.add(dfs(neighbor, visited));
        }

        return cloneNode;
    }
}


public class CloneGraph {
    public static void main(String[] args) {
        // 1. 建立測試用的圖 (Test Case)
        // 結構:
        // 1 --- 2
        // | |
        // 4 --- 3
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        // 設定無向圖的連接關係 (彼此互為 neighbor)
        node1.neighbors.add(node2);
        node1.neighbors.add(node4);

        node2.neighbors.add(node1);
        node2.neighbors.add(node3);

        node3.neighbors.add(node2);
        node3.neighbors.add(node4);

        node4.neighbors.add(node1);
        node4.neighbors.add(node3);

        // 2. 執行我們寫好的 Clone Graph
        Solution solution = new Solution();
        Node clonedNode1 = solution.cloneGraph(node1);

        // 3. 驗證結果
        System.out.println("=== 驗證節點 1 本身 ===");
        System.out.println("原圖 Node 1 記憶體位置: " + node1);
        System.out.println("新圖 Node 1 記憶體位置: " + clonedNode1);
        System.out.println("兩者記憶體位置是否不同 (Deep Copy 成功)? : " + (node1 != clonedNode1));
        System.out.println("兩者的值是否相同? : " + (node1.val == clonedNode1.val));

        System.out.println("\n=== 驗證 Node 1 的鄰居 ===");
        System.out.println("原圖 Node 1 的鄰居有:");
        for (Node n : node1.neighbors) {
            System.out.println(" -> 節點值: " + n.val + ", 記憶體位置: " + n);
        }

        System.out.println("\n新圖 Node 1 的鄰居有:");
        for (Node n : clonedNode1.neighbors) {
            System.out.println(" -> 節點值: " + n.val + ", 記憶體位置: " + n);
        }
    }
}
