import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //开发一个简单错误记录功能小模块，能够记录出错的代码所在的文件名称和行号。
        //
        //处理：
        //1、 记录最多8条错误记录，循环记录，最后只用输出最后出现的八条错误记录。对相同的错误记录只记录一条，但是错误计数增加。最后一个斜杠后面的带后缀名的部分（保留最后16位）和行号完全匹配的记录才做算是“相同”的错误记录。
        //2、 超过16个字符的文件名称，只记录文件的最后有效16个字符；
        //3、 输入的文件可能带路径，记录文件名称不能带路径。也就是说，哪怕不同路径下的文件，如果它们的名字的后16个字符相同，也被视为相同的错误记录
        //4、循环记录时，只以第一次出现的顺序为准，后面重复的不会更新它的出现时间，仍以第一次为准

        //示例输入
        /*
        D:\zwtymj\xccb\ljj\cqzlyaszjvlsjmkwoqijggmybr 645
        E:\je\rzuwnjvnuz 633
        C:\km\tgjwpb\gy\atl 637
        F:\weioj\hadd\connsh\rwyfvzsopsuiqjnr 647
        E:\ns\mfwj\wqkoki\eez 648
        D:\cfmwafhhgeyawnool 649
        E:\czt\opwip\osnll\c 637
        G:\nt\f 633
        F:\fop\ywzqaop 631
        F:\yay\jc\ywzqaop 631
        D:\zwtymj\xccb\ljj\cqzlyaszjvlsjmkwoqijggmybr 645
        */

        //输出
        /*
        rzuwnjvnuz 633 1
        atl 637 1
        rwyfvzsopsuiqjnr 647 1
        eez 648 1
        fmwafhhgeyawnool 649 1
        c 637 1
        f 633 1
        ywzqaop 631 2
         */

        Scanner sc = new Scanner(System.in);
        HashMap<String, Integer> map = new LinkedHashMap<>();
        while (sc.hasNext()) {
            String str = sc.next();
            int lineNum = sc.nextInt();
            String[] strS = str.split("\\\\");
            //取最后一个文件名
            String fileName = strS[strS.length - 1];
            if (fileName.length() > 16) {
                fileName = fileName.substring(fileName.length() - 16);
            }
            //储存文件名跟行数，行数不重复也可以储存
            String key = fileName + " " + lineNum;
            int num = 1;
            if (map.containsKey(key)) {
                //相同文件名直接加1
                map.put(key, map.get(key) + 1);
            } else {
                map.put(key, num);
            }
        }

        int count = 0;
        for (String s : map.keySet()) {
            count++;
            //只输出后8个
            if (count > (map.keySet().size() - 8)) {
                System.out.println(s + " " + map.get(s));
            }
        }
    }
}