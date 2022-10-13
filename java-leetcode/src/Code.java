
import java.util.LinkedList;
import java.util.PrimitiveIterator;
import java.util.Stack;


public class Code {
    public static void main(String[] args) {
//        String input1 = "{([])}";
//        boolean check = isStackValid(input1);
//        System.out.println(check); //Valid parentheses
//
//        ListNode l1 = new ListNode(1);
//        l1.next = new ListNode(3);
//        ListNode l2 = new ListNode(1);
//        l2.next = new ListNode(2);
//        ListNode ans = mergeTwoLists(l1,l2);
//        while(ans!=null){
//            System.out.println(ans.val + "\n");
//            ans = ans.next;
//        }

//        int[] prices = {7,1,5,3,6,4};
//        int[] prices = {7,6,5,3,2,1};
//        int[] prices = {2,4,5,1,2};
//        System.out.println(maxProfit(prices));

//        String s = " ";
//        String s = "A man, a plan, a canal: Panama";
//        String s ="race a car";
//        System.out.println(isPalindrome(s));

        TreeNode root = new TreeNode(2,new TreeNode(1),new TreeNode(3));
        //无法做题目，查看leetCode

    }

    //Valid parentheses
    public static boolean isStackValid(String s) {

        Stack<Character> stk = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == ')' && !stk.isEmpty() && stk.peek() == '(') {
                stk.pop();
            } else if (c == ']' && !stk.isEmpty() && stk.peek() == '[') {
                stk.pop();
            } else if (c == '}' && !stk.isEmpty() && stk.peek() == '{') {
                stk.pop();
            } else {
                stk.push(c);
            }
        }
        return stk.isEmpty();
    }

    public static ListNode mergeTwoLists(ListNode list1, ListNode list2){
        ListNode list = new ListNode(-1);//头不用管
        ListNode cur = list;
        while(list1!=null && list2!=null){//当两者都存在的时候才需要比较大小，否则直接将另外一个表直接接到后面
            if(list1.val>list2.val){
                cur.next = list2;
                cur = cur.next;
                list2 = list2.next;
            }else{
                cur.next = list1;
                cur = cur.next;
                list1 = list1.next;
            }
        }
        if(list1 == null){
            cur.next = list2;
        }
        if(list2 == null){
            cur.next = list1;
        }
        return list.next;
    }

    public static int maxProfit(int[] prices) {

        //一次循环
//        int min = Integer.MAX_VALUE;
//        int maxPro = 0;
//        for (int price : prices) {
//            if (price < min) {
//                min = price;
//            } else if (price - min > maxPro) {
//                maxPro = price - min;
//            }
//        }
//        return maxPro;

        //双指针
//        int max=0;
//        int min=prices[0];
//        for (int price : prices) {
//            min = Math.min(price, min);
//            max = Math.max(max, price-min);
//        }
//        return max;


        //贪心思想
        int max = 0;
        int cur = prices[0];
        for (int p : prices) {
            //如果比cur小则替换
            if (p < cur) {
                cur = p;
                continue;
            }
            //如果比 cur 大则计算卖出最大获利
            if (p > cur) {
                max = Math.max(max, p - cur);
            }

        }
        return max;
    }

    private static boolean isPalindrome(String s) {
//        //暴力解法
//        s = s.toLowerCase().replaceAll("[^A-Za-z0-9]","").replaceAll(" ",""); //做了多次循环导致了速度变慢
//        char[] str = s.toCharArray();
//        int max = str.length-1;
//        for(int i=0;i<str.length;i++){
//            if(str[i]-str[max-i]!=0){
//                return false;
//            }
//            if(i>max-i){
//                break;
//            }
//        }
//        return true;

        //官方双指针办法，主要是一次遍历
        int n = s.length();
        int left = 0, right = n - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                ++left;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                --right;
            }
            if (left < right) {
                if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                    return false;
                }
                ++left;
                --right;
            }
        }
        return true;
    }

    private static TreeNode invertTree(TreeNode root){

        if(root == null){
            return null;
        }

         //递归
//        TreeNode tmpNode = root.left;
//        root.left = root .right;
//        root.right = tmpNode;
//        invertTree(root.left);
//        invertTree(root.right);
//        return root;

        //迭代
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            TreeNode tmpNode = queue.poll();
            TreeNode left = tmpNode.left;
            tmpNode.left = tmpNode.right;
            tmpNode.right = left;
            if(tmpNode.left != null){
                queue.add(tmpNode.left);
            }
            if(tmpNode.right!= null){
                queue.add(tmpNode.right);
            }
        }
        return root;
    }
}
