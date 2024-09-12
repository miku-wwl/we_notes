https://leetcode.cn/problems/minimum-depth-of-binary-tree/submissions/

/**
 * Example:
 * var ti = TreeNode(5)
 * var v = ti.`val`
 * Definition for a binary tree node.
 * class TreeNode(var `val`: Int) {
 *     var left: TreeNode? = null
 *     var right: TreeNode? = null
 * }
 */
class Solution {
    private var ans = Int.MAX_VALUE
    fun minDepth(root: TreeNode?): Int {
        if (root == null) return 0
        minSearch(root, 1)
        return ans
    }

    private fun minSearch(root: TreeNode?, depth: Int) {
        root ?: return

        if (root.left == null && root.right == null) {
            ans = minOf(ans, depth)
        }

        minSearch(root.left, depth + 1)
        minSearch(root.right, depth + 1)
    }
}