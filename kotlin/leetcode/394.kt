https://leetcode.cn/problems/decode-string/description/

研究一下

class Solution {
    class RepeatItem(var count: Int) {
        val sb = StringBuilder()

        override fun toString(): String = sb.toString().repeat(count)
    }

    fun decodeString(s: String): String {
        val stack = mutableListOf<RepeatItem>()
        var num = StringBuilder()
        // 最外一层视作只重复一次的RepeatItem
        stack.add(RepeatItem(1))
        for (c in s) {
            when (c) {
                in '0'..'9' -> {
                    num.append(c)
                }
                // 通过数字，构造出一个重复指定次数的RepeatItem，之后读取到的字母都是在栈顶的item上面append
                '[' -> {
                    stack.add(RepeatItem(num.toString().toIntOrNull() ?: 1))
                    num = StringBuilder()
                }
                // 栈顶出栈，把解析出的完整字符串叠加到下面一层
                ']' -> {
                    val item = stack.removeLast()
                    stack.last().sb.append(item.toString())
                }
                // 是字母，直接在栈顶上面append
                else -> {
                    stack.last().sb.append(c)
                }
            }
        }
        return stack.last().toString()
    }
}