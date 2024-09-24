https://leetcode.cn/problems/find-median-from-data-stream/description/

import java.util.PriorityQueue

class MedianFinder() {
    private val maxPq = PriorityQueue<Int> { a, b ->
        b - a
    }
    private val minPq = PriorityQueue<Int>()

    fun addNum(num: Int) {
        when {
            maxPq.size == minPq.size -> {
                maxPq.add(num)
                minPq.add(maxPq.poll())
            }

            maxPq.size < minPq.size -> {
                when {
                    minPq.peek() < num -> {
                        maxPq.add(minPq.poll())
                        minPq.add(num)
                    }

                    minPq.peek() >= num -> {
                        maxPq.add(num)
                    }

                }
            }
        }
    }

    fun findMedian(): Double {
        when {
            maxPq.size == minPq.size -> {
                return (maxPq.peek() + minPq.peek()).toDouble() / 2
            }

            maxPq.size < minPq.size -> {
                return minPq.peek().toDouble()
            }
        }
        return 0.0
    }

}


/**
 * Your MedianFinder object will be instantiated and called as such:
 * var obj = MedianFinder()
 * obj.addNum(num)
 * var param_2 = obj.findMedian()
 */