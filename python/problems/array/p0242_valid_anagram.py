from collections import Counter


class Solution:
    def isAnagram(self, s: str, t: str) -> bool:
        scount = [0] * 26
        tcount = [0] * 26
        for char in s:
            scount[ord(char) - ord("a")] += 1
        for char in t:
            tcount[ord(char) - ord("a")] += 1
        return scount == tcount

    def isAnagram_counter(self, s: str, t: str) -> bool:
        return Counter(s) == Counter(t)
