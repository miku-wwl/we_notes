https://leetcode.cn/problems/perfect-squares/description/

class Solution {
    fun numSquares(n: Int): Int {
        val list = mutableListOf<Int>()
        for (i in 1..100) {
            list.add(i * i)
        }
        val ints = IntArray(10001)
        ints[0] = 0
        ints[1] = 1

        for (i in 2..n) {
            ints[i] = Int.MAX_VALUE

            list.forEach {
                if (i - it >= 0) {
                    ints[i] = minOf(ints[i], ints[i - it] + 1)
                }
            }
        }

        // ints.filterIndexed { index, _ ->
        //     index < 100
        // }.forEach {
        //     println(it)
        // }

        return ints[n]
    }
}