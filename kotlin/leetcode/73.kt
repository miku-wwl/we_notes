https://leetcode.cn/problems/set-matrix-zeroes/description/

class Solution {
    fun setZeroes(matrix: Array<IntArray>): Unit {
        val n = matrix.size
        val m = matrix[0].size
        val newMatrix = Array(n) { CharArray(m) }
        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                if (matrix[i][j] == 0) {
                    for (k in matrix.indices) newMatrix[k][j] = '*'
                    for (l in matrix[0].indices) newMatrix[i][l] = '*'
                }
            }
        }

        for (i in matrix.indices) {
            for (j in matrix[0].indices) {
                if (newMatrix[i][j] == '*') {
                    matrix[i][j] = 0
                }
            }
        }
        return
    }
}