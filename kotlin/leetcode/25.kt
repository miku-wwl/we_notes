https://leetcode.cn/problems/reverse-nodes-in-k-group/description/

class Solution {
    private val nodes = mutableListOf<ListNode>()
    fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
        var node = head

        while (node != null) {
            nodes.add(node)
            node = node.next
        }

        var start = 0
        while (start + k - 1 <= nodes.size - 1) {
            val renodes = mutableListOf<ListNode>()
            for (i in start + k - 1 downTo start) {
                renodes.add(nodes[i])
            }
            for (i in start..start + k - 1) {
                nodes[i] = renodes[i - start]
            }
            start += k
        }

        for (i in 0..<nodes.size - 1) {
            nodes[i].next = nodes[i + 1]
        }
        nodes.last().next = null
        return nodes.first()
    }
}