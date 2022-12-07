import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        /*
        问题描述：在计算机中，通配符一种特殊语法，广泛应用于文件搜索、数据库、正则表达式等领域。现要求各位实现字符串通配符的算法。
        要求：
        实现如下2个通配符：
        *：匹配0个或以上的字符（注：能被*和?匹配的字符仅由英文字母和数字0到9组成，下同）
        ？：匹配1个字符

        注意：匹配时不区分大小写。

        输入：
        通配符表达式；
        一组字符串。
        输出：
        返回不区分大小写的匹配结果，匹配成功输出true，匹配失败输出false

        示例1
        输入：
        te?t*.*
        txt12.xls
        输出：
        false

        示例5
        输入：
        ?*Bc*?
        abcd
        输出：
        true
         */

        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String value = sc.nextLine().toLowerCase();
            String target = sc.nextLine().toLowerCase();
            String regx = value.replaceAll("\\*{2,}", "\\*");
            regx = regx.replaceAll("\\?", "[0-9a-z]{1}");
            regx = regx.replaceAll("\\*", "[0-9a-z]{0,}");
            System.out.println(target.matches(regx));
        }
    }
}