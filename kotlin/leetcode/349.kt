https://leetcode.cn/problems/intersection-of-two-arrays/

class Solution {
    fun intersection(nums1: IntArray, nums2: IntArray): IntArray {
        return nums1.intersect(nums2.toSet()).toIntArray()
    }
}