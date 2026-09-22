public class PhoneDemo {
    public static void main(String[] args) {
        // 1. 调用无参构造器 Phone()  -> brand = "未知品牌", price = 0
        Phone p1 = new Phone();
        System.out.println("p1 = " + p1);

        // 2. 调用带参构造器 Phone(String, int)
        Phone p2 = new Phone("Apple", 7999);
        System.out.println("p2 = " + p2);

        // 3. 调用单个参数的构造器 Phone(String)，内部会 this(brand, 0)
        Phone p3 = new Phone("Xiaomi");
        System.out.println("p3 = " + p3);

        // 访问成员变量（brand / price 是默认包级权限，同包下可直接读写）
        System.out.println("p2 的品牌是: " + p2.brand);
        System.out.println("p2 的价格是: " + p2.price);

        p1.brand = "Huawei";   // 也可以手动修改
        System.out.println("修改后的 p1 = " + p1);
    }
}
