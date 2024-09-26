https://leetcode.cn/problems/climbing-stairs/

class Solution {
    fun climbStairs(n: Int): Int {
        when (n) {
            1 -> return 1
            2 -> return 2
        }

        val f = IntArray(n + 1)
        f[1] = 1
        f[2] = 2

        for (i in 3..n) {
            f[i] = f[i - 1] + f[i - 2]
        }

        return f[n]
    }
}