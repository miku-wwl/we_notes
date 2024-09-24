https://leetcode.cn/problems/remove-duplicates-from-sorted-array/description/

class Solution {
    fun removeDuplicates(nums: IntArray): Int {
        var index = 0
        for (i in 1..<nums.size) {
            if (nums[i] != nums[i - 1]) {
                index++
                nums[index] = nums[i]
            }
        }
        return index + 1
    }
}