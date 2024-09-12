https://leetcode.cn/problems/longest-common-subsequence/description/

class Solution {
    fun longestCommonSubsequence(text1: String, text2: String): Int {
        val m = text1.length
        val n = text2.length
        val dp = Array(m + 1) { IntArray(n + 1) }

        dp[0][0] = 0
        for (i in 1..m) {
            for (j in 1..n) {
                // println("i=$i, j=$j")
                dp[i][j] = if (text1[i - 1] == text2[j - 1]) dp[i - 1][j - 1] + 1 else maxOf(dp[i - 1][j], dp[i][j - 1])
            }
        }
        return dp[m][n]
    }
}