https://leetcode.cn/problems/find-all-anagrams-in-a-string/description/

class Solution {
    fun findAnagrams(s: String, p: String): List<Int> {
        if (s.length < p.length) return listOf()

        val ans = mutableListOf<Int>()
        val sMap = mutableMapOf<Char, Int>()
        val pMap = mutableMapOf<Char, Int>()

        for (i in 'a'..'z') {
            sMap[i] = 0
            pMap[i] = 0
        }

        p.forEach {
            pMap[it] = pMap[it]!! + 1
        }

        for (i in p.indices) {
            val it = s[i]
            sMap[it] = sMap[it]!! + 1
        }

        var l = 0
        var r = p.length - 1

        while (r <= s.length - 1) {
            if (l != 0) {
                sMap[s[l - 1]] = sMap[s[l - 1]]!! - 1
                sMap[s[r]] = sMap[s[r]]!! + 1
            }
            if (checkSp(sMap, pMap)) {
                ans.add(l)
            }
            l++
            r++
        }

        return ans
    }

    private fun checkSp(sMap: MutableMap<Char, Int>, pMap: MutableMap<Char, Int>): Boolean {
        for (i in 'a'..'z') {
            if (sMap[i] != pMap[i]) {
                return false
            }
        }
        return true
    }
}
