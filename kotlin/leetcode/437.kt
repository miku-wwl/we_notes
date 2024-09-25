https://leetcode.cn/problems/path-sum-iii/description/

/**
 * Example:
 * var ti = TreeNode(5)
 * var v = ti.`val`
 * Definition for a binary tree node.

 */


class Solution {
    private val nodeList = mutableListOf<TreeNode>()
    private var ans = 0
    fun pathSum(root: TreeNode?, targetSum: Int): Int {
        root ?: return 0
        searchAllNodes(root)

        nodeList.forEach {
            searchTargetSum(it, 0, targetSum)
        }

        return ans
    }

    private fun searchTargetSum(node: TreeNode?, curr: Long, targetSum: Int) {
        node ?: return
        val sum = node.`val`.toLong() + curr
        if (sum == targetSum.toLong()) {
            ans++
        }
        searchTargetSum(node.left, sum, targetSum)
        searchTargetSum(node.right, sum, targetSum)
    }

    private fun searchAllNodes(node: TreeNode?) {
        node ?: return
        nodeList.add(node)
        searchAllNodes(node.left)
        searchAllNodes(node.right)
    }
}