https://leetcode.cn/problems/minimum-path-sum/

class Solution {
    fun minPathSum(grid: Array<IntArray>): Int {
        val m = grid.size
        val n = grid[0].size
        val f = Array(m + 10) { IntArray(n + 10) }
        f[0][0] = grid[0][0]

        for (i in 1..<n) {
            f[0][i] = f[0][i - 1] + grid[0][i]
        }
        for (i in 1..<m) {
            f[i][0] = f[i - 1][0] + grid[i][0]
        }

        for (i in 1..<m) {
            for (j in 1..<n) {
                f[i][j] = minOf(f[i - 1][j], f[i][j - 1]) + grid[i][j]
            }
        }

        return f[m - 1][n - 1]
    }
}