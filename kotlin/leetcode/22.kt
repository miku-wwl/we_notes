https://leetcode.cn/problems/generate-parentheses/description/

class Solution {
    private val ans = mutableListOf<String>()

    fun generateParenthesis(n: Int): List<String> {
        findParenthesis(0, 0, n, "")
        return ans
    }

    private fun findParenthesis(left: Int, right: Int, n: Int, str: String) {
        if (left == n && right == n) {
            ans.add(str)
            return
        }
        when {
            left > n -> return
            right > n -> return
            left < right -> return
            left == right -> findParenthesis(left + 1, right, n, "$str(")
            left > right -> {
                findParenthesis(left + 1, right, n, "$str(")
                findParenthesis(left, right + 1, n, "$str)")
            }
        }

    }
}