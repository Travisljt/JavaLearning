import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            String key = sc.next().toLowerCase();
            String s = sc.next();
            ArrayList<Character> list = new ArrayList<>();
            for(int i=0;i<key.length();i++){
                //如果list中不存在就放入
                if(!list.contains(key.charAt(i))){
                    list.add(key.charAt(i));
                }
            }
            //将A-Z放入到list中
            for(int i= 'a';i<='z';i++){
                char c = (char) i;
                if(!list.contains(c))
                    list.add(c);
            }
            Map<Character,Character> map = new HashMap<>();
            int begin = 'a';
            for (int i=0;i<list.size();i++) {
                map.put((char)(begin+i),list.get(i));
            }

            StringBuilder result = new StringBuilder();
            for (int i=0;i<s.length();i++){
                char c = s.charAt(i);
                Character character = map.get(c);
                if(s.charAt(i)>='a' && s.charAt(i)<='z'){
                    result.append(character);
                }else {
                    result.append(Character.toUpperCase(character));
                }
            }
            System.out.println(result);
        }
    }
}