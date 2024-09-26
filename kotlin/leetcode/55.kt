https://leetcode.cn/problems/jump-game/

class Solution {
    private var far = 0
    
    fun canJump(nums: IntArray): Boolean {
        findJump(0, nums)
        return far >= nums.size - 1
    }

    private fun findJump(curr: Int, nums: IntArray) {
        if (curr > far || curr > nums.size - 1) return
        far = maxOf(far, curr + nums[curr])
        findJump(curr + 1, nums)
    }
}