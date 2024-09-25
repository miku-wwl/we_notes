https://leetcode.cn/problems/rotate-image/

class Solution {
    fun rotate(matrix: Array<IntArray>): Unit {
        val n = matrix.size
        for (i in 0 until n) {
            for (j in i + 1 until n) { // 将内层循环的起始索引调整为 i+1
                val temp = matrix[i][j]
                matrix[i][j] = matrix[j][i]
                matrix[j][i] = temp
            }
        }

        // 逐行逆序
        for (i in 0 until n) {
            for (j in 0 until n / 2) {
                val temp = matrix[i][j]
                matrix[i][j] = matrix[i][n - j - 1]
                matrix[i][n - j - 1] = temp
            }
        }

    }
}