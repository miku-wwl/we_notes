https://leetcode.cn/problems/add-two-numbers/

class Solution {
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        val n1 = mutableListOf<ListNode>()
        val n2 = mutableListOf<ListNode>()

        var current = l1
        while (current != null) {
            n1.add(current)
            current = current.next
        }

        current = l2
        while (current != null) {
            n2.add(current)
            current = current.next
        }

        val longerList = if (n1.size > n2.size) n1 else n2
        val shorterList = if (n1.size > n2.size) n2 else n1

        // Add values of shorter list to the corresponding nodes of the longer list
        for (i in shorterList.indices) {
            longerList[i].`val` += shorterList[i].`val`
        }

        // Carry handling
        var carry = 0
        for (node in longerList.withIndex()) {
            val index = node.index
            val currentNode = node.value
            currentNode.`val` += carry
            carry = currentNode.`val` / 10
            currentNode.`val` %= 10

            // Break early if we've processed all elements and there's no carry left
            if (index == longerList.lastIndex && carry > 0) {
                longerList.add(ListNode(carry))
                break
            }
        }

        // Link the nodes together
        longerList.zipWithNext { current, next ->
            current.next = next
        }

        // Set the last node's next to null
        longerList.lastOrNull()?.next = null

        return longerList.firstOrNull()
    }
}