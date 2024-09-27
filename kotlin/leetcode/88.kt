https://leetcode.cn/problems/merge-sorted-array/

class Solution {
    fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {
        if (nums2.isEmpty()) return

        val filterNums1 = nums1.filterIndexed { index, it ->
            index < m
        }.toMutableList()

        filterNums1.addAll(nums2.toList())
        filterNums1.sort()

        for (i in nums1.indices) {
            nums1[i] = filterNums1[i]
        }
        return
    }
}