package main.java.templates.cartesianProduct;

import java.util.*;

public class CartesianDFS {

    public static <T> List<List<T>> cartesianProductIterative(List<List<T>> lists) {
        List<List<T>> result = new ArrayList<>();
        if (lists == null || lists.isEmpty()) {
            result.add(new ArrayList<>());
            return result;
        }

        Deque<Integer> indexStack = new ArrayDeque<>();
        List<T> path = new ArrayList<>();

        indexStack.push(0);

        while (!indexStack.isEmpty()) {
            int depth = indexStack.size() - 1;
            List<T> currentList = lists.get(depth);
            int idx = indexStack.pop();

            if (idx < currentList.size()) {
                T choice = currentList.get(idx);

                indexStack.push(idx + 1);

                path.add(choice);

                if (depth + 1 == lists.size()) {
                    result.add(new ArrayList<>(path));
                    path.remove(path.size() - 1);
                } else {
                    indexStack.push(0);
                }
            } else {
                if (!path.isEmpty()) {
                    path.remove(path.size() - 1);
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        List<List<String>> lists = List.of(
                List.of("A", "B"),
                List.of("1", "2"),
                List.of("X", "Y")
        );

        List<List<String>> result = cartesianProductIterative(lists);
        result.forEach(System.out::println);
    }

    // public static <T> List<List<T>> cartesianProductIterative(List<List<T>> lists) {
    //     List<List<T>> result = new ArrayList<>();
    //     if (lists == null || lists.isEmpty()) {
    //         result.add(new ArrayList<>());
    //         return result;
    //     }

    //     Deque<Iterator<T>> stack = new ArrayDeque<>();
    //     List<T> path = new ArrayList<>();

    //     stack.push(lists.get(0).iterator());

    //     while (!stack.isEmpty()) {
    //         Iterator<T> it = stack.peek();

    //         if (it.hasNext()) {
    //             T choice = it.next();
    //             path.add(choice);

    //             if (stack.size() == lists.size()) {
    //                 result.add(new ArrayList<>(path));
    //                 path.remove(path.size() - 1);
    //             } else {
    //                 stack.push(lists.get(stack.size()).iterator());
    //             }
    //         } else {
    //             stack.pop();
    //             if (!path.isEmpty()) {
    //                 path.remove(path.size() - 1);
    //             }
    //         }
    //     }
    //     return result;
    // }

    // public static void main(String[] args) {
    //     // === 測資 1: 基本字串組合 ===
    //     List<List<String>> stringLists = List.of(
    //             List.of("A", "B"),
    //             List.of("1", "2"),
    //             List.of("X", "Y")
    //     );
    //     List<List<String>> result1 = cartesianProductIterative(stringLists);
    //     System.out.println("Test 1 - 字串組合 (A/B, 1/2, X/Y):");
    //     result1.forEach(System.out::println);

    //     // === 測資 2: 整數組合 ===
    //     List<List<Integer>> intLists = List.of(
    //             List.of(1, 2),
    //             List.of(10, 20, 30)
    //     );
    //     List<List<Integer>> result2 = cartesianProductIterative(intLists);
    //     System.out.println("\nTest 2 - 整數組合 (1,2 × 10,20,30):");
    //     result2.forEach(System.out::println);

    //     // === 測資 3: 單一層 ===
    //     List<List<String>> singleList = List.of(
    //             List.of("Alpha", "Beta", "Gamma")
    //     );
    //     List<List<String>> result3 = cartesianProductIterative(singleList);
    //     System.out.println("\nTest 3 - 單層組合:");
    //     result3.forEach(System.out::println);

    //     // === 測資 4: 空輸入 ===
    //     List<List<String>> emptyList = new ArrayList<>();
    //     List<List<String>> result4 = cartesianProductIterative(emptyList);
    //     System.out.println("\nTest 4 - 空集合:");
    //     result4.forEach(System.out::println);

    //     // === 測資 5: 含空子集合 ===
    //     List<List<String>> containEmpty = List.of(
    //             List.of("A", "B"),
    //             Collections.emptyList(),
    //             List.of("X")
    //     );
    //     List<List<String>> result5 = cartesianProductIterative(containEmpty);
    //     System.out.println("\nTest 5 - 含空子集合:");
    //     result5.forEach(System.out::println);
    // }
}