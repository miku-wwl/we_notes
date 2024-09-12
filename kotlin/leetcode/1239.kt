https://leetcode.cn/problems/maximum-length-of-a-concatenated-string-with-unique-characters/description/

class Solution {
    private var maxLength = 0;

    fun maxLength(arr: List<String>): Int {
        find(0, arr.size, "", arr)
        return maxLength
    }

    private fun find(curr: Int, size: Int, str: String, arr: List<String>) {
        if (curr == size) {
            maxLength = maxOf(maxLength, str.length)
            return
        }

        val currStr = arr[curr]

        var ifContains = false
        for (i in currStr.indices) {
            if (str.contains(currStr[i]) || currStr.count(predicate = { chr -> chr == currStr[i] }) > 1) {
                ifContains = true
                break;
            }
        }

        str.count()


        if (!ifContains) {
            find(curr + 1, size, str + currStr, arr)
        }

        find(curr + 1, size, str, arr)

    }
}