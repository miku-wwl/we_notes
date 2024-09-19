https://leetcode.cn/problems/kth-largest-element-in-an-array/submissions/566105544/

class Solution {
    private var ans = 0

    private fun findKmin(nums: IntArray, k: Int) {
        if (k == 1) ans = nums.max()
        val predict = nums[0]

        val l1 = nums.asSequence().filterIndexed { index, _ ->
            index > 0
        }.filter {
            it <= predict
        }.toList().toIntArray()

        val l2 = nums.asSequence().filterIndexed { index, _ ->
            index > 0
        }.filter {
            it > predict
        }.toList().toIntArray()

        when {
            k <= l1.size -> findKmin(l1, k)
            k == l1.size + 1 -> ans = predict
            k > l1.size + 1 -> findKmin(l2, k - l1.size - 1)
        }
    }

    fun findKthLargest(nums: IntArray, k: Int): Int {
        findKmin(nums, nums.size - k + 1)
        return ans
    }
}