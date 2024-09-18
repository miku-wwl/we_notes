在 Java 中，要求两个 List 的交集、并集和补集，可以利用 Set 类型来方便地进行这些操作

java 练习

```java
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class useSet {
    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list2 = Arrays.asList(4, 5, 6, 7, 8);
        // 将 List 转换为 Set
        Set<Integer> set1 = new HashSet<>(list1);
        Set<Integer> set2 = new HashSet<>(list2);

        // 求交集
        Set<Integer> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        System.out.println("Intersection: " + intersection);

        // 求并集
        Set<Integer> union = new HashSet<>(set1);
        union.addAll(set2);
        System.out.println("Union: " + union);

        // 求补集，这里指的是相对于两者的并集来说的补集
        // 即两个集合中的元素，除了它们共有的那些
        Set<Integer> complement1 = new HashSet<>(union);
        complement1.removeAll(set2);
        System.out.println("Complement of set2 in union: " + complement1);

        Set<Integer> complement2 = new HashSet<>(union);
        complement2.removeAll(set1);
        System.out.println("Complement of set1 in union: " + complement2);

        // 如果你想要的是对称差集（即两个集合中的元素，除了它们共有的那些）
        Set<Integer> symmetricDifference = new HashSet<>(complement1);
        symmetricDifference.addAll(complement2);
        System.out.println("Symmetric Difference: " + symmetricDifference);
    }
}

```

kotlin 练习

```kotlin
fun main() {
    val list1 = listOf(1, 2, 3, 4, 5)
    val list2 = listOf(4, 5, 6, 7, 8)

    val list3 = list1.intersect(list2).toList()
    val list4 = list1.union(list2).toList()

    println(list3)
    println(list4)
}

```
