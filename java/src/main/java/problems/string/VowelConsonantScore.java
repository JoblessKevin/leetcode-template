package problems.string;

import java.util.HashSet;
import java.util.Set;

public class VowelConsonantScore {
    /**
     * Lowercase String Constraints, Time Complexity: O(n) Space Complexity: O(1)
     */
    public int vowelConsonantScore_lowercase(String s) {
        String vowels = "aeiou";

        int v = 0;
        int c = 0;

        for (char ch : s.toCharArray()) {
            if (Character.isLetter(ch)) {
                if (vowels.indexOf(ch) != -1) {
                    v++;
                } else {
                    c++;
                }
            }
        }

        return (c == 0) ? 0 : (v / c);
    }

    /**
     * Boolean Array, Time Complexity: O(n) Space Complexity: O(1) (Fixed size 128)
     */
    public int vowelConsonantScore_boolean(String s) {
        boolean[] isVowel = new boolean[128];
        isVowel['a'] = true;
        isVowel['e'] = true;
        isVowel['i'] = true;
        isVowel['o'] = true;
        isVowel['u'] = true;

        int v = 0;
        int c = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (Character.isLetter(ch)) {
                char lowerCh = Character.toLowerCase(ch);

                // Prevent overflow (e.g. 'A' -> 65)
                if (lowerCh < 128 && isVowel[lowerCh]) {
                    v++;
                } else {
                    c++;
                }
            }
        }
        return c == 0 ? 0 : v / c;
    }

    /**
     * Static Array (Lookup Table), Time Complexity: O(n) Space Complexity: O(1)
     */
    private static final boolean[] IS_VOWEL = new boolean[128];

    static {
        IS_VOWEL['a'] = true;
        IS_VOWEL['e'] = true;
        IS_VOWEL['i'] = true;
        IS_VOWEL['o'] = true;
        IS_VOWEL['u'] = true;
    }

    public int vowelConsonantScore(String s) {
        int v = 0;
        int c = 0;
        int len = s.length();

        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);

            if (Character.isLetter(ch)) {
                char lowerCh = Character.toLowerCase(ch);

                if (lowerCh < 128 && IS_VOWEL[lowerCh]) {
                    v++;
                } else {
                    c++;
                }
            }
        }
        return (c == 0) ? 0 : (v / c);
    }

    /**
     * Method 3: HashSet Pros: Readable, flexible for dynamic vowels. Cons: Slower (Autoboxing,
     * Hashing), Higher Memory. Time Complexity: O(n) Space Complexity: O(1) (Fixed constant size)
     */
    public int vowelConsonantScore_set(String s) {
        Set<Character> set = new HashSet<>();
        set.add('a');
        set.add('e');
        set.add('i');
        set.add('o');
        set.add('u');

        int v = 0;
        int c = 0;

        for (char ch : s.toCharArray()) {
            if (Character.isLetter(ch)) {
                if (set.contains(Character.toLowerCase(ch))) {
                    v++;
                } else {
                    c++;
                }
            }
        }
        return (c == 0) ? 0 : (v / c);
    }

    // ==========================================
    // Main Method for Testing
    // ==========================================
    public static void main(String[] args) {
        VowelConsonantScore solver = new VowelConsonantScore();

        String[] testCases = {"hello", // v=2, c=3 -> 0
                                        "hello world", // v=3, c=7 -> 0
                                        "aeiou", // v=5, c=0 -> 0
                                        "xyz", // v=0, c=3 -> 0
                                        "AbcDe", // v=2 ('A','e'), c=3 ('b','c','D') -> 0 (int
                                                 // division 2/3)
                                        "AaaBbb" // v=3, c=3 -> 1
        };

        System.out.println("--- Testing vowelConsonantScore (Static Array) ---");
        for (String s : testCases) {
            System.out.printf("Input: %-12s | Score: %d%n", s, solver.vowelConsonantScore(s));
        }

        System.out.println("\n--- Testing vowelConsonantScore_boolean (Local Array) ---");
        System.out.println("Input: AaaBbb       | Score: "
                                        + solver.vowelConsonantScore_boolean("AaaBbb"));

        System.out.println("\n--- Testing vowelConsonantScore_set (HashSet) ---");
        System.out.println("Input: AaaBbb       | Score: "
                                        + solver.vowelConsonantScore_set("AaaBbb"));
    }
}
