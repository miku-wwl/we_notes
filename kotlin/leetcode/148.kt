https://leetcode.cn/problems/sort-list/description/

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
    private var nodeList = mutableListOf<ListNode>()

    fun sortList(head: ListNode?): ListNode? {
        head ?: return null

        var hed = head
        while (hed != null) {
            nodeList.add(hed)
            hed = hed.next
        }

        nodeList.sortBy {
            it.`val`
        }

        for (i in 0..<nodeList.size - 1) {
            nodeList[i].next = nodeList[i + 1]
        }
        nodeList.last().next = null

        return nodeList[0]
    }
}