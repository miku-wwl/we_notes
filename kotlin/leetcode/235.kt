https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-search-tree/

/**
 * Definition for a binary tree node.
 * class TreeNode(var `val`: Int = 0) {
 *     var left: TreeNode? = null
 *     var right: TreeNode? = null
 * }
 */


class Solution {
    private val pList = mutableListOf<TreeNode>()
    private val qList = mutableListOf<TreeNode>()

    fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        findP(root, p!!)
        findQ(root, q!!)

        pList.reverse()
        qList.reverse()

        var ans = TreeNode()

        for (i in 0..<minOf(pList.size, qList.size)) {
            if (pList[i].`val` == qList[i].`val`) {
                ans = pList[i]
            }
        }
        return ans
    }

    private fun findP(node: TreeNode?, p: TreeNode): Boolean {
        node ?: return false

        val ans = node.`val` == p.`val` || findP(node.left, p) || findP(node.right, p)
        if (ans) pList.add(node)
        return ans
    }

    private fun findQ(node: TreeNode?, q: TreeNode): Boolean {
        node ?: return false

        val ans = node.`val` == q.`val` || findQ(node.left, q) || findQ(node.right, q)
        if (ans) qList.add(node)
        return ans
    }
    
}