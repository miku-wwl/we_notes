https://leetcode.cn/problems/battleships-in-a-board/description/

class Solution {
    private var fleets = 0
    private var n = 0
    private var m = 0
    fun countBattleships(board: Array<CharArray>): Int {
        n = board.size
        m = board[0].size

        for (i in 0..<n) {
            for (j in 0..<m) {
                if (board[i][j] == 'X') {
                    fleets++
                    searchFleets(board, i, j)
                }
            }
        }
        return fleets
    }

    private fun searchFleets(board: Array<CharArray>, x: Int, y: Int) {
        if (x < 0 || x >= n || y < 0 || y >= m) return
        if (board[x][y] == '.') return
        
        board[x][y] = '.'
        searchFleets(board, x + 1, y)
        searchFleets(board, x - 1, y)
        searchFleets(board, x, y + 1)
        searchFleets(board, x, y - 1)
    }
}