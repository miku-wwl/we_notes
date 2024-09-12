https://leetcode.cn/problems/binary-tree-maximum-path-sum/submissions/564090010/

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

    private var maxRoute = Int.MIN_VALUE;
    fun maxPathSum(root: TreeNode?): Int {
        findMaxPathSum(root)
        return maxRoute
    }

    private fun findMaxPathSum(node: TreeNode?): Int {
        node ?: return 0
        val leftRoute = findMaxPathSum(node.left)
        val rightRoute = findMaxPathSum(node.right)

        println("node.val = ${node.`val`}, left = $leftRoute, right = $rightRoute")
        maxRoute =
            maxOf(maxRoute, node.`val`, node.`val` + leftRoute + rightRoute, node.`val` + leftRoute, node.`val` + rightRoute)

        return maxOf(node.`val`, node.`val` + leftRoute, node.`val` + rightRoute)
    }
}