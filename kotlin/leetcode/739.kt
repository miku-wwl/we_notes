https://leetcode.cn/problems/daily-temperatures/description/

class Solution {
    fun dailyTemperatures(temperatures: IntArray): IntArray {
        val list = mutableListOf<Int>()
        val ans = IntArray(temperatures.size)
        for (i in temperatures.size - 1 downTo 0) {
            while (list.isNotEmpty() && temperatures[list.last()] <= temperatures[i]) {
                list.removeLast()
            }
            if (list.isEmpty()) {
                ans[i] = 0
            } else {
                ans[i] = list.last() - i
            }
            list.add(i)
        }
        return ans
    }
}