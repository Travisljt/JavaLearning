import java.util.*;

public class Main {
    public static void main(String[] args) {
       /*
        输出 1到n之间 的与 7 有关数字的个数。
        一个数与7有关是指这个数是 7 的倍数，或者是包含 7 的数字（如 17 ，27 ，37 ... 70 ，71 ，72 ，73...）

        输入描述：
        一个正整数 n 。( n 不大于 30000 )

        输出描述：
        一个整数，表示1到n之间的与7有关的数字个数。

        */

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int sum = 0;
            for (int i = 1; i <= n; i++) {
                if (i % 7 == 0) {
                    sum++;
                } else {
                    String s = String.valueOf(i);
                    if (s.contains("7")) {
                        sum++;
                    }
                }
            }
            System.out.println(sum);
        }
    }
}