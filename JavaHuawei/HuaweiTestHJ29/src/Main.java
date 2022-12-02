import java.util.*;

public class Main {
    public static void main(String[] args) {
        /*
        描述
        对输入的字符串进行加解密，并输出。

        加密方法为：
        当内容是英文字母时则用该英文字母的后一个字母替换，同时字母变换大小写,如字母a时则替换为B；字母Z时则替换为a；
        当内容是数字时则把该数字加1，如0替换1，1替换2，9替换0；
        其他字符不做变化。

        解密方法为加密的逆过程。
        数据范围：输入的两个字符串长度满足 1 \le n \le 1000 \1≤n≤1000  ，保证输入的字符串都是只由大小写字母或者数字组成
        输入描述：
        第一行输入一串要加密的密码
        第二行输入一串加过密的密码

        输出描述：
        第一行输出加密后的字符
        第二行输出解密后的字符

        输入：
        abcdefg
        BCDEFGH

        输出：
        BCDEFGH
        abcdefg
         */
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            System.out.println(myEncode(sc.nextLine()));
            System.out.println(myDecode(sc.nextLine()));
        }
    }

    private static String myEncode(String code) {
        char[] codes = code.toCharArray();
        for (int i = 0; i < codes.length; i++) {
            //注意分界
            if (codes[i] >= 'a' && codes[i] < 'z') {
                codes[i] = (char) (codes[i] - 'a' + 'A' + 1);
            } else if (codes[i] == 'z') {
                codes[i] = 'A';
            } else if (codes[i] == 'Z') {
                codes[i] = 'a';
            } else if (codes[i] >= 'A' && codes[i] < 'Z') {
                codes[i] = (char) (codes[i] - 'A' + 'a' + 1);
            } else if (codes[i] >= '0' && codes[i] < '9') {
                codes[i] = (char) (codes[i] + 1);
            } else if (codes[i] == '9') {
                codes[i] = '0';
            }
        }
        return String.valueOf(codes);
    }

    private static String myDecode(String password) {
        char[] t = password.toCharArray();
        for (int i = 0; i < t.length; i++) {
            if (t[i] > 'a' && t[i] <= 'z')
                t[i] = (char) (t[i] - 'a' + 'A' - 1);
            else if (t[i] == 'a')
                t[i] = 'Z';
            else if (t[i] > 'A' && t[i] <= 'Z')
                t[i] = (char) (t[i] - 'A' + 'a' - 1);
            else if (t[i] == 'A')
                t[i] = 'z';
            else if (t[i] > '0' && t[i] <= '9')
                t[i] = (char) (t[i] - 1);
            else if (t[i] == '0')
                t[i] = '9';
        }
        return String.valueOf(t);
    }


}