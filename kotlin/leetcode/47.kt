https://leetcode.cn/problems/permutations-ii/

class Solution {
    private val lists = mutableListOf<MutableList<Int>>()
    private val set = mutableSetOf<MutableList<Int>>()

    fun permuteUnique(nums: IntArray): List<List<Int>> {
        findPermuteUnique(0, nums, mutableListOf<Int>())
        return lists
    }

    private fun findPermuteUnique(curr: Int, nums: IntArray, list: MutableList<Int>) {
        if (curr == nums.size) {
            val listMap = list.map {
                nums[it]
            }.toMutableList()

            if (set.contains(listMap)) {
                return
            } else {
                set.add(listMap)
                lists.add(listMap)
                return
            }
        }

        for (i in nums.indices) {
            if (!list.contains(i)) {
                val newList = list.toMutableList()
                newList.add(i)
                findPermuteUnique(curr + 1, nums, newList)
            }
        }

    }
}