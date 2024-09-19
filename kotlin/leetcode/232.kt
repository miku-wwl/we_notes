https://leetcode.cn/problems/implement-queue-using-stacks/description/

class MyQueue() {

    private val list1 = mutableListOf<Int>()
    private val list2 = mutableListOf<Int>()

    fun push(x: Int) {
        list1.add(x)
    }

    fun pop(): Int {
        if (list2.isNotEmpty()) return list2.removeLast()

        while (list1.isNotEmpty()) {
            list2.add(list1.removeLast())
        }
        return list2.removeLast()
    }

    fun peek(): Int {
        if (list2.isNotEmpty()) return list2.last()
        while (list1.isNotEmpty()) {
            list2.add(list1.removeLast())
        }
        return list2.last()
    }

    fun empty(): Boolean {
        return list1.isEmpty() && list2.isEmpty()
    }

}

/**
 * Your MyQueue object will be instantiated and called as such:
 * var obj = MyQueue()
 * obj.push(x)
 * var param_2 = obj.pop()
 * var param_3 = obj.peek()
 * var param_4 = obj.empty()
 */