https://leetcode.cn/problems/search-a-2d-matrix/description/

class Solution {
    private var found = false
    private var m = 0
    private var n = 0
    fun searchMatrix(matrix: Array<IntArray>, target: Int): Boolean {
        m = matrix.size
        n = matrix[0].size
        findMatrix(m - 1, 0, matrix, target)
        return found
    }

    private fun findMatrix(x: Int, y: Int, matrix: Array<IntArray>, target: Int) {
        if (x < 0 || y >= n) return
        when {
            matrix[x][y] == target -> {
                found = true
                return
            }

            matrix[x][y] < target -> {
                findMatrix(x, y + 1, matrix, target)
                return
            }

            matrix[x][y] > target -> {
                findMatrix(x - 1, y, matrix, target)
                return
            }
        }
        return
    }
}