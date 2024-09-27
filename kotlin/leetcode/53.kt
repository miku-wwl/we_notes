https://leetcode.cn/problems/maximum-subarray/submissions/568442973/

class Solution {
    fun maxSubArray(nums: IntArray): Int {
        var pre = 0
        var maxAns = nums[0]
        nums.forEach {
            pre = maxOf(pre + it, it)
            maxAns = maxOf(maxAns, pre)
        }

        return maxAns
        
    }
}