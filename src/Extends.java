class Animal1 {
    protected String name;

    public Animal1(String name) {
        this.name = name;
    }

    public void makeSound() {
        System.out.println(name + "发出声音");
    }
}

class Dog1 extends Animal1 {
    public Dog1(String name) {
        super(name); // 调用父类构造器（必须在第一行）
    }
    //方法名、参数列表必须与父类完全相同
    @Override
    public void makeSound() {
    //  调 super.方法() = 在父类基础上扩展；不调 = 彻底换成自己的
        super.makeSound();
        System.out.println("汪汪汪");
        System.out.println("（摇尾巴）");
    }
}
class Cat1 extends Animal1{
    public Cat1(String name){
        super(name);
    }
    public void makeSound() {
        super.makeSound();  // 先执行父类逻辑
        System.out.println("喵喵喵！");
    }
    public void climb() {
        System.out.println("爬树");
    }
}
//编译看左边 运行看右边
class Test2{
    public static void main(String[] args){
        Dog1 d = new Dog1("小黑狗");
        d.makeSound();
        Cat1 c = new Cat1("小猫");
        if (c instanceof Cat1) c.climb();
    }
}