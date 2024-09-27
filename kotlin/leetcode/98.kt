https://leetcode.cn/problems/validate-binary-search-tree/description/

class Solution {
    private var ifBST = true
    private val list = mutableListOf<Int>()
    fun isValidBST(root: TreeNode?): Boolean {
        findIfBST(root)
        for (i in 1..<list.size){
            if (list[i]<=list[i-1]){
                ifBST = false
                break
            }
        }
        
        return ifBST
    }

    private fun findIfBST(node: TreeNode?) {
        node ?: return
        findIfBST(node.left)
        list.add(node.`val`)
        findIfBST(node.right)
    }
}