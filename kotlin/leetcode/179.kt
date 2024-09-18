https://leetcode.cn/problems/largest-number/

class Solution {

    fun largestNumber(nums: IntArray): String {
        val numStrings = nums.map { it.toString() }
        val sortedStrings = numStrings.sortedWith(::compare)
        val result = sortedStrings.joinToString("")

        // 如果结果的第一个字符是'0'，则整个字符串表示的是0
        return if (result.isEmpty() || result[0] == '0') "0" else result
    }

  
    private fun compare(a: String, b: String): Int {
        val ab = a + b
        val ba = b + a
        return ba.compareTo(ab)
    }

}