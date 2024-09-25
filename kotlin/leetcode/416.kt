https://leetcode.cn/problems/partition-equal-subset-sum/

class Solution {
    fun canPartition(nums: IntArray): Boolean {
        val sum = nums.sum()
        if (sum % 2 == 1) return false
        val set = mutableSetOf<Int>()
        set.add(0)
        nums.forEach {
            set.toMutableSet().forEach { iit ->
                set.add(it + iit)
            }
        }

        return set.contains(sum / 2)
    }
}