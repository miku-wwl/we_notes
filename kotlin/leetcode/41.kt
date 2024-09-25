https://leetcode.cn/problems/first-missing-positive/description/

class Solution {
    fun firstMissingPositive(nums: IntArray): Int {
        val set = mutableSetOf<Int>()
        nums.forEach {
            set.add(it)
        }
        for (i in 1..nums.size) {
            if (!set.contains(i)) {
                return i
            }
        }

        return nums.size + 1
    }

}