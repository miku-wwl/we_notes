https://leetcode.cn/problems/remove-all-adjacent-duplicates-in-string/description/

class Solution {
    fun removeDuplicates(s: String): String {
        val chrStack = mutableListOf<Char>()
        s.forEach { chr ->
            if (chrStack.isNotEmpty() && chrStack.last() == chr) {
                chrStack.removeLast()
            } else {
                chrStack.add(chr)
            }
        }
        return chrStack.joinToString("")
    }
}