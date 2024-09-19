https://leetcode.cn/problems/swap-nodes-in-pairs/

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
    val nodes = mutableListOf<ListNode>()

    fun swapPairs(head: ListNode?): ListNode? {
        head ?: return null

        var node = head
        while (node != null) {
            nodes.add(node)
            node = node.next
        }


        for (i in 0..<nodes.size / 2) {
            val temp = nodes[2 * i]
            nodes[2 * i] = nodes[2 * i + 1]
            nodes[2 * i + 1] = temp
        }

        for (i in 0..<nodes.size - 1) {
            nodes[i].next = nodes[i + 1]
        }
        nodes.last().next = null
        return nodes.first()
    }
}