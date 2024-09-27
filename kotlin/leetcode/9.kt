https://leetcode.cn/problems/palindrome-number/

class Solution {
    fun isPalindrome(x: Int): Boolean {
        val str = x.toString()
        val reStr = x.toString().reversed()
        return str == reStr
    }
}