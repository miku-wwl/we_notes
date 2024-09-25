https://leetcode.cn/problems/wildcard-matching/description/

class Solution {
    val hashMap = HashMap<String, Boolean>()
    fun isMatch(s: String, p: String): Boolean {
        //为了节省空间，不使用二维数组，使用一个数组缓存上一轮的比较结果
        val array = BooleanArray(p.length + 1)
        //记录上一轮比较的当前i和j的前一个点的值即使用二维数组dp[i - 1][j - 1]的值
        var prev = false
        //记录上一轮的当前j点的值即使用二维数组dp[i - 1][j]的值
        var temp = false
        //0表示s为空
        for (i in 0..s.length) {
            //新一轮开始默认赋值false
            prev = false
            //0表示p为空
            for (j in 0..p.length) {
                //记录上一轮的j点的值，即使用二维数组dp[i - 1][j]的值
                temp = array[j]
                //当i，j都为0表示两个空串属于true
                if (i == 0 && j == 0) {
                    array[j] = true
                    //当i=0,s是空串此时如果j位置属于*则看他前面的结果，前面如果一直都是*则为true
                    //（如***，j=2时看他前面的结果，如果是**f*，当j是最后一个*时前面有f不匹配得到false）
                }else if (i == 0 && j > 0 && p[j - 1] == '*') {
                    array[j] = array[j - 1]
                    //匹配字符串是否相等或者j位置为？，匹配到则看前一个的比较结果
                } else if (i > 0 && j > 0 && (s[i - 1] == p[j - 1] || p[j - 1] == '?')) {
                    array[j] = prev
                    //如果当前j位置是*，则如果*不参与比较（*可以匹配任何，此时当做啥都不做）则看前一个比较结果
                    //array[j - 1]，如果参与比较则看上一轮比较即i - 1那一轮的结果array[j]
                } else if (i > 0 && j > 0 && p[j - 1] == '*'){
                    array[j] = array[j] || array[j - 1] 
                    //其他情况则为不匹配直接为false
                } else {
                    array[j] = false
                }
                //把上一轮的j点的值，即使用二维数组dp[i - 1][j]的值赋值到prev，当前循环下一次使用时
                //相当于下一个节点使用二维数组dp[i - 1][j - 1]的值
                prev = temp
            }
        }
        return array[p.length]
    }
}