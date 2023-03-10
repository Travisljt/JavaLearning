public class Main {
    public static TreeNode pre = null;
    public static void main(String[] args) {
        TreeNode root = new TreeNode(2,new TreeNode(1),new TreeNode(3));
        System.out.println(isValidBST(root));
    }

    public static boolean isValidBST(TreeNode root){
        //中序遍历  大小顺序一定是先遍历左子树，然后根，最后右子树，因此顺序为从小到大
        if(root == null){
            return true;
        }
        if(!isValidBST(root.left)){
            return false;
        }
        if(pre == null){
            pre = root;
        }else if(pre.val >= root.val){
            return false;
        }
        pre = root;
        return isValidBST(root.right);
    }

}