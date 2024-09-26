https://leetcode.cn/problems/unique-paths-ii/description/

class Solution {
    fun uniquePathsWithObstacles(obstacleGrid: Array<IntArray>): Int {
        val m = obstacleGrid.size
        val n = obstacleGrid[0].size

        val f = Array(m + 10) { IntArray(n + 10) }
        var ob = 1
        for (i in 1..n) {
            if (obstacleGrid[0][i-1] == 1) ob = 0
            f[1][i] = ob
        }

        ob = 1
        for (i in 1..m) {
            if (obstacleGrid[i-1][0] == 1) ob = 0
            f[i][1] = ob
        }

        for (i in 2..m) {
            for (j in 2..n) {
                f[i][j] = if (obstacleGrid[i-1][j-1] == 1) 0 else f[i - 1][j] + f[i][j - 1]
            }
        }

        return f[m][n]
    }
}