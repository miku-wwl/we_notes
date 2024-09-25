https://leetcode.cn/problems/find-all-numbers-disappeared-in-an-array/description/

class Solution {
    fun findDisappearedNumbers(nums: IntArray): List<Int> {
        val set = mutableSetOf<Int>()
        val list = mutableListOf<Int>()
        nums.forEach {
            set.add(it)
        }
        for (i in 1..nums.size) {
            if (!set.contains(i)) {
                list.add(i)
            }
        }
        return list
    }
}