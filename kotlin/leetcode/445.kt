https://leetcode.cn/problems/add-two-numbers-ii/description/

/**
 * Example:
 * var li = ListNode(5)
 * var v = li.`val`
 * Definition for singly-linked list.
 * class ListNode(var `val`: Int) {
 *     var next: ListNode? = null
 * }
 */
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
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        var str1 = ""
        var str2 = ""
        var node = l1
        while (node != null) {
            str1 += node.`val`
            node = node.next
        }

        node = l2
        while (node != null) {
            str2 += node.`val`
            node = node.next
        }

        val list = (str1.toBigInteger() + str2.toBigInteger()).toString().toList().map {
            ListNode(it.digitToInt())
        }
        for (i in 0..list.size - 2) {
            list[i].next = list[i + 1]
        }
        list.last().next = null


        return list.first()
    }
}