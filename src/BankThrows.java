// BankThrows：演示 throws 分层传播风格
// 和 BankDemo2（try/catch 风格）的区别：本例里 withdraw 自己不处理异常，
// 而是用 throws 声明“我把它抛给调用者”，由更上层决定怎么处理。
public class BankThrows {
    private String accountId;
    private String ownerName;
    private double balance;

    public BankThrows(String accountId, String ownerName) {
        this.accountId = accountId;
        this.ownerName = ownerName;
        this.balance = 0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("存款金额必须大于 0，本次操作被拒绝");
            return;
        }
        balance = balance + amount;
        System.out.println("存入 " + amount + "，当前余额 " + balance);
    }

    // 关键点 1：withdraw 自己不 try/catch，而是用 throws 把异常抛给调用者。
    // 因为 InsufficientBalanceException 是受检异常，这里 throws 是强制要写的。
    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            System.out.println("取款金额必须大于 0，本次操作被拒绝");
            return;
        }
        if (amount > balance) {
            throw new InsufficientBalanceException(amount - balance);
        }
        balance = balance - amount;
        System.out.println("取出 " + amount + "，当前余额 " + balance);
    }

    public double getBalance() {
        return balance;
    }

    public void printInfo() {
        System.out.println("账户：" + accountId + " 户主：" + ownerName + " 余额：" + balance);
    }

    // 关键点 2：转账方法调用了 withdraw，但也不 catch，继续 throws。
    // 异常会顺着 withdraw → transferTo 一路往上抛，直到有人 catch 为止。
    // 这就是 throws 的典型用法：中间层只管“声明会抛”，把处理责任交给上层。
    public void transferTo(BankThrows other, double amount) throws InsufficientBalanceException {
        this.withdraw(amount);          // 余额不足时，这里抛的异常会透传出去
        other.deposit(amount);
        System.out.println("转账 " + amount + " 完成");
    }

    // 受检异常：直接 extends Exception
    class InsufficientBalanceException extends Exception {
        private final double shortfall;

        public InsufficientBalanceException(double shortfall) {
            super("余额不足，还差 " + shortfall + " 元");
            this.shortfall = shortfall;
        }

        public double getShortfall() {
            return shortfall;
        }
    }

    public static void main(String[] args) {
        BankThrows a = new BankThrows("A001", "张三");
        BankThrows b = new BankThrows("B002", "李四");
        a.deposit(100);

        // 关键点 3：顶层（main）才是真正处理异常的地方。
        // 如果这里也不 catch、而是给 main 写 throws，异常就会一路抛到 JVM，
        // 程序直接以红色堆栈跟踪崩溃——所以“顶层 catch”是 throws 风格的收口处。
        try {
            a.transferTo(b, 200);   // 余额只有 70，转 200 → 抛异常，顺着 throws 到这里
        } catch (InsufficientBalanceException e) {
            System.out.println("转账失败：" + e.getMessage() + "（缺口 " + e.getShortfall() + " 元）");
        }
        a.printInfo();
        b.printInfo();
    }
}
