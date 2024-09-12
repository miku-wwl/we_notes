https://leetcode.cn/problems/populating-next-right-pointers-in-each-node-ii/submissions/

/**
 * Definition for a Node.
 * class Node(var `val`: Int) {
 *     var left: Node? = null
 *     var right: Node? = null
 *     var next: Node? = null
 * }
 */

class Solution {

    private var nodeList: MutableList<Pair<Node, Int>> = mutableListOf()

    fun connect(root: Node?): Node? {

        root ?: return null
        nodeList.add(root to 1)

        while (nodeList.size >= 1) {

            val node = nodeList.first().first
            val level = nodeList.first().second

            node.left?.let {
                nodeList.add(it to level + 1)
            }
            node.right?.let {
                nodeList.add(it to level + 1)
            }

            node.next = if (nodeList.size > 1 && nodeList[0].second == nodeList[1].second) nodeList[1].first else null
            nodeList.removeFirst()
        }
        return root
    }
}