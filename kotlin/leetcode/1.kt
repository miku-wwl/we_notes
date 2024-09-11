https://leetcode.cn/problems/two-sum/description/

class Solution {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val numIndices = mutableMapOf<Int, Int>()

        nums.forEachIndexed { i, num ->
            val complement = target - num
            numIndices[num]?.let {
                return intArrayOf(i, it)
            }
            numIndices[complement] = i
        }
        throw IllegalArgumentException("No two sum solution")
    }
}