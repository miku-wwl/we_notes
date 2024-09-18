https://leetcode.cn/problems/4sum/

class Solution {
    fun fourSum(nums: IntArray, target: Int): List<List<Int>> {
        val map = mutableMapOf<Long, Int>()
        val listSet = mutableSetOf<List<Int>>()
        val ans = mutableListOf<List<Int>>()
        nums.sort()
        nums.forEach {
            map[it.toLong()] = map.getOrPut(it.toLong()) { 0 } + 1
        }
        for (i in nums.indices) {
            for (j in i + 1..<nums.size) {
                for (k in j + 1..<nums.size) {
                    val x = nums[i].toLong()
                    val y = nums[j].toLong()
                    val z = nums[k].toLong()
                    map[x] = map[x]!! - 1
                    map[y] = map[y]!! - 1
                    map[z] = map[z]!! - 1
                    if (map.containsKey(target - x - y - z) && map[target - x - y - z]!! > 0 && target - x - y - z >= z) {
                        val list = mutableListOf(x.toInt(), y.toInt(), z.toInt(), (target - x - y - z).toInt())
                        if (!listSet.contains(list)) {
                            listSet.add(list)
                            ans.add(list)
                        }
                    }
                    map[x] = map[x]!! + 1
                    map[y] = map[y]!! + 1
                    map[z] = map[z]!! + 1

                }
            }
        }
        return ans

    }
}