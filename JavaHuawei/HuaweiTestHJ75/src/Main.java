import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    /*
    最长公共子串
    还是用dp

    注意，最长公共子串的最终答案并不一定在最后一个格子里，
    所以我们还需要一个变量 max 来记录最大值。
     */
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str1 = sc.next();
            String str2 = sc.next();
            char[] c1 = str1.toCharArray();
            char[] c2 = str2.toCharArray();

            int[][] dp = new int[c1.length + 1][c2.length + 1];
            //处理边界问题初始值，可以处理也可以不
            for (int i = 0; i <= c1.length; i++) {
                dp[i][0] = 0;
            }
            for (int j = 0; j <= c2.length; j++) {
                dp[0][j] = 0;
            }

            //定义一个结果来储存最大值
            int max = 0;

            for (int i = 1; i <= c1.length; i++) {
                for (int j = 1; j <= c2.length; j++) {
                    if (c1[i - 1] == c2[j - 1]) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    } else {
                        dp[i][j] = 0;
                    }
                    max = Math.max(max, dp[i][j]);
                }
            }
            System.out.println(max);
        }
    }
}