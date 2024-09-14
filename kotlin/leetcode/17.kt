https://leetcode.cn/problems/letter-combinations-of-a-phone-number/

class Solution {
    var ans = mutableListOf<String>()
    var map = mutableMapOf<Char, String>(
        '2' to "abc",
        '3' to "def",
        '4' to "ghi",
        '5' to "jkl",
        '6' to "mno",
        '7' to "pqrs",
        '8' to "tuv",
        '9' to "wxyz"
    )

    fun letterCombinations(digits: String): List<String> {
        if (digits.isEmpty()) return listOf()
        searchLetter(0, "", digits)
        return ans
    }

    private fun searchLetter(curr: Int, currStr: String, digits: String) {
        if (curr == digits.length) {
            ans.add(currStr)
            return
        }
        val chr = digits[curr]
        val num = map[chr]!!
        num.forEach {
            searchLetter(curr + 1, currStr + it, digits)
        }
    }
}