import java.util.*;

public class Main {
    public static void main(String[] args) {
        /*
        描述
        编写一个程序，将输入字符串中的字符按如下规则排序。
        规则 1 ：英文字母从 A 到 Z 排列，不区分大小写。
        如，输入： Type 输出： epTy

        规则 2 ：同一个英文字母的大小写同时存在时，按照输入顺序排列。
        如，输入： BabA 输出： aABb

        规则 3 ：非英文字母的其它字符保持原来的位置。
        如，输入： By?e 输出： Be?y
        */

        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String str = sc.nextLine();
            String result = mySort(str);
            System.out.println(result);
        }

    }

    private static String mySort(String str) {
        List<Character> arr = new ArrayList<>();
        for(char ch : str.toCharArray()){
            if(Character.isLetter(ch)){
                arr.add(ch);
            }
        }

        arr.sort(new Comparator<Character>() {
            public int compare(Character o1, Character o2) {
                return Character.toLowerCase(o1) - Character.toLowerCase(o2);
            }
        });

        StringBuilder result = new StringBuilder();
        int cnt = 0;
        for(int i = 0;i<str.length();i++){
            if(Character.isLetter(str.charAt(i))){
                result.append(arr.get(cnt++));
            }else{
                result.append(str.charAt(i));
            }
        }
        return result.toString();
    }
}