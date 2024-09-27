https://leetcode.cn/problems/edit-distance/description/


class Solution {
    fun minDistance(word1: String, word2: String): Int {
        val n = word1.length
        val m = word2.length

        // 如果有一个字符串为空串
        if (n * m == 0) {
            return n + m
        }

        // DP 数组
        val D = Array(n + 1) { IntArray(m + 1) }

        // 边界状态初始化
        for (i in 0..n) {
            D[i][0] = i
        }
        for (j in 0..m) {
            D[0][j] = j
        }

        // 计算所有 DP 值
        for (i in 1..n) {
            for (j in 1..m) {
                val left = D[i - 1][j] + 1
                val down = D[i][j - 1] + 1
                var leftDown = D[i - 1][j - 1]
                if (word1[i - 1] != word2[j - 1]) {
                    leftDown += 1
                }
                D[i][j] = kotlin.math.min(left, kotlin.math.min(down, leftDown))
            }
        }
        return D[n][m]
    }
}