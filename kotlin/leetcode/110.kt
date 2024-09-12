https://leetcode.cn/problems/balanced-binary-tree/

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
    private var result = true

    private fun helper(root: TreeNode?): Int {
        root ?: return 0

        val left = helper(root.left)
        val right = helper(root.right)

        if (abs(left - right) > 1) {
            result = false
        }

        return maxOf(left + 1, right + 1)
    }

    fun isBalanced(root: TreeNode?): Boolean {
        helper(root)
        return result
    }
}