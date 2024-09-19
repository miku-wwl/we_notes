https://leetcode.cn/problems/implement-stack-using-queues/description/

class MyStack() {

    private var list1 = mutableListOf<Int>()
    private var list2 = mutableListOf<Int>()

    fun push(x: Int) {
        list1.add(x)
    }

    fun pop(): Int {
        list2 = mutableListOf<Int>()
        var ans = 0
        while (list1.size > 1) {
            list2.add(list1.removeFirst())
        }
        ans = list1[0]
        list1 = list2
        return ans
    }

    fun top(): Int {
        list2 = mutableListOf<Int>()
        var ans = 0
        while (list1.size > 1) {
            list2.add(list1.removeFirst())
        }
        ans = list1[0]
        list2.add(ans)
        list1 = list2

        return ans
    }

    fun empty(): Boolean {
        return list1.isEmpty()
    }

}

/**
 * Your MyStack object will be instantiated and called as such:
 * var obj = MyStack()
 * obj.push(x)
 * var param_2 = obj.pop()
 * var param_3 = obj.top()
 * var param_4 = obj.empty()
 */