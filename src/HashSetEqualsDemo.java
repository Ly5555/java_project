import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Day 4 反例演示：HashSet 的去重到底看什么？
 *
 * 核心结论：HashSet 判断"是不是同一本"分两步 ——
 *   1. 先算 hashCode 找桶（bucket），不同桶直接当成不同元素，根本不会去比 equals；
 *   2. 只有在同一个桶里，才会调用 equals 比较。
 * 所以 equals 和 hashCode 必须【一起重写】，只重写其中一个没用。
 */
public class HashSetEqualsDemo {
    public static void main(String[] args) {

        // ===== 情况 1：完全不重写 equals / hashCode =====
        // 用的是 Object 默认实现：hashCode 按内存地址算（两个新对象地址不同），equals 比"是不是同一个对象"
        System.out.println("=== 情况1：Book 完全不重写 equals/hashCode ===");
        Set<BookNoOverride> s1 = new HashSet<>();
        s1.add(new BookNoOverride("B001", "Java"));
        s1.add(new BookNoOverride("B001", "Java"));   // 编号、书名都一样，但是两个 new 出来的对象
        System.out.println("size = " + s1.size() + "  → 预期 2：两个对象地址不同，被当成两本\n");

        // ===== 情况 2：只重写 equals，不重写 hashCode（经典坑）=====
        // 两个对象 hashCode 还是按地址算 → 落进不同的桶 → equals 根本没机会被调用
        System.out.println("=== 情况2：只重写 equals、不重写 hashCode ===");
        Set<BookEqualsOnly> s2 = new HashSet<>();
        s2.add(new BookEqualsOnly("B001", "Java"));
        s2.add(new BookEqualsOnly("B001", "Java"));
        System.out.println("size = " + s2.size() + "  → 还是 2！不同桶，equals 没被调用，所以「只重写 equals = 白写」\n");

        // ===== 情况 3：equals + hashCode 一起重写 =====
        // 编号相同 → hashCode 相同（落同一桶）→ equals 比较 → 相等 → 第二次 add 被忽略
        System.out.println("=== 情况3：equals + hashCode 一起重写 ===");
        Set<BookFull> s3 = new HashSet<>();
        s3.add(new BookFull("B001", "Java"));
        s3.add(new BookFull("B001", "Java"));
        System.out.println("size = " + s3.size() + "  → 预期 1：编号相同即视为同一本，自动去重\n");

        System.out.println("记住：HashSet 去重 = 同 hashCode 进同桶 + 同桶内 equals 相等，两者缺一不可。");
    }
}

// 情况1：啥都不重写，用 Object 默认的
class BookNoOverride {
    String id;
    String name;
    BookNoOverride(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

// 情况2：只重写 equals（故意不写 hashCode）
class BookEqualsOnly {
    String id;
    String name;
    BookEqualsOnly(String id, String name) {
        this.id = id;
        this.name = name;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookEqualsOnly)) return false;
        return Objects.equals(id, ((BookEqualsOnly) o).id);  // 编号相同就算相等
    }
    // ⚠️ 没有 @Override hashCode() —— 这是故意的，用来演示坑
}

// 情况3：equals 和 hashCode 一起重写
class BookFull {
    String id;
    String name;
    BookFull(String id, String name) {
        this.id = id;
        this.name = name;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookFull)) return false;
        return Objects.equals(id, ((BookFull) o).id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);   // 编号相同 → 哈希值相同 → 落进同一个桶
    }
}
