https://leetcode.cn/problems/reverse-linked-list-ii/

class Solution {
    fun reverseBetween(head: ListNode?, left: Int, right: Int): ListNode? {
        head ?: return null

        val list = mutableListOf<ListNode?>(ListNode(666))

        var node = head
        while (node != null) {
            list.add(node)
            node = node.next
        }
        list.add(null)
        list[0]!!.next = list[1]
        
        for (i in right downTo left + 1) {
            list[i]!!.next = list[i - 1]
        }
        list[left - 1]!!.next = list[right]
        list[left]!!.next = list[right + 1]

        return list.first()!!.next

    }
}