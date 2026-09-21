import java.util.Scanner;

public class stage1 {
    public static double main(String[] args) {
        // ===== 变量与数据类型 练习 =====
//        - 变量与数据类型（`int` `long` `double` `boolean` `String` 等）
//        - 运算符（算术、比较、逻辑、三目）
//        - 流程控制（`if/else`、`switch`、`for`、`while`）
//        - 数组（一维、二维）
//        - 方法（定义、参数、返回值、重载）
//        - 常用 API：`String`、`Math`、`Scanner`（键盘输入）
//        1. 计算器（控制台输入两个数和运算符，输出结果）
//        2. 猜数字游戏（程序随机生成，用户猜，提示太大/太小）
//        3. 学生成绩管理（用数组存成绩，求最高/最低/平均分）
//        // 1. 整数类型
            Scanner sc = new Scanner(System.in);
            System.out.print("请输入第一个数：");
            double num1 = sc.nextDouble();
            System.out.print("请输入运算符（+ - * /）：");
            String operator = sc.next();
            System.out.print("请输入第二个数：");
            double num2 = sc.nextDouble();
            double result;
            switch (operator){
                case "+":
                    return  num1 + num2;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    result = num1 / num2;
                    break;
                default:
                    System.out.println("错误：不支持的运算符！");
                    sc.close();
                    return num1;
            }

        System.out.println(num1 + " " + operator + " " + num2 + " = " + result);
        sc.close();
        return num1;
    }
}

