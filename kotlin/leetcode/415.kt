https://leetcode.cn/problems/add-strings/description/

class Solution {
    fun addStrings(num1: String, num2: String): String {
        return (num1.toBigInteger() + num2.toBigInteger()).toString()
    }
}