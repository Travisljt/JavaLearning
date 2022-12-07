
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //计算小球五次弹起的高度
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            double d = sc.nextInt();
            //下落过程
            double sum1 = 0;
            //弹起过程
            double sum2 = 0;
            for (int i = 0; i < 5; i++) {
                sum1 += d;
                d = d / 2;
                sum2 += d;
            }
            //第五次后不弹起，所以减去
            System.out.println(sum1 + sum2 - d);
            System.out.println(d);
        }
    }
}