import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[][] arr = {{0,0,0},{0,1,0},{0,0,0}};
        System.out.println(uniquePaths(3,7));
        System.out.println(uniquePathsWithObstacles(arr));
    }

    public static int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        Arrays.fill(dp[0],1);
        for(int i=0;i<m;i++){
            dp[i][0] = 1;
        }
        for(int i = 1;i<m;i++){
            for(int j = 1;j<n;j++){
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }

    public static int uniquePathsWithObstacles(int[][] obstacleGrid){
        int row = obstacleGrid.length;
        int col = obstacleGrid[0].length;
        int[][] dp = new int[row][col];
        for(int i=0;i<col;i++){
            if(obstacleGrid[0][i]==1)break;
            dp[0][i] = 1;
        }
        for(int i=0;i<row;i++){
            if(obstacleGrid[i][0]==1)break;
            dp[i][0] = 1;
        }
        for(int i=1;i<row;i++){
            for(int j=1;j<col;j++){
                if(obstacleGrid[i][j]==1){
                    continue;
                }
                if(obstacleGrid[i-1][j]==1 && obstacleGrid[i][j-1]==0){
                    dp[i][j] = dp[i][j-1];
                }else if(obstacleGrid[i][j-1]==1 && obstacleGrid[i-1][j]==0){
                    dp[i][j] = dp[i-1][j];
                }else{
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }

            }
        }
        return dp[row-1][col-1];
    }
}