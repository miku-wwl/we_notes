https://leetcode.cn/problems/partition-labels/description/

class Solution {
    fun partitionLabels(s: String): List<Int> {
        val str = s.toCharArray()
        val n = str.size
        val lastIndices = IntArray(26)

        // 计算每个字符最后一次出现的位置
        for (i in str.indices) {
            lastIndices[str[i] - 'a'] = i
        }

        val res = mutableListOf<Int>()
        var start = 0
        var end = 0
        for (i in str.indices) {
            end = maxOf(end, lastIndices[str[i] - 'a'])
            if (i == end) {
                res.add(end - start + 1)
                start = end + 1
            }
        }

        return res
    }
}