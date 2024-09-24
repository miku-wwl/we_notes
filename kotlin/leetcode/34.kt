https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array/description/

class Solution {
    private var leftAns = -1
    private var rightAns = -1

    fun searchRange(nums: IntArray, target: Int): IntArray {
        findLeft(nums, target, 0, nums.size - 1)
        findRight(nums, target, 0, nums.size - 1)

        return mutableListOf(leftAns, rightAns).toIntArray()
    }

    private fun findLeft(nums: IntArray, target: Int, left: Int, right: Int) {
        if (right < left) return
        val mid = left + (right - left) / 2

        when {
            nums[mid] == target -> {
                leftAns = mid
                findLeft(nums, target, left, mid - 1)
                return
            }

            nums[mid] > target -> {
                findLeft(nums, target, left, mid - 1)
            }

            nums[mid] < target -> {
                findLeft(nums, target, mid + 1, right)
            }
        }
    }


    private fun findRight(nums: IntArray, target: Int, left: Int, right: Int) {
        if (right < left) return
        val mid = left + (right - left) / 2

        when {
            nums[mid] == target -> {
                rightAns = mid
                findRight(nums, target, mid + 1, right)
                return
            }

            nums[mid] > target -> {
                findRight(nums, target, left, mid - 1)
                return
            }

            nums[mid] < target -> {
                findRight(nums, target, mid + 1, right)
                return
            }
        }
    }
}