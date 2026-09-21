import java.util.Scanner;

public class GuessNumber {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int target = (int) (Math.random()*100) + 1;
        System.out.println(target);
        int guess;
        int count = 0;
        System.out.println("=== 猜数字游戏 ===");
        System.out.println("我已经想好了一个 1~100 之间的整数，来猜猜看吧！");
        do{
            System.out.print("请输入你猜的数字：");
            guess = sc.nextInt();
            count++;
            if (guess > target){
                System.out.println("太大了，再小一点！");
            }else if(guess < target) {
                System.out.println("太小了，再大一点！");
            }else{
                System.out.println("🎉 恭喜你猜对了！答案就是 " + target);
                System.out.println("你一共猜了 " + count + " 次");
            }
        }while (guess != target);
        sc.close();
    }
}
