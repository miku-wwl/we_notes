https://leetcode.cn/problems/sum-root-to-leaf-numbers/submissions/564264438/

class Solution {
    var ans: Int = 0

    fun sumNumbers(root: TreeNode?): Int {
        root ?: return 0
        searchSumNumbers(root, "" )
        return ans
    }

    private fun searchSumNumbers(node: TreeNode, s: String) {
        var str = s + node.`val`
        println(str)
        if (node.left == null && node.right == null) {
            ans += str.toInt()
            return
        }

        node.left?.let {
            searchSumNumbers(it, str)
        }
        node.right?.let {
            searchSumNumbers(it, str)
        }

        return
    }
}