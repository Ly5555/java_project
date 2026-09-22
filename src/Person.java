//private 是最严的访问权限：出了 Person 这个类，
// 谁都碰不到 name、age。比如你在外面写 p.age = -5
// 会直接编译报错——这就是“外部无法直接访问”的意思。
public class Person {
    private String name;   // 私有属性，外部无法直接访问
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.setAge(age);  // 通过 setter 校验
    }

    // Getter
    public String getName() { return name; }

    // Setter（可加入校验逻辑）
    public void setAge(int age) {
        if (age > 0 && age < 150) {
            this.age = age;
        } else {
            System.out.println("年龄不合法");
        }
    }
    public int getAge() { return age; }
}
class test1 {
    public static void main(String[] args){
        Person p1 = new Person("张三", 25);
        System.out.println(p1.getName() + " / " + p1.getAge());

        Person p2 = new Person("李四", 200);   // 年龄非法
        System.out.println(p2.getName() + " / " + p2.getAge());
        p1.setAge(30);                          // 通过 setter 改
        System.out.println(p1.getAge());
    }
}
