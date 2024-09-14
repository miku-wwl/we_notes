https://leetcode.cn/problems/excel-sheet-column-title/description/

class Solution {
    fun convertToTitle(columnNumber: Int): String {
        var column = columnNumber

        var str = ""

        while (column > 26) {
            var mod = column % 26
            when {
                mod == 0 -> {
                    mod = 26
                    column = column / 26 - 1
                }
                mod != 0 -> column /= 26
            }
            str += (mod + 'A'.code - 1).toChar()
        }
        str += (column + 'A'.code - 1).toChar()

        return str.reversed()
    }
}