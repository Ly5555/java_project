public abstract class Abstract {
    protected String name;

    public Abstract(String name) {      // 构造器：无返回类型、名字=类名
        this.name = name;
    }
    public abstract void makeSound();   // ③ 抽象方法加 void

    public void sleep() {              // 具体方法
        System.out.println(name + "在睡觉");
    }
}

interface Flyable {
    void fly();  // 默认 public abstract
}

class Bird extends Abstract implements Flyable {
    public Bird(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println("叽叽喳喳");
    }

    @Override
    public void fly() {
        System.out.println(name + "在飞");
    }
}

class Test3 {
    public static void main(String[] args) {
        Abstract b = new Bird("小鸟");   // 抽象类不能 new，但能当父类引用指向 Bird
        b.makeSound();                    // 走 Bird 重写版（验证：抽象方法被实现）
        b.sleep();                        // sleep 父类有具体实现，Bird 没重写 → 直接用
        Flyable f = new Bird("小鸟");
        f.fly();
    }
}
