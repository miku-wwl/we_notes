https://leetcode.cn/problems/clumsy-factorial/description/

class Solution {
    fun clumsy(N: Int): Int {
        if (N <= 2) return N
        if (N == 3) return 6

        var sum = N * (N - 1) / (N - 2) + (N - 3)
        var current = N - 4

        while (current >= 4) {
            sum += (-current * (current - 1) / (current - 2) + (current - 3))
            current -= 4
        }

        return sum - clumsy(current)
    }
}