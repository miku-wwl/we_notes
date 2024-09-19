https://leetcode.cn/problems/reverse-linked-list/description/

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
    fun reverseList(head: ListNode?): ListNode? {
        head ?: return null
        head.next ?: return head

        val nodeList = mutableListOf<ListNode>()
        var curr = head
        while (curr != null) {
            nodeList.add(curr)
            curr = curr.next
        }

        for (i in nodeList.size - 1 downTo 1) {
            nodeList[i].next = nodeList[i - 1]
        }
        nodeList[0].next = null

        return nodeList.last()
    }
}