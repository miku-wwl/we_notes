https://leetcode.cn/problems/subarray-sum-equals-k/submissions/568197719/

class Solution {
    fun subarraySum(nums: IntArray, k: Int): Int {
        var ans = 0
        for (i in nums.indices) {
            var sum = 0
            for (j in i..<nums.size) {
                sum += nums[j]
                if (sum == k) {
                    ans++
                }
            }
        }
        return ans
    }
}