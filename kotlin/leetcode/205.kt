https://leetcode.cn/problems/isomorphic-strings/description/

class Solution {
    fun isIsomorphic(s: String, t: String): Boolean {
        if (s.length != t.length) return false

        val map = mutableMapOf<Char, Char>()
        val remap = mutableMapOf<Char, Char>()

        for (i in s.indices) {
            val x = s[i]
            val y = t[i]
            
            if (map.containsKey(x)) {
                val z = map[x]
                if (z != y) {
                    return false
                }
            } else {
                map[x] = y
            }

            if (remap.containsKey(y)) {
                val z = remap[y]
                if (z != x) {
                    return false
                }
            } else {
                remap[y] = x
            }
        }

        return true
    }
}