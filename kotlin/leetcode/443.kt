https://leetcode.cn/problems/string-compression/description/

class Solution {
    fun compress(chars: CharArray): Int {
        if (chars.size == 1) return 1
        var list = ""
        var str = chars[0].toString()
        for (i in 1..<chars.size) {
            when {
                chars[i] != chars[i - 1] -> {
                    if (str.length > 1) {
                        list += str.last()
                        list += str.length
                        str = chars[i].toString()
                    } else {
                        list += str.last()
                        str = chars[i].toString()
                    }
                }

                chars[i] == chars[i - 1] -> {
                    str += chars[i]
                }
            }
        }

        if (str.length > 1) {
            list += str.last()
            list += str.length
        } else {
            list += str.last()
        }

        for (i in list.indices) {
            chars[i] = list[i]
        }

        return list.length
    }
}