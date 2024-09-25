https://leetcode.cn/problems/target-sum/description/

class Solution {
    private var sum = 0
    fun findTargetSumWays(nums: IntArray, target: Int): Int {
        sum = 0
        findSum(0, 0, nums, target)
        return sum
    }

    private fun findSum(curr: Int, summer: Int, nums: IntArray, target: Int) {
        if (curr == nums.size) {
            if (summer == target) {
                sum++
            }
            return
        }
        findSum(curr + 1, summer + nums[curr], nums, target)
        findSum(curr + 1, summer - nums[curr], nums, target)
    }
}