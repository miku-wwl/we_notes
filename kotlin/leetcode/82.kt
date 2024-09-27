https://leetcode.cn/problems/remove-duplicates-from-sorted-list-ii/description/

class Solution {
    fun deleteDuplicates(head: ListNode?): ListNode? {
        head ?: return null

        val list = mutableListOf<ListNode>()
        var node = head
        while (node != null) {
            list.add(node)
            node = node.next
        }
        val set = mutableSetOf<Int>()
        val repeatSet = mutableSetOf<Int>()
        list.forEach {
            if (set.contains(it.`val`)) {
                repeatSet.add(it.`val`)
            } else {
                set.add(it.`val`)
            }
        }
        val filters = list.filter {
            !repeatSet.contains(it.`val`)
        }

        if (filters.isEmpty()) return null
        
        for (i in 0..<filters.size - 1) {
            filters[i].next = filters[i + 1]
        }

        filters.last().next = null
        return filters.first()

    }
}