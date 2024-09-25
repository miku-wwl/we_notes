https://leetcode.cn/problems/queue-reconstruction-by-height/description/

/**
     * 406. 根据身高重建队列
     * 思想：高个子先站好位，矮个子插入到K位置上，前面肯定有K个高个子，矮个子再插到前面，也满足K的要求
     *
     * [7,0], [4,4], [7,1], [5,0], [6,1], [5,2]  --> 排序前
     * [7,0], [7,1], [6,1], [5,0], [5,2], [4,4]  --> 排序后
     * 再一个一个插入。
     * [7,0]                                      // 将 [7, 0] 插入到 i = 0 处
     * [7,0], [7,1]                               // 将 [7, 1] 插入到 i = 1 处
     * [7,0], [6,1], [7,1]                        // 将 [6, 1] 插入到 i = 1 处，如果原来位置有数据了，会往后移（关键）
     * [5,0], [7,0], [6,1], [7,1]                 // 将 [5, 0] 插入到 i = 0 处
     * [5,0], [7,0], [5,2], [6,1], [7,1]         // 将 [5, 2] 插入到 i = 2 处
     * [5,0], [7,0], [5,2], [6,1], [4,4], [7,1]  // 将 [4, 4] 插入到 i = 4 处
     *
     * 时间复杂度：O(n^2)，其中 n 是数组 people 的长度。我们需要 O(nlogn) 的时间进行排序，随后需要 O(n^2) 的时间遍历每一个人并将他们放入队列中。
     * 空间复杂度：O(logn)，即为排序需要使用的栈空间。
     * 
     * https://leetcode-cn.com/problems/queue-reconstruction-by-height/solution/406-gen-ju-shen-gao-zhong-jian-dui-lie-b-99hr/
     * 
     * @param array
     * @return
     */

class Solution {
    fun reconstructQueue(array: Array<IntArray>): Array<IntArray>? {
        val res = LinkedList<IntArray>()

        // 1.排序规则：先按身高降序，若身高相同则按第二个数字升序
        Arrays.sort(array) { o1, o2 ->
            if (o1[0] != o2[0]) {
                // 按身高降序
                o2[0] - o1[0]
            } else {
                // 按第二个数字升序
                o1[1] - o2[1]
            }
        }

        // 2.遍历排序后的数组，第二个数字作为索引位置，把数组插入到目标索引位置上，如果原来位置有数据了，会往后移。
        for (i in array) {
            res.add(i[1], i)
        }

        return res.toArray(Array(res.size) { IntArray(2) })
    }

}