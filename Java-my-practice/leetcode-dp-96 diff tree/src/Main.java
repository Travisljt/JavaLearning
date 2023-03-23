import javax.swing.tree.TreeCellRenderer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public static int numTrees(int n){
        //主要是推导公式
        //G(n) = f(1) + f(2) +.....+f(n)
        //当i为根节点时，左子树的节点为i-1个，右子树的节点为n-i个  f(i) = G(i-1) * G(n-i)
        //将俩公式结合，有：G(n) = G(0)*G(n-1) + G(1)*G(n-2) + .....+ G(n-1)*G(0)
        //作为循环变量，就可以写出
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        for(int i=2;i<=n;i++){
            for(int j=1;j<=i;j++){
                dp[i] += dp[j-1] * dp[i-j];
            }
        }
        return dp[n];
    }

    public static List<TreeNode> generatesTrees(int n){
        if (n == 0) {
            return new LinkedList<>();
        }
        return dfs(1, n);


    }

    public static List<TreeNode> dfs(int start, int end){
        List<TreeNode> allTrees = new LinkedList<TreeNode>();
        if (start > end) {
            allTrees.add(null);
            return allTrees;
        }

        // 枚举可行根节点
        for (int i = start; i <= end; i++) {
            // 获得所有可行的左子树集合
            List<TreeNode> leftTrees = dfs(start, i - 1);

            // 获得所有可行的右子树集合
            List<TreeNode> rightTrees = dfs(i + 1, end);

            // 从左子树集合中选出一棵左子树，从右子树集合中选出一棵右子树，拼接到根节点上
            for (TreeNode left : leftTrees) {
                for (TreeNode right : rightTrees) {
                    TreeNode currTree = new TreeNode(i);
                    currTree.left = left;
                    currTree.right = right;
                    allTrees.add(currTree);
                }
            }
        }
        return allTrees;


    }


}

class TreeNode{
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(){}
    TreeNode(int val){this.val = val;}
    TreeNode(int val, TreeNode left, TreeNode right){
        this.val =val;
        this.left =left;
        this.right = right;
    }
}