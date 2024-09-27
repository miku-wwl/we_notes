https://leetcode.cn/problems/remove-duplicates-from-sorted-list/description/

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
    fun deleteDuplicates(head: ListNode?): ListNode? {
        head ?: return null

        val set = mutableSetOf<Int>()
        val list = mutableListOf<ListNode>()
        var node = head
        while (node != null) {
            when {
                set.contains(node.`val`) -> {}
                !set.contains(node.`val`) -> {
                    list.add(node)
                    set.add(node.`val`)
                }
            }
            node = node.next
        }

        for (i in 0..<list.size - 1) {
            list[i].next = list[i + 1]
        }
        list.last().next = null
        return list.first()
    }
}