https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-ii/submissions/564069623/

class Solution {
    fun maxProfit(prices: IntArray): Int {
        var nohold = 0
        var hold = -1 * prices[0]
        for (i in 1..<prices.size) {
            val noholdTemp = nohold
            val holdTemp = hold
            nohold = maxOf(holdTemp + prices[i], noholdTemp)
            hold = maxOf(noholdTemp - prices[i], holdTemp)
        }
        return nohold
    }
}