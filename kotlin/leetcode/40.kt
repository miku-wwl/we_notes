https://leetcode.cn/problems/combination-sum-ii/description/

class Solution {
    val ans = ArrayList<ArrayList<Int>>()
    fun combinationSum2(candidates: IntArray, target: Int): List<List<Int>> {
        candidates.sort()
        solve(candidates, target, 0, 0, ArrayList())
        return ans
    }

    fun solve(array: IntArray, target: Int, sum: Int, index: Int, list: ArrayList<Int>) {
        for (i in index..array.lastIndex) {
            if (i != index && array[i] == array[i - 1]) {
                continue
            }
            val a = array[i]
            if (a + sum == target) {
                val l = ArrayList<Int>()
                l.addAll(list)
                l.add(a)
                ans.add(l)
                return
            } else if (a + sum > target) {
                return
            } else {
                list.add(a)
                solve(array, target, sum + a, i + 1, list)
                list.remove(a)
            }
        }
    }
}