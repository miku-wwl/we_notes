https://leetcode.cn/problems/combinations/description/

class Solution {
    private val lists = mutableListOf<MutableList<Int>>()

    fun combine(n: Int, k: Int): List<List<Int>> {
        findCombine(0, n, k, mutableListOf<Int>())
        return lists
    }

    private fun findCombine(curr: Int, n: Int, k: Int, list: MutableList<Int>) {
        if (curr == k) {
            lists.add(list)
            return
        }
        val start = if (list.isEmpty()) 0 else list.last()
        for (i in start + 1..n) {
            val newList = list.toMutableList()
            newList.add(i)
            findCombine(curr + 1, n, k, newList)
        }
    }
}