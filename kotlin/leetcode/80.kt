https://leetcode.cn/problems/remove-duplicates-from-sorted-array-ii/

class Solution {
    fun removeDuplicates(nums: IntArray): Int {
        var size = 1
        var count = 1
        for (i in 1..<nums.size) {
            when {
                nums[i] != nums[i - 1] -> {
                    nums[size] = nums[i]
                    size++
                    count = 1
                    continue
                }

                nums[i] == nums[i - 1] && count < 2 -> {
                    nums[size] = nums[i]
                    size++
                    count++
                }
            }
        }

        return size
    }
}