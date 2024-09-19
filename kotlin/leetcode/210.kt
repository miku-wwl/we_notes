https://leetcode.cn/problems/course-schedule-ii/

class Solution {
    fun findOrder(numCourses: Int, prerequisites: Array<IntArray>): IntArray {
        val map = mutableListOf<MutableList<Int>>()
        val count = mutableListOf<Int>()
        val queue = mutableListOf<Int>()
        val ans = mutableListOf<Int>()

        for (i in 0..<numCourses) {
            map.add(mutableListOf())
            count.add(0)
        }

        prerequisites.forEach { (x, y) ->
            map[y].add(x)
            count[x]++
        }

        for (i in 0..<numCourses) {
            if (count[i] == 0) {
                queue.add(i)
                ans.add(i)
            }
        }

        while (queue.isNotEmpty()) {
            val first = queue.first()
            val ints = map[first]

            ints.forEach {
                count[it]--
                if (count[it] == 0) {
                    queue.add(it)
                    ans.add(it)
                }
            }
            queue.removeFirst()
        }

        return if (count.sum() != 0) mutableListOf<Int>().toIntArray() else ans.toList().toIntArray()
    }
}