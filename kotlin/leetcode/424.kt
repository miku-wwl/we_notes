https://leetcode.cn/problems/longest-repeating-character-replacement/

class Solution {
 /**
     * 424. 替换后的最长重复字符
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     *
     * https://leetcode-cn.com/problems/longest-repeating-character-replacement/solution/424-ti-huan-hou-de-zui-chang-zhong-fu-zi-2p6l/
     * @param s
     * @param k
     * @return
     */
    fun characterReplacement(s: String, k: Int): Int {
        // 左指针
        var left = -1
        var res = 0

        // 窗口内相同元素的最大个数
        var maxSame = 0
        // 模拟 hashMap，很多场合都可以，大家可以总结一下
        val array = IntArray(26)

        for (i in 0 until s.length) {
            // 得出索引，ASCLL 码
            val c = s[i] - 'A'
            // 数目加1，要求出窗口内最多元素
            array[c]++
            // 把出现次数最多的字符当成主力，得出最大 maxSame
            maxSame = Math.max(maxSame, array[c])

            // 要替换次数大于 k 了，窗口超出范围了，缩小窗口左边
            while (i - left - maxSame > k) {
                val old = s[++left]
                array[old - 'A']--
            }

            // 到这里为止，(left, i]就是一个满足要求的区间
            res = Math.max(res, i - left)
        }

        return res
    }
}