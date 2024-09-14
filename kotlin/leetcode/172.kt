https://leetcode.cn/problems/factorial-trailing-zeroes/solutions/

class Solution {
    fun trailingZeroes(n: Int): Int {
        var ans = 0
        for (i in 5..n) {
            var temp = i
            while (temp % 5 == 0) {
                ans++
                temp /= 5
            }
        }
        return ans
    }
}