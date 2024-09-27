https://leetcode.cn/problems/binary-tree-inorder-traversal/

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
    private val list = mutableListOf<Int>()
    fun inorderTraversal(root: TreeNode?): List<Int> {
        findInorderTraversal(root)
        return list
    }

    private fun findInorderTraversal(node: TreeNode?) {
        node ?: return
        findInorderTraversal(node.left)
        list.add(node.`val`)
        findInorderTraversal(node.right)
    }
}