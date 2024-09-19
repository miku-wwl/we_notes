https://leetcode.cn/problems/kth-smallest-element-in-a-bst/

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

    fun kthSmallest(root: TreeNode?, k: Int): Int {
        searchKthSmallest(root)
        return list[k - 1]
    }

    private fun searchKthSmallest(node: TreeNode?) {
        node ?: return
        searchKthSmallest(node.left)
        list.add(node.`val`)
        searchKthSmallest(node.right)
    }
}