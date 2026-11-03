class Solution:
    def containsDuplicate(self, nums: list[int]) -> bool:
        seen = set()
        for num in nums:
            if num in seen:
                return True
            seen.add(num)
        return False

    def containsDuplicate_len(self, nums: list[int]) -> bool:
        return len(set(nums)) != len(nums)
