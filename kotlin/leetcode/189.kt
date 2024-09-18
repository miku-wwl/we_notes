https://leetcode.cn/problems/rotate-array/

class Solution {
    fun rotate(nums: IntArray, k: Int): Unit {
        var ns = nums + nums
        var kk = k % nums.size

        var desIntArray = ns.toList().subList(nums.size - kk, nums.size - kk + nums.size).toIntArray()

        for (i in nums.indices) {
            nums[i] = desIntArray[i]
        }
        return
    }
}
