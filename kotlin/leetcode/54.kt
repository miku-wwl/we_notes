https://leetcode.cn/problems/spiral-matrix/submissions/

class Solution {
    private val list = mutableListOf<Int>()
    private var n = 0
    private var m = 0
    private val directx = arrayOf(0, 1, 0, -1)
    private val directy = arrayOf(1, 0, -1, 0)

    fun spiralOrder(matrix: Array<IntArray>): List<Int> {
        n = matrix.size
        m = matrix[0].size

        findNum(0, 0, matrix, 0)
        return list
    }

    private fun findNum(x: Int, y: Int, matrix: Array<IntArray>, direct: Int) {
        list.add(matrix[x][y])
        matrix[x][y] = Int.MAX_VALUE
        var thisDirect = direct
        var dx = x + directx[thisDirect]
        var dy = y + directy[thisDirect]
        var times = 0
        while (times < 5) {
            when {
                dx <= n - 1 && dx >= 0 && dy <= m - 1 && dy >= 0 && matrix[dx][dy] != Int.MAX_VALUE -> {
                    findNum(dx, dy, matrix, thisDirect)
                    return
                }

                else -> {
                    thisDirect = (thisDirect + 1) % 4
                    dx = x + directx[thisDirect]
                    dy = y + directy[thisDirect]
                    times++
                }
            }
        }
        return
    }
}