https://leetcode.cn/problems/palindrome-linked-list/description/

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
    fun isPalindrome(head: ListNode?): Boolean {
        head ?: true
        val nodeList = mutableListOf<ListNode>()
        var node = head
        var ifPalindrome = true

        while (node != null) {
            nodeList.add(node)
            node = node.next
        }

        nodeList.forEachIndexed { index, listNode ->
            ifPalindrome = ifPalindrome && listNode.`val` == nodeList[nodeList.size - 1 - index].`val`
        }
        return ifPalindrome

    }
}