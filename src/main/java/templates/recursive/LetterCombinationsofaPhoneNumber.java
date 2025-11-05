package templates.recursive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class LetterCombinationsofaPhoneNumber {
    private static final String[] MAP = {
        "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
    };

    /** Recursive */
     public List<String> letterCombinations(String digits) {
         List<String> result = new ArrayList<>();
         if (digits == null || digits.isEmpty()) return result;

         backtrack(digits, 0, new StringBuilder(), result);
         return result;
     }

    private void backtrack(String digits, int idx, StringBuilder path, List<String> result) {
        if (idx == digits.length()) {
            result.add(path.toString());
            return;
        }

        String letters = MAP[digits.charAt(idx) - '0'];

        for (int i = 0; i < letters.length(); i++) {
            path.append(letters.charAt(i));
            backtrack(digits, idx + 1, path, result);
            path.deleteCharAt(path.length() - 1);
        }
    }

    /** Using Stream API */
    public List<String> letterCombinations_streamApi(String digits) {
        if (digits == null || digits.isEmpty()) return Collections.emptyList();

        List<String> result = List.of("");

        for (char ch : digits.toCharArray()) {
            String letters = MAP[ch - '0'];

            result = result.stream()
                        .flatMap(prefix -> letters.chars()
                            .mapToObj(c -> prefix + (char)c))
                        .toList();
        }

        return result;
    }

    /** Improved Stream API */
    public List<String> letterCombinations_streamApi_improved(String digits) {
        if (digits == null || digits.length() == 0) return Collections.emptyList();

        List<StringBuilder> result = new ArrayList<>();
        result.add(new StringBuilder());

        for (char ch : digits.toCharArray()) {
            String letters = MAP[ch - '0'];
            if (letters.isEmpty()) continue;

            result = result.stream()
                           .flatMap(sb -> letters.chars()
                                                 .mapToObj(c -> new StringBuilder(sb).append((char) c)))
                           .collect(Collectors.toList());
        }

        return result.stream().map(StringBuilder::toString).collect(Collectors.toList());
    }

    /** ----------------Test Case--------------------- */
    public static void main(String[] args) {
        LetterCombinationsofaPhoneNumber soluiton = new LetterCombinationsofaPhoneNumber();

        String[] tests = {"23", "2", "79", "234", ""};

        for (String digits : tests) {
            List<String> result = soluiton.letterCombinations(digits);
            System.out.println("Input: " + digits);
            System.out.println("Output: " + result);
            System.out.println("----------------------------");
        }
    }
}

