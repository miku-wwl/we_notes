https://leetcode.cn/problems/top-k-frequent-elements/description/

class Solution {
    fun topKFrequent(nums: IntArray, k: Int): IntArray {
        val map = mutableMapOf<Int, Int>()
        nums.forEach {
            map[it] = map.getOrPut(it) { 0 } + 1
        }

        val list = map.toList().toMutableList()

        list.sortByDescending {
            it.second
        }

        return list.filterIndexed { index, _ ->
            index < k
        }.map {
            it.first
        }.toIntArray()
    }
}