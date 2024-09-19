https://leetcode.cn/problems/search-a-2d-matrix-ii/

class Solution {
    private var m = 0
    private var n = 0
    private var found = false

    fun searchMatrix(matrix: Array<IntArray>, target: Int): Boolean {
        m = matrix.size
        n = matrix[0].size

        findTarget(m - 1, 0, matrix, target)
        return found
    }

    private fun findTarget(x: Int, y: Int, matrix: Array<IntArray>, target: Int) {
        if (x < 0 || y > n - 1) return

        when {
            matrix[x][y] == target -> {
                found = true
                return
            }

            matrix[x][y] < target -> {
                findTarget(x, y + 1, matrix, target)
                return
            }

            matrix[x][y] > target -> {
                findTarget(x - 1, y, matrix, target)
                return
            }

        }
    }
}