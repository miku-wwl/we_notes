https://leetcode.cn/problems/binary-tree-right-side-view/

/**
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 */

class Solution {
    private val list = mutableListOf<Int>()
    private val levelMap = mutableMapOf<Int, Int>()

    fun rightSideView(root: TreeNode?): List<Int> {
        searchRightSideView(root, 0)

        return levelMap.entries.sortedBy { it.key }.map { it.value }.toList()
    }

    private fun searchRightSideView(node: TreeNode?, level: Int) {
        node ?: return
        levelMap[level] = node.`val`
        list.add(node.`val`)

        searchRightSideView(node.left, level + 1)
        searchRightSideView(node.right, level + 1)

    }
}