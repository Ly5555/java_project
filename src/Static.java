public class Static {
    private static String schoolName = "阳光中学";  // 所有学生共享r v s
    private  String name;
    public static String getSchool() { return schoolName; }  // 静态方法
}
//加 static = 属于“类”而不是“对象”：整个程序只有一份，不 new 也能用；
// 不加 static = 属于“对象”，每个 new 出来的实例各持一份
class Test4{
    public static void main(String[] args){
        System.out.println(Static.getSchool());
    }
}
