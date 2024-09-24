https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-cooldown/submissions/567648741/

class Solution {
    fun maxProfit(prices: IntArray): Int {
        /*
            状态分析：
            集合：f[i][j]表示第i天在j状态下的最大利润
            状态：
                一共有4种状态
                0. 今天这个状态属于买了股票
                1. 今天这个状态属于卖了股票
                2. 已经卖出了股票且度过了冷冻期，一直没操作，处于卖出状态
                3. 今天这个状态属于冷冻期
            属性： maxOf(状态1，状态2) （因为只有卖出了股票利润才最大）
        */

        val f = Array<IntArray>(prices.size+1){IntArray(4)}
        f[0][0] = -prices[0]
        f[0][1] = 0
        for (i in 1..prices.size) {
            // 达到"买入股票"的状态：
            // 1. 今天之前就是处于这个买入的状态 f[i][0] = f[i-1][0]
            // 2. 今天之前是冷冻期，今天买入 f[i][0] = f[i-1][3] - prices[i-1]
            // 3. 今天之前保持股票卖出，度过了冷冻期，今天买入 f[i][0] = f[i-1][2] - prices[i-1]
            // 三者取最大
            f[i][0] = maxOf(f[i-1][0], maxOf(f[i-1][2], f[i-1][3]) - prices[i-1])

            // 达到"卖出股票"的状态
            // 1. 昨天买入股票，今天直接卖出
            f[i][1] = f[i-1][0] + prices[i-1]

            // 达到"已经卖出了股票且度过了冷冻期"的状态
            // 1. 今天之前就是处于已经出售股票的状态 f[i-1][1]
            // 2. 今天的前一天是冷冻期，今天买了，又在今天卖出股票 f[i-1][2]
            f[i][2] = maxOf(f[i-1][1], f[i-1][2])

            // 达到"冷冻期"的状态
            // 1. 昨天卖出了股票
            f[i][3] = f[i-1][1]
        }
        // 一定是卖出了股票的情况，利润最大
        return maxOf(f[prices.size][1], f[prices.size][2])
    }
}