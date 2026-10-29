"""Binary search templates."""


def lower_bound(nums: list[int], target: int) -> int:
    """Return the first index >= target; or len(nums) if none."""
    l, r = 0, len(nums)
    while l < r:
        m = (l + r) // 2
        if nums[m] < target:
            l = m + 1
        else:
            r = m
    return l


def binary_search(nums: list[int], target: int) -> int:
    """Classic binary search; return index or -1."""
    l, r = 0, len(nums) - 1
    while l <= r:
        m = (l + r) // 2
        if nums[m] == target:
            return m
        if nums[m] < target:
            l = m + 1
        else:
            r = m - 1
    return -1


def test_lower_bound():
    assert lower_bound([1, 2, 2, 4], 2) == 1
    assert lower_bound([1, 2, 2, 4], 3) == 3
    assert lower_bound([1, 2, 2, 4], 5) == 4


def test_binary_search():
    assert binary_search([1, 3, 5, 7], 5) == 2
    assert binary_search([1, 3, 5, 7], 4) == -1
