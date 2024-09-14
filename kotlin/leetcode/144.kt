https://leetcode.cn/problems/binary-tree-preorder-traversal/

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
    private var list = mutableListOf<Int>()
    fun preorderTraversal(root: TreeNode?): List<Int> {
        searchPreorder(root)
        return list
    }

    private fun searchPreorder(node: TreeNode?) {
        node ?: return
        list.add(node.`val`)
        searchPreorder(node.left)
        searchPreorder(node.right)
        return
    }
}