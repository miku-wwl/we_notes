https://leetcode.cn/problems/merge-intervals/description/

class Solution {
    fun merge(intervals: Array<IntArray>): Array<IntArray> {
        // 排序
        intervals.sortBy{ it.first() }
        val ans = mutableListOf<IntArray>()

        for(i in intervals.indices){
            // ans 为空则直接添加
            if(ans.none()) ans += intervals[i]
            else {
                val lastTo = ans.last()[1]
                val (from, to) = intervals[i]
                if(lastTo >= from) // 相交则更新 ans 中的 lastTo
                    ans.last()[1] = max(lastTo, to)
                else { // 不相交则添在尾部
                    ans += intervals[i]
                }
            }
        }

        return ans.toTypedArray()
    }
}