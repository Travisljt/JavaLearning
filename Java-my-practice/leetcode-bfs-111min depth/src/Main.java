import java.security.interfaces.RSAMultiPrimePrivateCrtKey;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }


   static class QueueNode {
        TreeNode node;
        int depth;

        public QueueNode(TreeNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }

    public static int minDepth(TreeNode root){
//        return dfs(root);

        if(root==null){
            return 0;
        }
        Queue<QueueNode> queue = new LinkedList<>();
        queue.offer(new QueueNode(root,1));
        while (!queue.isEmpty()){
            QueueNode queueNode = queue.poll();
            TreeNode node = queueNode.node;
            int depth = queueNode.depth;
            //BFS能够保证最先搜索到的叶子节点的深度最小
            if(node.left==null && node.right==null){
                return depth;
            }
            if(node.left!=null){
                queue.offer(new QueueNode(node.left,depth+1));
            }
            if(node.right!=null){
                queue.offer(new QueueNode(node.right,depth+1));
            }
        }
        return 0;
    }

//    private static int dfs(TreeNode root) {
//        if(root == null) return 0;
//        if(root.left==null && root.right==null) return 1;
//        int min1 = dfs(root.left);
//        int min2 = dfs(root.right);
//        if(root.left==null || root.right==null) return min1+min2+1;
//        return Math.min(min1,min2)+1;
//    }


}



