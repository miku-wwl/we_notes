https://leetcode.cn/problems/trapping-rain-water/submissions/

class Solution {
    fun trap(height: IntArray): Int {
        if (height.size == 1) return 0

        var max = height.first()
        val lmax = IntArray(height.size)
        for (i in 1..<height.size) {
            lmax[i] = max
            max = maxOf(max, height[i])
        }

        max = height.last()
        val rmax = IntArray(height.size)
        for (i in height.size - 1 downTo 0) {
            rmax[i] = max
            max = maxOf(max, height[i])
        }
        var ans = 0

        for (i in 1..height.size - 2) {
            ans += maxOf((minOf(lmax[i], rmax[i]) - height[i]), 0)
        }

        return ans
    }
}