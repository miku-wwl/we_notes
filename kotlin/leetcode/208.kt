https://leetcode.cn/problems/implement-trie-prefix-tree/

class Trie() {

    /** Initialize your data structure here. */
    private val trie = mutableSetOf<String>()

    /** Inserts a word into the trie. */
    fun insert(word: String) {
        trie.add(word)
    }

    /** Returns if the word is in the trie. */
    fun search(word: String): Boolean {
        return trie.contains(word)
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    fun startsWith(prefix: String): Boolean {
        return trie.count {
            it.startsWith(prefix)
        } > 0
    }

}

/**
 * Your Trie object will be instantiated and called as such:
 * var obj = Trie()
 * obj.insert(word)
 * var param_2 = obj.search(word)
 * var param_3 = obj.startsWith(prefix)
 */

/**
 * Your Trie object will be instantiated and called as such:
 * var obj = Trie()
 * obj.insert(word)
 * var param_2 = obj.search(word)
 * var param_3 = obj.startsWith(prefix)
 */