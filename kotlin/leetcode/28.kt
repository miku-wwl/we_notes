https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/description/

class Solution {
    fun strStr(haystack: String, needle: String): Int {
        for (i in 0..haystack.length - needle.length) {
            if (haystack.substring(i, i + needle.length) == needle) {
                return i
            }
        }
        return -1
    }
}