https://leetcode.cn/problems/merge-k-sorted-lists/description/

class Solution {
    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        if (lists.isEmpty()) return null

        val nodeList = mutableListOf<ListNode>()
        lists.forEach {
            var node = it
            while (node != null) {
                nodeList.add(node)
                node = node.next
            }
        }

        if (nodeList.size == 0) return null

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