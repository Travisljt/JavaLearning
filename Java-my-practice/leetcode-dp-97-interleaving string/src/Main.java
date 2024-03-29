import java.util.*;

public class Main {

    public static void main(String[] args) {
    }


    public static boolean isInterleave(String s1,String s2, String s3){
        int m  =s1.length(), n = s2.length();
        if(s3.length()!=(m+n))return false;
        boolean[][] dp = new boolean[m+1][n+1];
        dp[0][0] = true;
        for(int i=1;i<=m && s1.charAt(i-1)==s3.charAt(i-1);i++){
            dp[i][0] = true;
        }
        for(int j =1;j<=n && s2.charAt(j-1)==s3.charAt(j-1);j++){
            dp[0][j] = true;
        }
        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                dp[i][j] = dp[i][j-1] && s3.charAt(j+i-1)==s2.charAt(j-1) || dp[i-1][j] && s3.charAt(j+i-1)==s1.charAt(i-1);
            }
        }
        return dp[m][n];
    }
}