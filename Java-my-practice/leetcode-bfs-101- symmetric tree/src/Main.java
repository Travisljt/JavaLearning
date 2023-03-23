
import java.util.ArrayDeque;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public static boolean isSymmetric(TreeNode root){
        if(root==null || root.left==null && root.right==null){
            return true;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();

        queue.offer(root);
        queue.offer(root);
        while(!queue.isEmpty() ){
            TreeNode root1 = queue.poll();
            TreeNode root2 = queue.poll();
            if(root1==null && root2==null) continue;
            if(root1==null) return false;
            if(root2==null) return false;
            if(root1.val!=root2.val) return false;
            queue.offer(root1.left);
            queue.offer(root2.right);
            queue.offer(root1.right);
            queue.offer(root2.left);
        }
        return true;
    }
}


