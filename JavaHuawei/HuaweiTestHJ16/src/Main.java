import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //输入描述：
        //输入的第 1 行，为两个正整数N，m，用一个空格隔开：
        //
        //（其中 N （ N<32000 ）表示总钱数， m （m <60 ）为可购买的物品的个数。）

        //从第 2 行到第 m+1 行，第 j 行给出了编号为 j-1 的物品的基本数据，每行有 3 个非负整数 v p q
        //
        //（其中 v 表示该物品的价格（ v<10000 ）， p 表示该物品的重要度（ 1 ~ 5 ），
        // q 表示该物品是主件还是附件。如果 q=0 ，表示该物品为主件，
        // 如果 q>0 ，表示该物品为附件， q 是所属主件的编号）

        //输出描述：
        // 输出一个正整数，为张强可以获得的最大的满意度。

        int N = sc.nextInt();
        int m = sc.nextInt();

        Goods[] goods = new Goods[m];
        for (int i = 0; i < m; i++) {
            goods[i] = new Goods();
        }
        for (int i = 0; i < m; i++) {
            int v = sc.nextInt();
            int p = sc.nextInt();
            int q = sc.nextInt();
            goods[i].v = v;
            goods[i].p = p * v;  // 满意度：直接用p*v，方便后面计算
            //判断是否为主件
            if (q == 0) {
                goods[i].main = true;
            } else if (goods[q - 1].a1 == -1) {//由于是从0开始计数，所以附件对于第一件是相当于good[0]
                goods[q - 1].a1 = i;
            } else {//一个主件可以有两个附件，所以不是a1就是a2了
                goods[q - 1].a2 = i;
            }
        }

        int[][] dp = new int[m + 1][N + 1];//创建dp方程，为总满意度，第一行第一列都为0
        for (int i = 1; i <= m; i++) {
            for (int j = 0; j <= N; j++) {
                dp[i][j] = dp[i - 1][j]; //每一次将上一次状态转移至这一件物品，则只需哟判断j>=good[i-1].v,etc的情况
                if (!goods[i - 1].main) {//如果物品是附件，直接跳过
                    continue;
                }
                if (j >= goods[i - 1].v) {//购买主件，比较上一个状态的满意度跟加上这个主键满意度相比
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - goods[i - 1].v] + goods[i - 1].p);
                }
                if (goods[i - 1].a1 != -1 && j >= goods[i - 1].v + goods[goods[i - 1].a1].v) {//购买主键与他存在的第一个附件一起看哪个具有更高的满意度
                    dp[i][j] = Math.max(dp[i][j],
                            dp[i - 1][j - goods[i - 1].v - goods[goods[i - 1].a1].v] + goods[i - 1].p +
                                    goods[goods[i - 1].a1].p);
                }
                if (goods[i - 1].a2 != -1 && j >= goods[i - 1].v + goods[goods[i - 1].a2].v) {//购买主键与他存在的第二个附件一起看哪个具有更高的满意度
                    dp[i][j] = Math.max(dp[i][j],
                            dp[i - 1][j - goods[i - 1].v - goods[goods[i - 1].a2].v] + goods[i - 1].p +
                                    goods[goods[i - 1].a2].p);
                }
                if (goods[i - 1].a1 != -1 && goods[i - 1].a2 != -1 &&
                        j >= goods[i - 1].v + goods[goods[i - 1].a1].v + goods[goods[i - 1].a2].v) {//购买主键与他存在的两个附件一起看哪个具有更高的满意度
                    dp[i][j] = Math.max(dp[i][j],
                            dp[i - 1][j - goods[i - 1].v - goods[goods[i - 1].a1].v - goods[goods[i -
                                    1].a2].v] + goods[i - 1].p + goods[goods[i - 1].a1].p + goods[goods[i -
                                    1].a2].p);
                }
            }
        }
        //判定结束后，取表中最右下角的值，即为最大满意度
        System.out.println(dp[m][N]);

    }

    static class Goods {
        int v;
        int p;
        boolean main = false; //考虑这个是否为主件

        //给附件定义
        int a1 = -1;  //定义附件1的编号
        int a2 = -1;  //定义附件2的编号
    }

//    private static class good{
//        public int v;
//        public int p;
//        public int q;
//
//        public int a1 = 0;
//        public int a2 = 0;
//
//        public good(int v, int p, int q){
//            this.v = v;
//            this.p = p;
//            this.q = q;
//        }
//
//        public int getV() {
//            return v;
//        }
//
//        public int getP() {
//            return p;
//        }
//
//        public int getQ() {
//            return q;
//        }
//
//        public void setA1(int a1) {
//            this.a1 = a1;
//        }
//
//        public void setA2(int a2) {
//            this.a2 = a2;
//        }
//    }
}