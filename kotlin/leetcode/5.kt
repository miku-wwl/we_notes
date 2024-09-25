https://leetcode.cn/problems/longest-palindromic-substring/

class Solution {

    fun longestPalindrome(s: String): String {
        val len = s.length
        if (len < 2) {
            return s
        }

        var maxLen = 1
        var begin = 0
        // dp[i][j] 表示 s[i..j] 是否是回文串
        val dp = Array(len) { BooleanArray(len) }

        // 初始化：所有长度为 1 的子串都是回文串
        for (i in 0 until len) {
            dp[i][i] = true
        }

        val charArray = s.toCharArray()
        // 递推开始
        // 先枚举子串长度
        for (L in 2 until len + 1) {
            // 枚举左边界，左边界的上限设置可以宽松一些
            for (i in 0 until len) {
                // 由 L 和 i 可以确定右边界，即 j - i + 1 = L 得
                val j = L + i - 1
                // 如果右边界越界，就可以退出当前循环
                if (j >= len) {
                    break
                }

                if (charArray[i] != charArray[j]) {
                    dp[i][j] = false
                } else {
                    if (j - i < 3) {
                        dp[i][j] = true
                    } else {
                        dp[i][j] = dp[i + 1][j - 1]
                    }
                }

                // 只要 dp[i][j] == true 成立，就表示子串 s[i..j] 是回文，此时记录回文长度和起始位置
                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1
                    begin = i
                }
            }
        }
        return s.substring(begin, begin + maxLen)
    }
}