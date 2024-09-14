https://leetcode.cn/problems/excel-sheet-column-number/submissions/564670244/

class Solution {
    fun titleToNumber(columnTitle: String): Int {
        val colum = columnTitle.reversed()
        var times = 1

        var ans = 0
        colum.forEach {
            ans += (it - 'A' + 1) * times
            times *= 26
        }
        
        return ans
    }
}