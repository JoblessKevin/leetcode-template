from collections import defaultdict


class Solution:
    def groupAnagrams(self, strs: list[str]) -> list[list[str]]:
        ans = defaultdict(list)
        for s in strs:
            ans[tuple(sorted(s))].append(s)
        return list(ans.values())

    def groupAnagrams_counter(self, strs: list[str]) -> list[list[str]]:
        ans = defaultdict(list)
        for s in strs:
            sorted_s = "".join(sorted(s))
            ans[sorted_s].append(s)
        return list(ans.values())
