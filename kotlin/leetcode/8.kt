https://leetcode.cn/problems/string-to-integer-atoi/description/

class Solution {
    fun myAtoi(s: String): Int {
        if (s.isEmpty()) return 0

        var mark = 1
        var pre = true
        var str = ""
        for (i in s.indices) {
            when {
                s[i] == ' ' && pre -> {}
                s[i] in '0'..'9' -> {
                    str += s[i]
                    pre = false
                }

                s[i] == '-' && pre -> {
                    if (str.isEmpty()) {
                        mark = -1
                        pre = false
                    } else {
                        break
                    }
                }

                s[i] == '+' && pre -> {
                    if (str.isEmpty()) {
                        mark = 1
                        pre = false
                    } else {
                        break
                    }
                }

                else -> {
                    if (str.isEmpty()) return 0
                    break
                }

            }
        }
        if (str.isEmpty()) return 0
        val l = str.toBigInteger().times(mark.toBigInteger())

        println(l)
        when {
            l > Int.MAX_VALUE.toBigInteger() -> return Int.MAX_VALUE
            l < Int.MIN_VALUE.toBigInteger() -> return Int.MIN_VALUE
            else -> return l.toInt()
        }
    }
}