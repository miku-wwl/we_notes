https://leetcode.cn/problems/3sum/

class Solution {
    fun threeSum(nums: IntArray): List<List<Int>> {
        if (nums.size < 3) return emptyList()

        val ans = mutableListOf<List<Int>>()
        val set = mutableSetOf<List<Int>>()
        val count = mutableMapOf<Int, Int>()

        // Count occurrences of each number
        nums.forEach { num ->
            count[num] = (count[num] ?: 0) + 1
        }

        nums.sort()

        for (i in nums.indices) {
            if (i > 0 && nums[i] == nums[i - 1]) continue // Skip duplicates

            for (j in i + 1 until nums.size) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue // Skip duplicates

                val x = -(nums[i] + nums[j])
                if (x < nums[j]) continue // Ensure that x is greater than or equal to j

                val countX = count[x] ?: 0
                when {
                    x == nums[i] && countX < 2 -> continue
                    x == nums[j] && countX < 2 -> continue
                    x == nums[i] && x == nums[j] && countX < 3 -> continue
                    countX <= 0 -> continue
                }

                val list = listOf(nums[i], nums[j], x)
                if (!set.contains(list)) {
                    ans.add(list)
                    set.add(list)
                }
            }
        }

        return ans
    }
}