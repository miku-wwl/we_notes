https://leetcode.cn/problems/closest-dessert-cost/

import kotlin.math.abs

class Solution {
    private var ans = Int.MAX_VALUE
    private var costMap = mutableMapOf<Int, Int>()

    fun closestCost(baseCosts: IntArray, toppingCosts: IntArray, target: Int): Int {

        val doubleToppingCosts = toppingCosts + toppingCosts


        baseCosts.forEach {
            findClosetCost(doubleToppingCosts, it, target, -1)
        }
        return ans

    }

    private fun findClosetCost(toppingCosts: IntArray, curr: Int, target: Int, position: Int) {

        when {
            abs(curr - target) < abs(ans - target) -> {
                ans = curr
            }

            (abs(curr - target) == abs(ans - target)) && curr < ans -> {
                ans = curr
                println("ans=$ans")
            }

            abs(curr - target) > abs(ans - target) && curr > target -> return
        }

        for (i in position + 1..<toppingCosts.size) {
            findClosetCost(toppingCosts, curr + toppingCosts[i], target, i)
        }


    }
}