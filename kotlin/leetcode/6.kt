https://leetcode.cn/problems/zigzag-conversion/description/

class Solution {
    fun convert(s: String, numRows: Int): String {
        if (numRows == 1) return s
        val strs = Array<String>(numRows) { "" }
        findStr(0, 0, 1, s, strs)

        var ans = ""
        strs.forEach {
            ans += it
        }
        return ans
    }

    private fun findStr(i: Int, curr: Int, direct: Int, s: String, strs: Array<String>) {
        if (curr == s.length) return
        strs[i] = strs[i] + s[curr]
        var newDirect = direct
        when (i) {
            0 -> newDirect = 1
            strs.size - 1 -> newDirect = -1
        }
        findStr(i + newDirect, curr + 1, newDirect, s, strs)
    }
}