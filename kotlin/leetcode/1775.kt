https://leetcode.cn/problems/equal-sum-arrays-with-minimum-number-of-operations/

@Suppress("UNUSED_EXPRESSION")
class Solution {
    fun minOperations(nums1: IntArray, nums2: IntArray): Int {
        var n1 = nums1.clone()
        var n2 = nums2.clone()

        when {
            nums1.sum() <= nums2.sum() -> {
                val n3 = n1
                n1 = n2
                n2 = n3
            }
        }
        n1.sort()
        n2.sort()

        var difference = n1.sum() - n2.sum()

        var l = n1.size - 1
        var r = 0
        var count = 0

        while (l > -1 || r < n2.size) {
            when {
                difference <= 0 -> return count
                r == n2.size -> {
                    difference -= (n1[l] - 1)
                    l--
                    count++
                    continue
                }

                l == -1 -> {
                    difference -= (6 - n2[r])
                    r++
                    count++
                    continue
                }

                (n1[l] - 1 >= 6 - n2[r]) -> {
                    difference -= (n1[l] - 1)
                    l--
                    count++
                    continue
                }

                (n1[l] - 1 < 6 - n2[r]) -> {
                    difference -= (6 - n2[r])
                    r++
                    count++
                    continue
                }
            }

        }

        if (difference > 0) return -1
        return count

    }
}