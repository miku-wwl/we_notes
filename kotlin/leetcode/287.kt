https://leetcode.cn/problems/find-the-duplicate-number/description/

class Solution {
    fun findDuplicate(nums: IntArray): Int {
        val set = mutableSetOf<Int>()

        nums.forEach {
            if (set.contains(it)) {
                return it
            } else {
                set.add(it)
            }
        }
        return -1
    }
}