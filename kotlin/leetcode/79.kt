https://leetcode.cn/problems/word-search/description/

class Solution {
    private var n = 0
    private var m = 0
    private var found = false
    private lateinit var board: Array<CharArray>
    private lateinit var word: String
    fun exist(board: Array<CharArray>, word: String): Boolean {
        n = board.size
        m = board[0].size
        this.board = board
        this.word = word
        for (i in 0..<n) {
            for (j in 0..<m) {
                findWord(0, i, j, mutableSetOf<Pair<Int, Int>>())
            }
        }

        return found
    }

    private fun findWord(curr: Int, x: Int, y: Int, set: MutableSet<Pair<Int, Int>>) {
        if (curr == word.length) {
            found = true
            return
        }
        if (x < 0 || x >= n || y < 0 || y >= m) return
        
        if (board[x][y] != word[curr]) return
        if (set.contains(x to y)) return
        
        val directx = mutableListOf(1, 0, -1, 0)
        val directy = mutableListOf(0, 1, 0, -1)

        for (i in 0..3) {
            set.add(x to y)
            findWord(curr + 1, x + directx[i], y + directy[i], set)
            set.remove(x to y)
        }
    }
}