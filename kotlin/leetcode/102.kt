https://leetcode.cn/problems/binary-tree-level-order-traversal/description/

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
    private var map = mutableMapOf<Int, MutableList<Int>>()

    fun levelOrder(root: TreeNode?): List<List<Int>> {
        levelOrderSearch(root, 0)
        var sortedKeys: List<Int> = map.keys.sorted()

        val sortedValue = sortedKeys.map { key ->
            map[key]!!.toList()
        }
        return sortedValue
    }

    private fun levelOrderSearch(root: TreeNode?, level: Int) {
        if (root == null) return
        val v = root.`val`

        map.getOrPut(level) { mutableListOf() }.add(v)
        levelOrderSearch(root.left, level + 1)
        levelOrderSearch(root.right, level + 1)

    }
}