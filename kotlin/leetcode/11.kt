https://leetcode.cn/problems/container-with-most-water/submissions/563955497/

class Solution {
    fun maxArea(height: IntArray): Int {
        var l = 0
        var r = height.size - 1
        var maxCap = 0

        while (l < r) {
            val cap = (r - l) * minOf(height[l], height[r])
            maxCap = maxOf(maxCap, cap)

            if (height[l] < height[r])
                l++
            else
                r--
        }

        return maxCap
    }
}