https://leetcode.cn/problems/move-zeroes/

class Solution {
    fun moveZeroes(nums: IntArray): Unit {
        val count = nums.count {
            it == 0
        }

        var leftIndex = 0
        for (i in nums.indices) {
            if (nums[i] != 0) {
                nums[leftIndex] = nums[i]
                leftIndex++
            }
        }

        for (i in 1..count) {
            nums[nums.size - i] = 0
        }
    }
}