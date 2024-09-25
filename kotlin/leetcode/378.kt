https://leetcode.cn/problems/kth-smallest-element-in-a-sorted-matrix/description/

class Solution {
    fun kthSmallest(matrix: Array<IntArray>, k: Int): Int {
        val n = matrix.size
        val m = matrix[0].size
        val list = mutableListOf<Int>()
        for (i in 0..<n) {
            for (j in 0..<m) {
                list.add(matrix[i][j])
            }
        }

        return list.apply {
            this.sort()
        }[k-1]

    }
}