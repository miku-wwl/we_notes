https://leetcode.cn/problems/invert-binary-tree/description/

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
    fun invertTree(root: TreeNode?): TreeNode? {
        searchInvertTree(root)
        return root
    }

    private fun searchInvertTree(node: TreeNode?) {
        node ?: return
        searchInvertTree(node.left)
        searchInvertTree(node.right)

        val temp = node.left
        node.left = node.right
        node.right = temp

    }
}