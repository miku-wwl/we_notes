https://leetcode.cn/problems/remove-k-digits/description/

class Solution {
    fun removeKdigits(num: String, k: Int): String {
        var stack = LinkedList<Char>()
        var size = num.length
        var targetSize = size - k
        for(i in num.indices){
            while(!stack.isEmpty() && num[i] < stack.peek() && stack.size + (size - i) > targetSize){
               stack.pop()
            }
            if(stack.size < targetSize){
                stack.push(num[i])
            }
        }
        var builder = StringBuilder()
        while(!stack.isEmpty()){
            builder.insert(0,stack.pop())
        }
        while(!builder.isEmpty()&& builder[0] == '0'){
            builder.deleteCharAt(0)
        }
        if(builder.isEmpty()){
            builder.append('0')
        }
        return builder.toString()
    }
}