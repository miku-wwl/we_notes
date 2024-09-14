https://leetcode.cn/problems/intersection-of-two-linked-lists/submissions/


class Solution {
    fun getIntersectionNode(headA: ListNode?, headB: ListNode?): ListNode? {
        headA ?: return null
        headB ?: return null

        val nodeSet = mutableSetOf<Int>() // 存储节点的 hashCode
        var ha = headA
        var hb = headB

        // 遍历第一个链表，记录每个节点的 hashCode
        while (ha != null) {
            nodeSet.add(System.identityHashCode(ha))
            ha = ha.next
        }

        // 遍历第二个链表，查找已记录的 hashCode
        while (hb != null) {
            if (nodeSet.contains(System.identityHashCode(hb))) {
                return hb
            }
            hb = hb.next
        }

        return null
    }
}