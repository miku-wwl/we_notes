https://leetcode.cn/problems/fibonacci-number/description/

class Solution {
    fun fib(n: Int): Int {
        if (n == 0) return 0
        if (n == 1) return 1

        val nums = IntArray(n + 1)
        nums[0] = 0
        nums[1] = 1
        for (i in 2..n) {
            nums[i] = nums[i - 1] + nums[i - 2]
        }
        return nums[n]
    }
}