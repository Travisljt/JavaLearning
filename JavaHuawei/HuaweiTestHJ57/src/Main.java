import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*输入两个用字符串 str 表示的整数，求它们所表示的数之和。
        数据范围：  10000 \1≤len(str)≤10000

         */

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String s1 = sc.next();
            String s2 = sc.next();
            String res = myAdd(s1, s2);
            System.out.println(res);
        }
    }

    private static String myAdd(String s1, String s2) {
        StringBuilder sb = new StringBuilder();
        int n = s1.length() - 1;
        int m = s2.length() - 1;
        int carry = 0;
        while (n >= 0 || m >= 0) {
            //怕两者长度不一，如果s1大于s2的长度，s1高位需要通过补‘0’
            char c1 = n >= 0 ? s1.charAt(n--) : '0';
            char c2 = m >= 0 ? s2.charAt(m--) : '0';
            int sum = (c1 - '0') + (c2 - '0') + carry;
            sb.append(sum % 10);
            carry = sum / 10;
        }
        if (carry == 1) {
            sb.append(carry);
        }
        return sb.reverse().toString();
    }
}