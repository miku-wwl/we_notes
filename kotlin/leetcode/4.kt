https://leetcode.cn/problems/median-of-two-sorted-arrays/description/

class Solution {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        val list = nums1.toMutableList().apply {
            this.addAll(nums2.toList())
        }.apply {
            this.sort()
        }

        println(list)
        return if (list.size % 2 == 1) list[list.size / 2].toDouble() else (list[list.size / 2] + list[list.size / 2 - 1]).toDouble() / 2
    }
}