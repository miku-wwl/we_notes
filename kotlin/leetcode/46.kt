https://leetcode.cn/problems/permutations/

class Solution {
    private val lists = mutableListOf<MutableList<Int>>()

    fun permute(nums: IntArray): List<List<Int>> {
        findPermute(0, nums, mutableListOf<Int>())
        return lists
    }

    private fun findPermute(curr: Int, nums: IntArray, list: MutableList<Int>) {
        if (curr == nums.size) lists.add(list)
        nums.filter {
            !list.contains(it)
        }.forEach {
            val newList = list.toMutableList()
            newList.add(it)
            findPermute(curr + 1, nums, newList)
        }
    }
}