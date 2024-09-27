https://leetcode.cn/problems/restore-ip-addresses/

class Solution {
    fun restoreIpAddresses(s: String): List<String> {
        if (s.length < 4 || s.length > 12) return listOf()
        val ans = mutableListOf<String>()
        for (i in 1..3)
            for (j in 1..3)
                for (k in 1..3)
                    for (l in 1..3) {
                        if (i + j + k + l == s.length) {
                            val strs = mutableListOf<String>()
                            strs.add(s.substring(0, i))
                            strs.add(s.substring(i, i + j))
                            strs.add(s.substring(i + j, i + j + k))
                            strs.add(s.substring(i + j + k, i + j + k + l))
                            val count = strs.filter {
                                it.toInt() < 256
                            }.count {
                                it.length == it.toInt().toString().length
                            }
                            if (count == 4) {
                                ans.add("${strs[0]}.${strs[1]}.${strs[2]}.${strs[3]}")
                            }

                        }
                    }

        return ans
    }
}