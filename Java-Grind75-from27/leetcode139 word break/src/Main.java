import java.util.*;

public class Main {
    public static void main(String[] args) {
            
    }


    public static boolean wordBreak(String s, List<String> wordDict) {
        //动态规划 优化版本
//        int len = s.length();
//        boolean[] dp = new boolean[len+1];
//        dp[0] = true;
//        for(int i=0;i<=len;i++){
//            if(!dp[i]){
//                continue;
//            }
//            for(String word : wordDict){
//                if(word.length()+i <= s.length() && s.startsWith(word,i)){
//                    dp[i+word.length()] =true;
//                }
//            }
//        }
//        return  dp[len];

        //动态规划 官方版本
        Set<String> wordDictSet = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];

    }
}

