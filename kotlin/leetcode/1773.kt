https://leetcode.cn/problems/count-items-matching-a-rule/description/

class Solution {
    fun countMatches(items: List<List<String>>, ruleKey: String, ruleValue: String): Int {
        when (ruleKey) {
            "type" -> return items.count {
                it[0] == ruleValue
            }

            "color" -> return items.count {
                it[1] == ruleValue
            }

            "name" -> return items.count {
                it[2] == ruleValue
            }
        }
        return 0
    }
}