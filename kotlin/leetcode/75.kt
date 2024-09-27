https://leetcode.cn/problems/sort-colors/description/

class Solution {
    fun sortColors(nums: IntArray): Unit {
        val num0 = nums.count {
            it == 0
        }
        val num1 = nums.count {
            it == 1
        }
        val num2 = nums.count {
            it == 2
        }

        for (i in 0..<num0) {
            nums[i] = 0
        }

        for (i in num0..<num0 + num1) {
            nums[i] = 1
        }
        for (i in num0 + num1..<num0 + num1 + num2) {
            nums[i] = 2
        }
        
    }
}