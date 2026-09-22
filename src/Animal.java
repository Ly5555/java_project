 class Animal {
    protected String name;
    public Animal(String name){
        this.name = name;
    }
    public void makeSound() {
        System.out.println(name + "发出声音");
    }
}

class Dog extends Animal{
    public Dog(String name) {
        super(name);  // 调用父类构造器（必须在第一行）
    }

    @Override
    public void makeSound() {
        super.makeSound();
        System.out.println("汪汪汪！");

    }
}
class Cat extends Animal{
     public Cat(String name) {
         super(name);  // 调用父类构造器（必须在第一行）
     }

     @Override
     public void makeSound() {
         super.makeSound();
         System.out.println("喵喵喵！");

     }
 }

 class Test5{
    public static void main(String[] args){
        Animal d = new Dog("小狗");
        d.makeSound();
        Animal c = new Cat("小猫");
        c.makeSound();
    }
 }
