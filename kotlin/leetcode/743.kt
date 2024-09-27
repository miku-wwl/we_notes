https://leetcode.cn/problems/network-delay-time/description/

class Solution {
    fun networkDelayTime(times: Array<IntArray>, n: Int, k: Int): Int {
        val map = mutableMapOf<Int, MutableList<Pair<Int, Int>>>()
        for (i in times.indices) {
            val x = times[i][0]
            val y = times[i][1]
            val z = times[i][2]
            map[x] = map.getOrPut(x) { mutableListOf() }
            map[x]!!.add(y to z)
        }
        val dist = IntArray(n + 1) { Int.MAX_VALUE }
        dist[k] = 0
        dist[0] = 0  //节点从1开始, 0的情况不考虑

        val list = mutableListOf(k)
        while (list.isNotEmpty()) {
            val x = list.first()
            map[x]?.forEach {
                val y = it.first
                val z = it.second
                if (dist[x] + z < dist[y]) {
                    dist[y] = dist[x] + z
                    when {
                        list.contains(y) -> {}
                        !list.contains(y) -> {
                            list.add(y)
                        }
                    }
                }
            }
            list.removeFirst()
        }
        val ans = dist.max()
        return if (ans == Int.MAX_VALUE) -1 else ans
    }
}