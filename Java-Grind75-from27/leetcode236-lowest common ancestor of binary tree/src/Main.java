import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {

    Map<Integer, TreeNode> map = new HashMap<>();
    Set<Integer> set = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    /*
    hashmap解法：
    先遍历二叉树，将二叉树子值对应的父节点存起来；然后在通过set对p往前遍历时做访问记录，最后从q往前遍历。
    如果遇到有相同值的情况，那个节点就是公共节点
     */
//    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
//        dfs(root);
//        while( p != null){
//            this.set.add(p.val);
//            p = map.get(p.val);
//        }
//        while (q != null){
//            if(this.set.contains(q.val)){
//                return q;
//            }
//            q = map.get(q.val);
//        }
//        return null;
//    }
//
//    public void dfs(TreeNode root){
//        if(root.left != null){
//            this.map.put(root.left.val,root);
//            dfs(root.left);
//        }
//        if(root.right != null){
//            this.map.put(root.right.val, root);
//            dfs(root.right);
//        }
//    }

    //递归方式 好理解 但跑的慢
    /*
    root为p，q的最近公共祖先，满足三种情况：
    1。p和q在root子树中，且分别处于两侧
    2。p=root，q在左侧或右侧
    3。q=root，p在左侧或右侧
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        // 如果p,q为根节点，则公共祖先为根节点
        if (root.val == p.val || root.val == q.val) return root;
        // 如果p,q在左子树，则公共祖先在左子树查找
        if (find(root.left, p) && find(root.left, q)) {
            return lowestCommonAncestor(root.left, p, q);
        }
        // 如果p,q在右子树，则公共祖先在右子树查找
        if (find(root.right, p) && find(root.right, q)) {
            return lowestCommonAncestor(root.right, p, q);
        }
        // 如果p,q分属两侧，则公共祖先为根节点
        return root;
    }

    public boolean find (TreeNode root, TreeNode c){
        if (root == null) return false;
        if (root.val == c.val) {
            return true;
        }

        return find(root.left, c) || find(root.right, c);
    }

    //大佬递归
    /*
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if(left == null) return right;
        if(right == null) return left;
        return root;
    }
     */
}