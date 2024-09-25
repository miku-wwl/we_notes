https://leetcode.cn/problems/combination-sum

class Solution {

    private val lists = mutableListOf<MutableList<Int>>()
    fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
        candidates.sort()
        findCombination(0, 0, mutableListOf<Int>(), candidates, target)
        return lists
    }

    private fun findCombination(curr: Int, pos: Int, list: MutableList<Int>, candidates: IntArray, target: Int) {
        when {
            curr > target -> return
            curr == target -> {
                lists.add(list)
                return
            }

            curr < target -> {
                for (i in pos..<candidates.size) {
                    val newList = mutableListOf<Int>().apply { addAll(list) }
                    newList.add(candidates[i])
                    findCombination(curr + candidates[i], i, newList, candidates, target)
                }
            }
        }
    }
}