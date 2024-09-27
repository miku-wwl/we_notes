https://leetcode.cn/problems/subsets-ii/

class Solution {
    private val lists = mutableListOf<MutableList<Int>>()
    private val set = mutableSetOf<MutableList<Int>>()

    fun subsetsWithDup(nums: IntArray): List<List<Int>> {
        nums.sort()
        for (i in 0..nums.size) {
            findSubsetsWithDup(0, nums.size, i, mutableListOf<Int>(), nums)
        }
        return lists
    }

    private fun findSubsetsWithDup(curr: Int, n: Int, k: Int, list: MutableList<Int>, nums: IntArray) {
        if (curr == k) {
            val toList = list.map { nums[it - 1] }.toMutableList()
            if (!set.contains(toList)) {
                lists.add(toList)
                set.add(toList)
            }
            return
        }
        val start = if (list.isEmpty()) 0 else list.last()
        for (i in start + 1..n) {
            val newList = list.toMutableList()
            newList.add(i)
            findSubsetsWithDup(curr + 1, n, k, newList, nums)
        }
    }
}