https://leetcode.cn/problems/course-schedule/description/

class Solution {
    fun canFinish(numCourses: Int, prerequisites: Array<IntArray>): Boolean {
        val map = mutableListOf<MutableList<Int>>()
        val count = mutableListOf<Int>()
        val queue = mutableListOf<Int>()

        for (i in 0..numCourses) {
            map.add(mutableListOf())
            count.add(0)
        }

        prerequisites.forEach { (x, y) ->
            map[y].add(x)
            count[x]++
        }

        for (i in 0..numCourses) {
            if (count[i] == 0) {
                queue.add(i)
            }
        }

        while (queue.isNotEmpty()) {
            val first = queue.first()
            val ints = map[first]

            ints.forEach {
                count[it]--
                if (count[it] == 0) {
                    queue.add(it)
                }
            }
            queue.removeFirst()
        }

        return count.sum() == 0
    }
}