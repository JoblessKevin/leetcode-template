class Solution:
    def longestCommonPrefix_horizonal(self, strs: list[str]) -> str:
        if not strs:
            return ""
        prefix = strs[0]
        for s in strs[1:]:
            while not s.startswith(prefix):
                prefix = prefix[:-1]
                if not prefix:
                    return ""
        return prefix

    def longestCommonPrefix_vertical(self, strs: list[str]) -> str:
        if not strs:
            return ""
        res = []
        for chars in zip(*strs, strict=False):
            if len(set(chars)) == 1:
                res.append(chars[0])
            else:
                break
        return "".join(res)
