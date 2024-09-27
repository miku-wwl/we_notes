https://leetcode.cn/problems/largest-rectangle-in-histogram/

class Solution {

    fun largestRectangleArea(heights: IntArray): Int {
        val len = heights.size
        if (len == 0) {
            return 0
        }
        if (len == 1) {
            return heights[0]
        }

        var res = 0
        val stack = ArrayDeque<Int>(len)
        for (i in heights.indices) {
            // 当前高度比栈顶的高度要小时，计算栈中的柱形最大宽度
            while (stack.isNotEmpty() && heights[i] < heights[stack.last()]) {
                val curHeight = heights[stack.removeLast()]
                while (stack.isNotEmpty() && heights[stack.last()] == curHeight) {
                    stack.removeLast()
                }

                val curWidth = if (stack.isEmpty()) {
                    i
                } else {
                    i - stack.last() - 1
                }

                res = kotlin.math.max(res, curHeight * curWidth)
            }
            stack.addLast(i)
        }

        // 处理剩余的元素
        while (stack.isNotEmpty()) {
            val curHeight = heights[stack.removeLast()]
            while (stack.isNotEmpty() && heights[stack.last()] == curHeight) {
                stack.removeLast()
            }
            val curWidth = if (stack.isEmpty()) {
                len
            } else {
                len - stack.last() - 1
            }
            res = kotlin.math.max(res, curHeight * curWidth)
        }
        return res
    }
}