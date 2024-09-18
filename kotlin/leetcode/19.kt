https://leetcode.cn/problems/remove-nth-node-from-end-of-list/

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
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        head ?: return null
        var node = head
        val nodeList = mutableListOf<ListNode>()

        while (node != null) {
            nodeList.add(node)
            node = node.next
        }

        when {
            nodeList.size == n -> return if (nodeList.size >= 2) nodeList[1] else null
            nodeList.size != n -> nodeList[nodeList.size - n - 1].next = nodeList[nodeList.size - n].next
        }

        return nodeList[0]
    }
}