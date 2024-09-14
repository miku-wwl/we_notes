https://leetcode.cn/problems/find-minimum-in-rotated-sorted-array/description/

class Solution {
    fun findMin(nums: IntArray): Int {
        var low = 0
        var high = nums.lastIndex
        
        while (low < high) {
            val pivot = low + (high - low) / 2
            if (nums[pivot] < nums[high]) {
                high = pivot
            } else {
                low = pivot + 1
            }
        }
        
        return nums[low]
    }
}