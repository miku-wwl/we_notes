https://leetcode.cn/problems/unique-paths/description/

class Solution {
    fun uniquePaths(m: Int, n: Int): Int {
        val f = Array(m + 10) { IntArray(n + 10) }
        for (i in 1..n) {
            f[1][i] = 1
        }
        for (i in 1..m) {
            f[i][1] = 1
        }

        for (i in 2..m) {
            for (j in 2..n) {
                f[i][j] = f[i - 1][j] + f[i][j - 1]
            }
        }

        return f[m][n]
    }
}