https://leetcode.cn/problems/sqrtx/description/

import kotlin.math.sqrt

class Solution {
    fun mySqrt(x: Int): Int {
        return sqrt(x.toDouble()).toInt()
    }
}