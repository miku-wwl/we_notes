https://leetcode.cn/problems/house-robber/

class Solution {
    fun rob(nums: IntArray): Int {
        var robLast = 0
        var notRobLast = 0
        var robThis = 0
        var notRobThis = 0

        nums.forEach {
            robThis = notRobLast + it
            notRobThis = maxOf(notRobLast, robLast)
            
            robLast = robThis
            notRobLast = notRobThis
        }

        return maxOf(robLast, notRobLast)
    }
}