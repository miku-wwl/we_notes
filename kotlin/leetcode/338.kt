https://leetcode.cn/problems/counting-bits/description/

class Solution {
    fun countBits(n: Int): IntArray {
        val list = mutableListOf<Int>()
        for (i in 0..n) {
            val toBinaryString = Integer.toBinaryString(i)
            list.add(toBinaryString.count {
                it == '1'
            })
        }
        return list.toIntArray()
    }
}