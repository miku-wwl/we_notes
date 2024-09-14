https://leetcode.cn/problems/candy/submissions/564277953/

class Solution {
    fun candy(ratings: IntArray): Int {
        if (ratings.isEmpty()) return 0

        // 初始化左右两侧的糖果数量数组
        val left = IntArray(ratings.size) { 1 }
        val right = IntArray(ratings.size) { 1 }

        // 从前向后计算左侧糖果数量
        for (i in 1 until ratings.size) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1
            }
        }

        // 从后向前计算右侧糖果数量
        for (i in ratings.size - 2 downTo 0) {
            if (ratings[i] > ratings[i + 1]) {
                right[i] = right[i + 1] + 1
            }
        }

        // 计算总糖果数量

        return left.zip(right).sumOf { (l, r) -> maxOf(l, r) }
    }
}