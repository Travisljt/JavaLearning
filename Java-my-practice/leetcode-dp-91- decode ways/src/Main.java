import java.util.concurrent.locks.ReadWriteLock;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public static int numDecoding(String s){
        int len = s.length();
        s = " " + s;
        char[] arr = s.toCharArray();

        int[] dp = new int[len +1];
        dp[0] = 1;
        //状态转移：1. 单独看一个单字，可以成为一个item，则dp[i] = dp[i - 1]  1<= a <= 9
        //2. 单独看前一个和这个单字，可以成为一个item,则dp[i] = dp[i-1]+dp[i-2] 10<=b<=26
        for(int i=1;i<=len;i++){
            int a = arr[i]-'0', b = (arr[i-1]-'0') * 10 + (arr[i]-'0');
            if(1<=a && a<= 9){
                dp[i] = dp[i-1];
            }
            if(10<=b && b<=26){
                dp[i] += dp[i-2];
            }
        }
        return dp[len];

    }
}