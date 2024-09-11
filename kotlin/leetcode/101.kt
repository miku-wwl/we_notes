https://leetcode.cn/problems/symmetric-tree/description/

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
    fun isSymmetric(root: TreeNode?): Boolean {
        return checkSymmetric(root, root)
    }

    private fun checkSymmetric(p: TreeNode?, q: TreeNode?): Boolean {
        if (p == null && q == null) return true
        if (p == null || q == null) return false

        return p.`val` == q.`val` && checkSymmetric(p.left, q.right) && checkSymmetric(p.right, q.left)
    }
}