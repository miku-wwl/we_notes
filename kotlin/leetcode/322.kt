https://leetcode.cn/problems/coin-change/

class Solution {
    fun coinChange(coins: IntArray, amount: Int): Int {
        val f = IntArray(amount + 1)
        f[0] = 0
        for (i in 1..amount) {
            f[i] = Int.MAX_VALUE
            coins.forEach {
                if (i - it >= 0 && f[i - it] != Int.MAX_VALUE) {
                    f[i] = minOf(f[i], f[i - it] + 1)
                }
            }
        }

        return if (f[amount] == Int.MAX_VALUE) -1 else f[amount]

    }
}