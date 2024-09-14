https://leetcode.cn/problems/roman-to-integer/submissions/564271017/


class Solution {
    fun romanToInt(s: String): Int {
        val romanValues = mapOf(
            "I" to 1,
            "V" to 5,
            "X" to 10,
            "L" to 50,
            "C" to 100,
            "D" to 500,
            "M" to 1000,
            "IV" to 4,
            "IX" to 9,
            "XL" to 40,
            "XC" to 90,
            "CD" to 400,
            "CM" to 900
        )

        var i = 0
        var result = 0

        while (i < s.length) {
            // Check if the current two characters form a special combination
            if (i + 1 < s.length && romanValues.containsKey(s.substring(i, i + 2))) {
                result += romanValues[s.substring(i, i + 2)]!!
                i += 2
            } else {
                // If not a special combination, add the value of the single character
                result += romanValues[s[i].toString()]!!
                i++
            }
        }

        return result
    }
}