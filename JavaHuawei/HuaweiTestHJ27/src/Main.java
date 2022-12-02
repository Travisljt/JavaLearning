import org.w3c.dom.ls.LSInput;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        /*
        描述
        定义一个单词的“兄弟单词”为：交换该单词字母顺序（注：可以交换任意次），而不添加、删除、修改原有的字母就能生成的单词。
        兄弟单词要求和原来的单词不同。例如： ab 和 ba 是兄弟单词。 ab 和 ab 则不是兄弟单词。
        现在给定你 n 个单词，另外再给你一个单词 x ，让你寻找 x 的兄弟单词里，按字典序排列后的第 k 个单词是什么？
        注意：字典中可能有重复单词。

        数据范围：1 \le n \le 1000 \1≤n≤1000 ，输入的字符串长度满足 1 \le len(str) \le 10 \1≤len(str)≤10  ， 1 \le k < n \1≤k<n
        输入描述：
        输入只有一行。 先输入字典中单词的个数n，再输入n个单词作为字典单词。 然后输入一个单词x 最后后输入一个整数k
        输出描述：
        第一行输出查找到x的兄弟单词的个数m 第二行输出查找到的按照字典顺序排序后的第k个兄弟单词，没有符合第k个的话则不用输出。
        示例1
        输入：
        3 abc bca cab abc 1

        输出：
        2
        bca
         */

        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            String[] str = sc.nextLine().split(" ");
            int total = Integer.parseInt(str[0]);
            String target = str[str.length -2];
            int point = Integer.parseInt(str[str.length-1]);
            List<String> list = new ArrayList<>();

            for(int i = 1; i<=total;i++){
                if(isBrother(target,str[i])){
                    list.add(str[i]);
                }
            }
            System.out.println(list.size());
            if(list.size()>=point){
                Collections.sort(list);
                System.out.println(list.get(point-1));
            }
        }
    }

    private static boolean isBrother(String target, String s) {
        if(target.length()!= s.length() || target.equals(s)){
            return false;
        }
        char[] targetList = target.toCharArray();
        char[] sList = s.toCharArray();
        Arrays.sort(targetList);
        Arrays.sort(sList);
        return new String(targetList).equals(new String(sList));
    }
}