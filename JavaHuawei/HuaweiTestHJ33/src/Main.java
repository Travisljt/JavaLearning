import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

       /*
       原理：ip地址的每段可以看成是一个0-255的整数，把每段拆分成一个二进制形式组合起来，然后把这个二进制数转变成
        一个长整数。
        举例：一个ip地址为10.0.3.193
        每段数字             相对应的二进制数
        10                   00001010
        0                    00000000
        3                    00000011
        193                  11000001

        组合起来即为：00001010 00000000 00000011 11000001,转换为10进制数就是：167773121，即该IP地址转换后的数字就是它了。

        数据范围：保证输入的是合法的 IP 序列

        输入：
        10.0.3.193
        167969729

        输出：
        167773121
        10.3.3.193
        */

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String s = sc.next();
            if (s.contains(".")) {
                System.out.println(ip2num(s));
            } else {
                System.out.println(num2ip(Long.parseLong(s)));
            }
        }
    }

    //256意味着二进制左移8个字节
    private static String num2ip(long num) {
        String[] ans = new String[4];
        for (int i = 3; i >= 0; i--) {
            ans[i] = Long.toString(num % 256);
            num /= 256;
        }
        //将数组中间以"."连接
        return String.join(".", ans);
    }

    private static long ip2num(String ip) {
        String[] ips = ip.split("\\.");
        long ans = 0;
        for (int i = 0; i < 4; i++) {
            ans = ans * 256 + Long.parseLong(ips[i]);
        }
        return ans;
    }
}