https://leetcode.cn/problems/reverse-words-in-a-string/description/

class Solution {
    fun reverseWords(s: String): String {
        var str = ""
        s.split(Regex("\\s+")).filter {
            it.isNotEmpty()
        }.forEach() {
            str = " $it$str"
        }

        return str.substring(1)
    }
}