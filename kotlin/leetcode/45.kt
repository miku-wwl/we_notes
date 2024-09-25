https://leetcode.cn/problems/jump-game-ii/description/

class Solution {
    fun jump(nums: IntArray): Int {
        val n = nums.size
        val f = IntArray(n)
        f.fill(Int.MAX_VALUE, 0)
        f[0] = 0

        for (i in nums.indices) {
            for (j in 1..nums[i]) {
                if (i + j >= nums.size) {
                    break
                }
                f[i + j] = minOf(f[i + j], f[i] + 1)
            }
        }
        return f.last()

    }
}