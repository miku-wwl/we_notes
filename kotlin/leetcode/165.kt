https://leetcode.cn/problems/compare-version-numbers/description/

class Solution {
    fun compareVersion(version1: String, version2: String): Int {
        val v1 = version1.split(".").toMutableList()
        val v2 = version2.split(".").toMutableList()

        while (v1.size != v2.size) {
            when {
                v1.size < v2.size -> v1.add("0")
                v1.size > v2.size -> v2.add("0")
            }
        }

        for (i in v1.indices) {
            when {
                v1[i].toInt() < v2[i].toInt() -> return -1
                v1[i].toInt() > v2[i].toInt() -> return 1
            }
        }
        return 0
    }
}