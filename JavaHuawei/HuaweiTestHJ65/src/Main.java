import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        查找两个字符串a,b中的最长公共子串。若有多个，输出在较短串中最先出现的那个。
        注：子串的定义：将一个字符串删去前缀和后缀（也可以不删）形成的字符串。请和“子序列”的概念分开！

        输入描述：
        输入两个字符串

        输出描述：
        返回重复出现的字符

        使用dp思想
        算法流程：

        维护动态数组dp[i][j]表示在较短字符串str1以第i个字符结尾，str2中以第j个字符结尾时的公共子串长度。
        从左向右递推，i为短字符串str1的结尾索引，j为str2的结尾索引
        相等则计数，并不断更新最长公共子串的长度和结尾索引
         */
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String s1 = sc.nextLine();
            String s2 = sc.nextLine();
            System.out.println(longString(s1, s2));
        }
    }

    private static String longString(String str1, String str2) {
        String temp;
        //保证str1是较短的字符串
        if (str1.length() > str2.length()) {
            temp = str1;
            str1 = str2;
            str2 = temp;
        }
        int m = str1.length() + 1;
        int n = str2.length() + 1;
        //表示在较短字符串str1以第i个字符结尾，str2中以第j个字符结尾时的公共子串长度
        int[][] dp = new int[m][n];
        //匹配字符，并记录最大值的str1的结尾下标
        int max = 0;
        int index = 0;
        //从左往右递推，i为短字符串str1的结尾索引，j为str2的结尾索引
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                //i-1和j-1是因为最开始维护dp的时候，dp[0][0]的横排跟竖排都不具有有效值
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    //相等则计数
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    //不断更新变量
                    if (dp[i][j] > max) {
                        max = dp[i][j];
                        //index表示当前最大值位置时的结尾下标，通过index-max可以知道起始下标
                        index = i;
                    }
                }
            }
        }

        return str1.substring(index - max, index);
    }
}