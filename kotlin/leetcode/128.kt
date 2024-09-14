https://leetcode.cn/problems/longest-consecutive-sequence/submissions/564260265/

class Solution {
    fun longestConsecutive(nums: IntArray): Int {
        if (nums.isEmpty()) return 0

        var newNums = nums.toSet().toIntArray()
        newNums.sort();

        var maxLen = 1
        var ans = 1
        for (i in 0..<newNums.size - 1) {
            if (newNums[i] == newNums[i + 1] - 1) {
                ans++;
                maxLen = maxOf(maxLen, ans)
            } else {
                ans = 1
            }
        }
        return maxLen;
    }
}