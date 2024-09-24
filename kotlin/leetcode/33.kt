https://leetcode.cn/problems/search-in-rotated-sorted-array/description/

class Solution {
    fun search(nums: IntArray, target: Int): Int {
        var left = 0
        var right = nums.size - 1
        while (left <= right) {
            var mid = left + (right - left) / 2
            if (nums[mid] == target) {
                return mid;
            }
            when {
                nums[0] <= nums[mid] -> {
                    if (nums.first() <= target && target < nums[mid]) {
                        right = mid - 1
                    } else {
                        left = mid + 1
                    }
                }

                nums[0] > nums[mid] -> {
                    if (nums[mid] < target && target <= nums.last()) {
                        left = mid + 1
                    } else {
                        right = mid - 1
                    }
                }
            }
        }
        return -1
    }
}