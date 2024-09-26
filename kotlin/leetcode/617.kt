https://leetcode.cn/problems/merge-two-binary-trees/description/

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
    fun mergeTrees(root1: TreeNode?, root2: TreeNode?): TreeNode? {
        return merge(root1, root2)
    }

    private fun merge(node1: TreeNode?, node2: TreeNode?): TreeNode? {
        node1 ?: return node2
        node2 ?: return node1

        node1.`val` += node2.`val`
        node1.left = merge(node1.left, node2.left)
        node1.right = merge(node1.right, node2.right)
        return node1
    }
}