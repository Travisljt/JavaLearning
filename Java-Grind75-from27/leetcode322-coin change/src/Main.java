import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int amount = 11;
        System.out.println(coinChange(coins, amount));
    }

    public static int coinChange(int[] coins, int amount) {
        int max = amount + 1;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, max);//将所有最大值填入dp
        dp[0] = 0;
        for (int i = 0; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);  //dp[i-coin]指减去coin后剩下金额的最优办法，此时需要加1，即加上coin这个硬币1个
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }
}