https://leetcode.cn/problems/group-anagrams/description/

class Solution {
    fun groupAnagrams(strs: Array<String>): List<List<String>> {
        val map = mutableMapOf<String, MutableList<String>>()

        strs.forEach {
            val sortedStr = it.toList().sorted().toString()
            map[sortedStr] = map.getOrPut(sortedStr) { mutableListOf<String>() }.apply {
                this.add(it)
            }
        }
        
        val ans = mutableListOf<MutableList<String>>()
        map.forEach {
            ans.add(it.value)
        }
        return ans
    }
}