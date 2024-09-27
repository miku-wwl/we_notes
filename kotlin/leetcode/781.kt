https://leetcode.cn/problems/rabbits-in-forest/description/

class Solution {
    fun numRabbits(answers: IntArray): Int {
        var ans = 0
        val ansCount = IntArray(1000)

        answers.forEach {
            ansCount[it]++
        }
        for (i in 0..<1000) {
            while (ansCount[i] > 0) {
                ansCount[i] -= (i + 1)
                ans += (i + 1)
            }
        }
        return ans
    }
}