https://leetcode.cn/problems/longest-common-prefix/submissions/564304289/

class Solution {
    fun longestCommonPrefix(strs: Array<String>): String {
        if (strs.isEmpty()) return ""

        val minLength = strs.minOfOrNull { it.length } ?: return ""
        val commonPrefix = StringBuilder()

        for (i in 0 until minLength) {
            val char = strs[0][i]
            if (strs.all { it[i] == char }) {
                commonPrefix.append(char)
            } else {
                break
            }
        }

        return commonPrefix.toString()
    }
}