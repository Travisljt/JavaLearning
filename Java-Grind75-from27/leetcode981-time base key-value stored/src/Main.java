public class Main {
    public static void main(String[] args) {
//        TimeMap timeMap = new TimeMap();
//        timeMap.set("love","high",10);
//        timeMap.set("love","low",20);
//        System.out.println(timeMap.get("love", 15));
        int[] orders = {10, 20, 30, 40, 50};
        int diff = partitionOrders(orders);
        System.out.println(diff);

    }

    public static int partitionOrders(int[] orders) {
        int n = orders.length;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += orders[i];
        }
        int[][] dp = new int[n+1][sum/2+1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum/2; j++) {
                if (j >= orders[i-1]) {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-orders[i-1]] + orders[i-1]);
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        return sum - 2 * dp[n][sum/2];
    }

}