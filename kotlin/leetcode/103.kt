https://leetcode.cn/problems/binary-tree-zigzag-level-order-traversal/description/

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
    fun zigzagLevelOrder(root: TreeNode?): List<List<Int>> {
        val res = mutableListOf<List<Int>>()
        if (root == null) return res

        val parents = mutableListOf(root)
        res.add(listOf(root.`val`))
        var direction = 0

        while (parents.isNotEmpty()) {
            direction = 1 - direction
            val level = mutableListOf<Int>()
            val children = mutableListOf<TreeNode>()

            for (node in parents) {
                node.left?.let {
                    children.add(it)
                    level.add(it.`val`)
                }
                node.right?.let {
                    children.add(it)
                    level.add(it.`val`)
                }
            }

            if (children.isEmpty()) break

            res.add(if (direction == 0) level else level.reversed())
            parents.clear()
            parents.addAll(children)
        }

        return res
    }
}