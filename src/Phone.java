public class Phone {
    String brand;
    int price;

    // 无参构造器
    public Phone() {
        this.brand = "未知品牌";
    }

    // 带参构造器
    public Phone(String brand, int price) {
        this.brand = brand;   // this 区分成员变量和局部变量
        this.price = price;
    }

    // 构造器重载：this() 调用本类其他构造器（必须在第一行）
    public Phone(String brand) {
        this(brand, 0);
    }
}
