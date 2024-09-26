https://leetcode.cn/problems/palindromic-substrings/description/

class Solution {
    fun countSubstrings(s: String): Int {
        var ans = 0
        val f = Array(s.length + 10) { IntArray(s.length + 10) }
        for (i in s.indices) {
            f[i][1] = 1
            ans++
        }
        for (j in 2..s.length) {
            for (i in s.indices) {
                if (i + j - 1 <= s.length - 1 && s[i] == s[i + j - 1]) {
                    when {
                        j % 2 == 0 && (f[i + 1][j - 2] == 1 || j - 2 == 0) -> {
                            f[i][j] = 1
                            ans++
                        }

                        j % 2 == 1 && f[i + 1][j - 2] == 1 -> {
                            f[i][j] = 1
                            ans++
                        }
                    }
                }
            }
        }
        return ans
    }
}