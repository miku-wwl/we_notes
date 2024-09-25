https://leetcode.cn/problems/valid-sudoku/description/

class Solution {

    fun isValidSudoku(board: Array<CharArray>): Boolean {
        return checkRowColBox(board, 0) &&
                checkRowColBox(board, 1) &&
                checkRowColBox(board, 2)
    }

    private fun checkRowColBox(board: Array<CharArray>, type: Int): Boolean {
        val sets = List(9) { mutableSetOf<Char>() }

        for (i in board.indices) {
            for (j in board[i].indices) {
                val value = board[i][j]
                if (value == '.') continue

                val index = when (type) {
                    0 -> i // Rows
                    1 -> j // Columns
                    2 -> (i / 3 * 3) + j / 3 // Boxes
                    else -> throw IllegalArgumentException("Invalid type")
                }

                if (!sets[index].add(value)) {
                    return false
                }
            }
        }

        return true
    }

}