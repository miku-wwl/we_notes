https://leetcode.cn/problems/next-greater-element-ii/description/

class Solution {
    fun nextGreaterElements(nums: IntArray): IntArray {
        val list = mutableListOf<Int>()

        val ints = nums + nums
        val ans = IntArray(nums.size)
        for (i in ints.size - 1 downTo 0) {
            when {
                list.isEmpty() -> {

                }

                list.isNotEmpty() -> {
                    while (list.isNotEmpty()) {
                        if (list.last() <= ints[i]) {
                            list.removeLast()
                        } else {
                            break
                        }
                    }

                }
            }
            if (i <= nums.size - 1) {
                ans[i] = if (list.isEmpty()) -1 else (list.last())
            }
            list.add(ints[i])
        }
        return ans
    }
}