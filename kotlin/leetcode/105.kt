https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/

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
    private val indexMap = mutableMapOf<Int, Int>()

    private fun myBuildTree(preorder: IntArray, inorder: IntArray,
                            preorderLeft: Int, preorderRight: Int,
                            inorderLeft: Int, inorderRight: Int): TreeNode? {
        if (preorderLeft > preorderRight) {
            return null
        }

        val preorderRoot = preorderLeft
        val inorderRoot = indexMap[preorder[preorderRoot]] ?: error("Root not found in inorder map")

        val root = TreeNode(preorder[preorderRoot])
        val sizeLeftSubtree = inorderRoot - inorderLeft

        root.left = myBuildTree(preorder, inorder, preorderLeft + 1, preorderLeft + sizeLeftSubtree, inorderLeft, inorderRoot - 1)
        root.right = myBuildTree(preorder, inorder, preorderLeft + sizeLeftSubtree + 1, preorderRight, inorderRoot + 1, inorderRight)

        return root
    }

    fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
        val n = preorder.size
        for (i in 0 until n) {
            indexMap[inorder[i]] = i
        }
        return myBuildTree(preorder, inorder, 0, n - 1, 0, n - 1)
    }
}