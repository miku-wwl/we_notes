https://leetcode.cn/problems/diameter-of-binary-tree/

class Solution {
    private var ans = 0

    fun diameterOfBinaryTree(root: TreeNode?): Int {
        searchBinaryTree(root)
        return ans
    }

    private fun searchBinaryTree(node: TreeNode?): Int {
        node ?: return 0
        val left = searchBinaryTree(node.left)
        val right = searchBinaryTree(node.right)
        ans = maxOf(left + right, ans)

        return maxOf(left, right) + 1
    }
}