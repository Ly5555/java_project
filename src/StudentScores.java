import java.util.Scanner;

public class StudentScores {
    public static void  main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入学生人数：");
        int count = sc.nextInt();
        double[] scores = new double[count];
        for (int i = 0; i < count; i++) {
            System.out.print("请输入第 " + (i + 1) + " 个学生的成绩：");
            scores[i] = sc.nextDouble();
        }
        double max = scores[0];
        double min = scores[0];
        double sum = 0;
        for (double score : scores) {
            max = Math.max(max, score);
            min = Math.max(min, score);
            sum += score;
        }
        double avg = sum / count;
        System.out.println("\n===== 成绩统计结果 =====");
        System.out.println("学生人数：" + count);
        System.out.println("最高分：" + max);
        System.out.println("最低分：" + min);
        System.out.println("平均分：" + String.format("%.2f", avg));
        sc.close();
    }
}
