import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        输入一个字符串，返回其最长的数字子串，以及其长度。若有多个最长的数字子串，
        则将它们全部输出（按原字符串的相对位置）
        本题含有多组样例输入。

        输入描述：
        输入一个字符串。1<=len(字符串)<=200

        输出描述：
        输出字符串中最长的数字字符串和它的长度，中间用逗号间隔。
        如果有相同长度的串，则要一块儿输出（中间不要输出空格）。

        输入：
        abcd12345ed125ss123058789
        a8a72a6a5yy98y65ee1r2

        输出：
        123058789,9
        729865,2

        说明：
        样例一最长的数字子串为123058789，长度为9
        样例二最长的数字子串有72,98,65，长度都为2
         */
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] ss = line.split("[^0-9]+");
            int max = 0;
            ArrayList<String> list = new ArrayList<>();
            for (String s : ss) {
                if (s.length() > max) {
                    max = s.length();
                    list.clear();
                    list.add(s);
                } else if (s.length() == max) {
                    list.add(s);
                }
            }
            System.out.println(String.join("", list) + "," + max);
        }
    }
}