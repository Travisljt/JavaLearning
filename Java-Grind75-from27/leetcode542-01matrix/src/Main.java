import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
    //01 矩阵
        /*
        给定一个由 0 和 1 组成的矩阵 mat，请输出一个大小相同的矩阵，
        其中每一个格子是 mat 中对应位置元素到最近的 0 的距离。

        两个相邻元素间的距离为 1 。

        输入：mat = [[0,0,0],[0,1,0],[1,1,1]]
        输出：[[0,0,0],[0,1,0],[1,2,1]]
         */
        boolean flag = true;
        int[][] mat = {{0,0,0},{0,1,0},{1,1,1}};
        int[][] update_BFS = updateMatrix_BFS(mat);
        int[][] update_DP = update_DP(mat);
        int[][] update;
        if(flag) {
            update = update_BFS;
        }else {
            update = update_DP;
        }
        for(int[] i : update){
            for(int j : i){
                System.out.print(j);
            }
            System.out.print("\n");
        }

    }

    private static int[][] update_DP(int[][] mat) {
        /*
        对于任一点 (i, j)(i,j)，距离 00 的距离为：
        f(i,j)={
          1+min(f(i−1,j),f(i,j−1),f(i+1,j),f(i,j+1))   if matrix[i][j]==1
          0                                            if matrix[i][j]==0
         }
        尝试将问题分解：

       1. 距离 (i, j)(i,j) 最近的 00 的位置，是在其 「左上，右上，左下，右下」4个方向之一；
       2. 因此我们分别从四个角开始递推，就分别得到了位于「左上方、右上方、左下方、右下方」距离 (i, j)(i,j) 的最近的 00 的距离，取 minmin 即可；
       3. 通过上两步思路，我们可以很容易的写出 44 个双重 forfor 循环，动态规划的解法写到这一步其实已经完全 OKOK 了；
       4. 如果第三步还不满足的话，从四个角开始的 44 次递推，其实还可以优化成从任一组对角开始的 22 次递推，比如只写从左上角、右下角开始递推就行了，为啥这样可以呢？且听我不负责任的草率论证 = = #
            首先从左上角开始递推 dp[i][j]dp[i][j] 是由其 「左方」和 「左上方」的最优子状态决定的；
            然后从右下角开始递推 dp[i][j]dp[i][j] 是由其 「右方」和 「右下方」的最优子状态决定的；
            看起来第一次递推的时候，把「右上方」的最优子状态给漏掉了，其实不是的，因为第二次递推的时候「右方」的状态在第一次递推时已经包含了「右上方」的最优子状态了；
            看起来第二次递推的时候，把「左下方」的最优子状态给漏掉了，其实不是的，因为第二次递推的时候「右下方」的状态在第一次递推时已经包含了「左下方」的最优子状态了。

         */
        int m = mat.length, n = mat[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = mat[i][j] == 0 ? 0 : 10000;
            }
        }

        // 从左上角开始
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i - 1 >= 0) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + 1);
                }
                if (j - 1 >= 0) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + 1);
                }
            }
        }
        // 从右下角开始
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i + 1 < m) {
                    dp[i][j] = Math.min(dp[i][j], dp[i + 1][j] + 1);
                }
                if (j + 1 < n) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][j + 1] + 1);
                }
            }
        }
        return dp;
    }

    private static int[][] updateMatrix_BFS(int[][] mat) {
        //首先把每个源点0入队，然后从各个0同时开始一圈一圈的向1扩散（每个1都是被离它最近的0扩散到的 ），
        // 扩散的时候可以设置 int[][] dist 来记录距离（即扩散的层次）并同时标志是否访问过。
        // 对于本题是可以直接修改原数组 int[][] matrix 来记录距离和标志是否访问的，
        // 这里要注意先把 matrix 数组中 1 的位置设置成 -1
        // （设成Integer.MAX_VALUE啦，m * n啦，10000啦都行，
        // 只要是个无效的距离值来标志这个位置的 1 没有被访问过就行辣~）
        //

        Queue<int[]> queue = new LinkedList<>();
        int m = mat.length;
        int n = mat[0].length;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(mat[i][j]==0){
                    queue.offer(new int[] {i,j});
                } else {
                    mat[i][j] = -1;
                }
            }
        }

        int[] dx = new int[] {-1,1,0,0}; //横坐标方向
        int[] dy = new int[] {0,0,-1,1}; //纵坐标方向

        while(!queue.isEmpty()){
            int[] point =  queue.poll();//先将第一个为0的元素删除，拿出来
            int x = point[0], y = point[1];//x,y分别为其坐标
            for(int i=0;i<4;i++){
                int newX = x + dx[i];
                int newY = y + dy[i];
                /*
                  如果访问的点是-1，则这就是未被访问过的1
                  因此将这个点到0的距离可以表示为mat[x][y]+1;
                  再将这个点放入队列中，寻找与这个点的另外四个临点的值是否为未被访问过的点(即为-1）
                  未被找到的点，就再一次更新它的值，再添加进队列中
                 */
                if(newX>=0 && newX<m && newY>=0 && newY<n && mat[newX][newY]==-1){
                    mat[newX][newY] = mat[x][y]+1;
                    queue.offer(new int[] {newX,newY});
                }
            }
        }
        return mat;
    }
}