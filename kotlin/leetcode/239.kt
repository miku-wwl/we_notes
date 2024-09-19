https://leetcode.cn/problems/sliding-window-maximum/description/

class Solution {
    private val stack = mutableListOf<Int>()
    private val ans = mutableListOf<Int>()

    fun maxSlidingWindow(nums: IntArray, k: Int): IntArray {
        for (i in 0..<k) {
            makeStack(nums, i, k)
        }
        ans.add(stack[0])
        for (i in k..<nums.size) {
            makeStack(nums, i, k)
            ans.add(stack[0])
        }
        return ans.map {
            nums[it]
        }.toIntArray()
    }

    private fun makeStack(nums: IntArray, i: Int, k: Int) {
//        println(stack.joinToString(","))
        if (stack.isEmpty()) {
            stack.add(i)
            return
        }
        if (i - stack[0] == k) {
//            println("i=$i, stack=$stack, k=$k")
            stack.removeFirst()
        }

        if (stack.isEmpty()) {
            stack.add(i)
            return
        }

        var last = stack.last()
        while (stack.isNotEmpty() && nums[stack.last()] < nums[i]) {
            stack.removeLast()
        }
        stack.add(i)
    }
}