//只定义 不执行
public class Student {
    // 属性（成员变量）——描述"这个对象有什么"
    String name;
    int age;
    String brand;
    // 方法（行为）——描述"这个对象能做什么"
    void study() {
        System.out.println(name + "正在学习");
        System.out.println(age + "多岁了");
    };
}

// 真正存数据的地方
// 注意：一个 .java 文件只能有一个 public 类（必须和文件名同名）。
// 这个 Main 类不加 public，就可以和 Student 共存于 Student.java 中。
class Main {
    public static void main(String[] args) {
//        真正存数据的地方
        Student s = new Student();  // 创建对象：类是图纸，对象是按图纸造出来的实物
        s.name = "小明";            // 给对象的属性赋值
        s.age = 20;
        s.study();                  // 调用对象的方法
    }
}
