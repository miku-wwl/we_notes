https://leetcode.cn/problems/subsets/description/

class Solution {
    private val lists = mutableListOf<MutableList<Int>>()

    fun subsets(nums: IntArray): List<List<Int>> {
        for (i in 0..nums.size) {
            findSubsets(0, nums.size, i, mutableListOf<Int>(), nums)
        }
        return lists
    }

    private fun findSubsets(curr: Int, n: Int, k: Int, list: MutableList<Int>, nums: IntArray) {
        if (curr == k) {
            lists.add(list.map { nums[it - 1] }.toMutableList())
            return
        }
        val start = if (list.isEmpty()) 0 else list.last()
        for (i in start + 1..n) {
            val newList = list.toMutableList()
            newList.add(i)
            findSubsets(curr + 1, n, k, newList, nums)
        }
    }
}