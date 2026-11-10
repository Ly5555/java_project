/**
 * 银行账户系统（存款、取款、余额，用封装保护数据）
 * Day 2 改造：withdraw 余额不足时抛自定义异常，而不是把余额算成负数
 */
public class BankDemo {
    private String accountId;      // 账号
    private String ownerName;      // 户主姓名
    private double balance;        // 余额

    public BankDemo(String accountId, String ownerName) {
        this.accountId = accountId;
        this.ownerName = ownerName;
        this.balance = 0;
    }

    // 存钱
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("存款金额必须大于 0，本次操作被拒绝");
            return;                   // 提前结束方法，余额不变
        }
        balance = balance + amount;
        System.out.println("存入 " + amount + "，当前余额 " + balance);
    }

    // 查看金额
    public double getBalance() {
        return balance;               // 只读，不给外部修改的机会
    }

    // 取钱：余额不足时主动抛出自定义异常，而不是默默把余额算成负数
    // 非受检异常，方法签名上可写可不写 throws，这里省略
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("取款金额必须大于 0，本次操作被拒绝");
            return;                   // 负数也会被拦下，避免余额被反向加回去
        }
        if (amount > balance) {
            throw new InsufficientBalanceException(amount - balance);  // ← 主动抛出
        }
        balance = balance - amount;
        System.out.println("取出 " + amount + "，当前余额 " + balance);
    }

    public void printInfo() {
        System.out.println("账号=" + accountId + "，户主=" + ownerName + "，余额=" + balance);
    }
}

// 自定义异常：余额不足。继承 RuntimeException（非受检），调用处可处理也可不处理
class InsufficientBalanceException extends RuntimeException {
    private final double shortfall;   // 还差多少钱

    public InsufficientBalanceException(double shortfall) {
        super("余额不足，还差 " + shortfall + " 元");   // 提示交给父类存着，getMessage() 能取
        this.shortfall = shortfall;
    }

    public double getShortfall() {    // 调用者直接拿数字，不用解析字符串
        return shortfall;
    }
}

class BankDemoApp {
    public static void main(String[] args) {
        BankDemo account = new BankDemo("001", "小明");
        account.printInfo();

        account.deposit(100);
        System.out.println("余额=" + account.getBalance());
        System.out.println("=============================");

        // 正常取款
        account.withdraw(30);
        System.out.println("余额=" + account.getBalance());
        System.out.println("=============================");

        // 余额不足：异常会中断这一轮，余额保持不变
        try {
            account.withdraw(200);
        } catch (InsufficientBalanceException e) {
            System.out.println("取款失败：" + e.getMessage()
                    + "（还差 " + e.getShortfall() + " 元）");
        }
        System.out.println("余额=" + account.getBalance());   // 验证余额没被改
        System.out.println("=============================");

        account.deposit(-50);          // 非法金额，应被挡住
        System.out.println("余额=" + account.getBalance());
    }
}
