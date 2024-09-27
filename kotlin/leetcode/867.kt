https://leetcode.cn/problems/transpose-matrix/

class Solution {
    fun transpose(matrix: Array<IntArray>): Array<IntArray> {
        val m = matrix.size
        val n = matrix[0].size
        val newMatrix = Array(n) { IntArray(m) }
        for (i in 0..<m) {
            for (j in 0..<n) {
                newMatrix[j][i] = matrix[i][j]
            }
        }
        return newMatrix
    }
}