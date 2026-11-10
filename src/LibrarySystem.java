import java.util.*;

/**
 * 图书管理系统 —— 单文件版（三个类放在同一个 .java 里）
 *
 * 写法要点（和之前报错的 BookManagement.java 对照着看）：
 *   1. 三个类里【只有 LibrarySystem 加 public】，所以文件名必须叫 LibrarySystem.java
 *   2. Book 和 BookManager 不加 public —— 这叫"包内可见"，
 *      同一个文件、同一个包里互相调用完全没问题
 *   3. 类的先后顺序无所谓，谁在前谁在后都能互相引用
 *   4. 单文件写法的代价：类多了文件会很长，找代码全靠搜索，所以只适合练习和 demo
 *
 * 文件内顺序按"依赖从下到上"排：数据 → 业务 → 入口
 *
 * 【Day 3 改动】把 BookManager 内部存储从「对象数组 Book[] + 计数 count」换成「List<Book>」：
 *   - 自动扩容，没有 100 本上限，连续加 200 本都不崩
 *   - 删除用 removeIf，不会触发 for-each 里 remove 的并发修改异常
 */

// ============================================================
// 第 1 层 · 数据：Book —— 只描述"一本图书长什么样"
// ============================================================
class Book {
    private String bookId;      // 图书编号
    private String bookName;    // 书名
    private String author;      // 作者
    private boolean borrowed;   // 是否被借出

    public Book(String bookId, String bookName, String author) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.borrowed = false;  // 新书默认在馆
    }

    public String getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }

    // boolean 的 getter 习惯叫 isXxx()
    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // 借出状态用语义方法改，不直接暴露 boolean setter
    public void borrow() {
        this.borrowed = true;
    }

    public void giveBack() {
        this.borrowed = false;
    }

    public void showInfo() {
        System.out.println("编号=" + bookId + "，书名=" + bookName + "，作者=" + author
                + "，状态=" + (borrowed ? "已借出" : "在馆"));
    }
}

// ============================================================
// 第 2 层 · 业务：BookManager —— 管理一批图书，提供增查删借还
// ============================================================
class BookManager {
    // Day 5：用 Map 代替 List。key = 图书编号，value = 书对象
    // 编号集合就是 Map 的 keySet，无需再单独维护一个 Set 做查重
    private Map<String, Book> books = new HashMap<>();

    // 【增】新增图书
    public void addBook(Book book) {
        // 查重：用 Map 的 containsKey 判断编号是否已存在（key 就是编号）
        if (books.containsKey(book.getBookId())) {
            System.out.println("❌ 编号 " + book.getBookId() + " 已存在，无法重复添加");
            return;
        }
        // 没有 100 本上限了：put 自动扩容，连续加 200 本都不会崩
        books.put(book.getBookId(), book);
        System.out.println("✅ 添加图书成功");
    }

    // 【查】列出全部图书
    public void showAllBooks() {
        if (books.isEmpty()) {
            System.out.println("暂无图书！");
            return;
        }
        // 增强 for：不用管索引、不用管有效长度，List 自己知道有多少本
        for (Book b : books.values()) {   // ✅ 改成这样
            b.showInfo();
        }
    }

    // 【删】Day 3 新增：删除图书
    public void deleteBook(String bookId) {
        Book target = findBookById(bookId);
        if (target == null) {
            System.out.println("❌ 没有找到该图书！");
            return;
        }
        books.remove(bookId);
        System.out.println("✅ 删除成功：" + target.getBookName());
    }

    // 【借】借书
    public void borrowBook(String bookId) {
        Book target = findBookById(bookId);
        if (target == null) {
            System.out.println("❌ 没有找到该图书！");
            return;
        }
        if (target.isBorrowed()) {
            System.out.println("❌ 这本书已经被借出，无法重复借阅");
            return;
        }
        target.borrow();
        System.out.println("✅ 借书成功：" + target.getBookName());
    }

    // 【还】还书
    public void returnBook(String bookId) {
        Book target = findBookById(bookId);
        if (target == null) {
            System.out.println("❌ 没有找到该图书！");
            return;
        }
        if (!target.isBorrowed()) {
            System.out.println("❌ 这本书没有借出，无需归还");
            return;
        }
        target.giveBack();
        System.out.println("✅ 还书成功：" + target.getBookName());
    }

    // private 内部工具方法：借书、还书、删除都复用它
    private Book findBookById(String bookId) {
        return books.get(bookId);
    }
}

// ============================================================
// 第 3 层 · 入口：LibrarySystem —— 只负责和用户对话
// ============================================================
public class LibrarySystem {
    public static void main(String[] args) {
        BookManager manager = new BookManager();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== 图书管理系统 =====");
            System.out.println("1.新增图书");
            System.out.println("2.查看全部图书");
            System.out.println("3.借书");
            System.out.println("4.还书");
            System.out.println("5.删除图书");
            System.out.println("0.退出系统");
            System.out.print("请输入功能序号：");
            try {
                int op = sc.nextInt();
                sc.nextLine(); // 吸收换行，否则后面读字符串会读到空串
                switch (op) {
                    case 1:
                        System.out.print("输入图书编号：");
                        String id = sc.nextLine();
                        System.out.print("输入书名：");
                        String name = sc.nextLine();
                        System.out.print("输入作者：");
                        String author = sc.nextLine();
                        Book b = new Book(id, name, author);   // 依赖 Book
                        manager.addBook(b);                    // 依赖 BookManager
                        break;
                    case 2:
                        manager.showAllBooks();
                        break;
                    case 3:
                        System.out.print("输入要借阅的图书编号：");
                        String borrowId = sc.nextLine();
                        manager.borrowBook(borrowId);
                        break;
                    case 4:
                        System.out.print("输入要归还的图书编号：");
                        String returnId = sc.nextLine();
                        manager.returnBook(returnId);
                        break;
                    case 5:
                        System.out.print("输入要删除的图书编号：");
                        String deleteId = sc.nextLine();
                        manager.deleteBook(deleteId);
                        break;
                    case 0:
                        System.out.println("退出系统，再见！");
                        sc.close();
                        return;
                    default:
                        System.out.println("输入错误，请重新选择！");
                }
            } catch (InputMismatchException e) {
                System.out.println("输入错误，请输入数字！");
                sc.nextLine();           // ← 关键：把这一行非法输入吃掉
            } finally {
                System.out.println("—— 本轮输入处理结束 ——");   // 每轮都会打印
            }
        }
    }
}
