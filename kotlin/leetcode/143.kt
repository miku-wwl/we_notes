https://leetcode.cn/problems/reorder-list/description/

class Solution {
    fun reorderList(head: ListNode?): Unit {
        var nodeList = mutableListOf<ListNode>()
        var hed = head;

        while (hed != null) {
            nodeList.add(hed)
            hed = hed.next
        }
        if (nodeList.size == 1) return

        var l = 0
        var r = nodeList.size - 1

        while (l <= r) {
            nodeList[l].next = nodeList[r]
            nodeList[r].next = nodeList[l + 1]
            l++
            r--
        }

        nodeList[r + 1].next = null
        return
    }

}