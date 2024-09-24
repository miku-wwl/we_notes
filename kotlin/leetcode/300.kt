https://leetcode.cn/problems/longest-increasing-subsequence/

class Solution {
    fun lengthOfLIS(nums: IntArray): Int {
        val f = IntArray(nums.size)
        for (i in nums.size - 1 downTo 0) {
            f[i] = 1
            for (j in i + 1..<nums.size) {
                if (nums[j] > nums[i]) {
                    f[i] = maxOf(f[i], f[j] + 1)
                }
            }
        }

        return f.max()
    }
}