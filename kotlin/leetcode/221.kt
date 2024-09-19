https://leetcode.cn/problems/maximal-square/description/

class Solution {
    fun maximalSquare(matrix: Array<CharArray>): Int {
        var ans = 0
        val m = matrix.size
        if (m == 0) return 0
        val n = matrix[0].size
        // 为了方便下标的计算, 矩阵容量多出 1 行 1 列
        val f = Array(m + 1) { IntArray(n + 1) { 0 } }

        for (i in 1..m) {
            for (j in 1..n) {
                if (matrix[i - 1][j - 1] == '1') {
                    // f(i,j) 表示坐标(i,j) 范围内, 由 1 组成的最大正方形的边长
                    f[i][j] = 1 + minOf(f[i - 1][j - 1], f[i - 1][j], f[i][j - 1])
                    // 找到最大的正方形边长
                    ans = maxOf(ans, f[i][j])
                }
            }
        }

        return ans * ans
    }
}