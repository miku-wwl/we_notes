https://leetcode.cn/problems/reverse-integer/

import kotlin.math.abs

class Solution {
    fun reverse(x: Int): Int {
        println(abs(x.toLong()))
        val mark = if (x < 0) -1 else 1
        val toLong = abs(x.toLong()).toString().reversed().toLong()
        if (toLong * mark !in Int.MIN_VALUE..Int.MAX_VALUE) {
            return 0
        } else {
            return (toLong * mark).toInt()
        }

    }
}