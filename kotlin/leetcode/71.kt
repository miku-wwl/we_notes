https://leetcode.cn/problems/simplify-path/description/

class Solution {
    fun simplifyPath(path: String): String {
        val paths = path.split("/")
        val router = mutableListOf<String>()
        paths.forEach {
            when (it) {
                "" -> {}
                "." -> {}
                ".." -> {
                    if (router.isNotEmpty()) {
                        router.removeLast()
                    }
                }

                else -> {
                    router.add(it)
                }
            }
        }
        when {
            router.isEmpty() -> return "/"
            router.isNotEmpty() -> {
                var ans = ""
                router.forEach {
                    ans = "$ans/$it"
                }
                return ans
            }
        }
        return "/"
    }
}