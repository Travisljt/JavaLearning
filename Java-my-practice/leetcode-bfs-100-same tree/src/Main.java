import com.sun.source.tree.NewArrayTree;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public boolean isSameTree(TreeNode p, TreeNode q){
        if(p==null && q==null){
            return true;
        }else if(p==null||q==null){
            return false;
        }
        Queue<TreeNode> queue1 = new ArrayDeque<>();
        Queue<TreeNode> queue2 = new ArrayDeque<>();
        queue1.offer(p);
        queue2.offer(q);
        while (!queue1.isEmpty() && !queue2.isEmpty()){
            TreeNode child1 = queue1.poll();
            TreeNode child2 = queue2.poll();
            if(child1.val != child2.val){
                return false;
            }
            if(child1.left==null ^ child2.left==null){
                return false;
            }
            if(child2.right==null ^ child1.right==null){
                return false;
            }
            if(child1.left !=null){
                queue1.offer(child1.left);
            }
            if(child2.left!= null){
                queue2.offer(child2.left);
            }
            if(child1.right !=null){
                queue1.offer(child1.right);
            }
            if(child2.right!= null){
                queue2.offer(child2.right);
            }

        }
        return queue1.isEmpty() && queue2.isEmpty();
    }
}


