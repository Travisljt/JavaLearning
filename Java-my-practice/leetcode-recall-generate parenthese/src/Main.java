import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int n = 3;
        List<String> list = generateParenthesis(3);
        for(String s : list){
            System.out.println(s);
        }
    }

    //题意是解有多少个有效括号的例子
    public static List<String> generateParenthesis(int n){
        List<String> ans = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        if(n==0){
            return ans;
        }
        dfs(ans,sb,0,0,n);
        return ans;
    }

    public static void dfs(List<String> ans, StringBuilder sb, int open, int close, int max){
        if(sb.length()==max*2){
            ans.add(sb.toString());
            return;
        }
        if(open<max){
            sb.append("(");
            dfs(ans,sb,open+1,close,max);
            sb.deleteCharAt(sb.length()-1);
        }
        if(close<open){
            sb.append(")");
            dfs(ans,sb,open,close+1,max);
            sb.deleteCharAt(sb.length()-1);
        }
    }
}