import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String s = "32";
        List<String> list = letterCombinations(s);
        for(String s1 : list){
            System.out.println(s1);
        }

    }

    static List<String> ans = new ArrayList<>();

    public static List<String> letterCombinations(String digits) {
        String[] table = {"abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};

       if(digits==null || digits.length()==0){
           return ans;
       }
       StringBuilder sb = new StringBuilder();
       dfs(digits,0,sb,table);
        return ans;
    }

    public static void dfs(String digits, int index, StringBuilder sb, String[] table){
        if(index==digits.length()){
            ans.add(sb.toString());
            return;
        }
        char c = digits.charAt(index);
        int pos = c - '2';
        String str = table[pos];
        for(int i=0;i<str.length();i++){
            sb.append(str.charAt(i));
            dfs(digits,index+1,sb,table);
            sb.deleteCharAt(sb.length()-1);
        }
    }
}