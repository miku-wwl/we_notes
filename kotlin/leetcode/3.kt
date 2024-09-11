https://leetcode.cn/problems/longest-substring-without-repeating-characters/description/

class Solution {
    fun lengthOfLongestSubstring(s: String): Int {
        if (s.isEmpty()) {
            return 0
        }
        var left = -1
        var ans = 0
        var map = mutableMapOf<Char, Int>()

        s.forEachIndexed { i, chr ->
            map[chr]?.let { v ->
                left = maxOf(left, v)
                ans = maxOf(ans, i - left)
                map[chr] = i
            }.also {
                ans = maxOf(ans, i - left)
                map[chr] = i
            }
        }
        return ans
    }
}