https://leetcode.cn/problems/merge-two-sorted-lists/description/


class Solution {
    fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
        var n1 = list1
        var n2 = list2
        val nodeList = mutableListOf<ListNode>()

        while (n1 != null) {
            nodeList.add(n1)
            n1 = n1.next
        }

        while (n2 != null) {
            nodeList.add(n2)
            n2 = n2.next
        }

        if (nodeList.isEmpty()) return null

        nodeList.sortBy {
            it.`val`
        }

        for (i in 0..<nodeList.size - 1) {
            nodeList[i].next = nodeList[i + 1]
        }
        
        nodeList.last().next = null
        return nodeList.first()


    }
}