import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    /*
    给出一个字符串，该字符串仅由小写字母组成，定义这个字符串的“漂亮度”是其所有字母“漂亮度”的总和。
    每个字母都有一个“漂亮度”，范围在1到26之间。没有任何两个不同字母拥有相同的“漂亮度”。字母忽略大小写。

    给出多个字符串，计算每个字符串最大可能的“漂亮度”。

    本题含有多组数据。

    输入描述
    第一行一个整数N，接下来N行每行一个字符串

    输出描述：
    每个字符串可能的最大漂亮程度

    输入：
    2
    zhangsan
    lisi

    输出：
    192
    101

    说明：
    对于样例lisi，让i的漂亮度为26，l的漂亮度为25，
    s的漂亮度为24，lisi的漂亮度为25+26+24+26=101.
     */

        //使用table思想，由于题目没有给出字符串漂亮度限制，只需要找到重复最多的数给到最大值，然后对较少的数依次递减即可
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            for (int i = 0; i < n; i++) {
                String str = sc.next();
                int[] s = new int[26];
                for (int j = 0; j < str.length(); j++) {
                    s[str.charAt(j) - 'a']++;
                }
                //升序排序后 从最后一个往后开始，赋予最大值，依次递减
                Arrays.sort(s);
                int mul = 26, sum = 0;
                for (int j = s.length - 1; j >= 0 && s[j] > 0; j--) {
                    sum += s[j] * mul;
                    mul--;
                }
                System.out.println(sum);
            }

        }

    }
}