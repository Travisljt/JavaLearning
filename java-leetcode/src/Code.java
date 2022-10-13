

import java.util.*;


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

//        TreeNode root = new TreeNode(2,new TreeNode(1),new TreeNode(3));
//        //无法做题目，查看leetCode
//
//        String s = "aacc";
//        String t = "ccac";
//        System.out.println(isAnagram(s,t));

//        int[] nums = {-1,0,3,5,9,12};
//        int target = 9;
//        System.out.println(search(nums,target));
//
//        int[][] image = {{1,1,1},{1,1,0},{1,0,1}};
//        int sr = 1, sc = 1, newColor = 2;
//        System.out.println(Arrays.deepToString(floodFill(image, sr, sc, newColor)));

        TreeNode root;
        //二叉搜索树，查看leetcode
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

    private static boolean isAnagram(String s, String t){
        //相加字符结果 出错
//        int counter1=0,counter2=0;
//        for(char q:s.toCharArray()){
//            counter1 += q;
//        }
//        for(char p: t.toCharArray()) {
//            counter2 += p;
//        }
//        return counter1 == counter2;

        //判断个位 然后算总数  出错
//        if(s.length()!=t.length()){
//            return false;
//        }
//        int counter=0;
//        for(int i=0;i<s.length();i++){
//            for(int j=0;j<t.length();j++){
//                if(s.charAt(i)==t.charAt(j)){
//                    counter++;
//                    break;
//                }
//            }
//        }
//        return counter==s.length();

        //数组排序
//        if (s.length() != t.length()) {
//            return false;
//        }
//        char[] str1 = s.toCharArray();
//        char[] str2 = t.toCharArray();
//        Arrays.sort(str1);
//        Arrays.sort(str2);
//        return Arrays.equals(str1, str2);

        //hashtable
        if(s.length()!=t.length()){
            return false;
        }
        int[] hashtable = new int[26];
        for(char q : s.toCharArray()){
            hashtable[q-'a']++;
        }
        for(char q : t.toCharArray()){
            hashtable[q-'a']--;
            if(hashtable[q-'a']<0){
                return false;
            }
        }
        return true;
    }

    private static int search(int[] nums, int target){
//        //暴力解法  太慢
//        for(int i=0;i<nums.length;i++){
//            if(nums[i]==target){
//                return i;
//            }
//        }
//        return -1;

        //真正二分法
        int left = 0;
        int right = nums.length - 1;
        while(left<=right){
            int middle = left + (right-left)/2;
            if(nums[middle]<target){
                left = middle + 1;
            }else if (nums[middle]>target){
                right = middle - 1;
            }else{
                return middle;
            }
        }

        return -1;
    }

    private static int[][] floodFill(int[][] image, int sr, int sc, int color) {
        //DFS
//        floodFill_dfs(image,sr,sc,color,image[sr][sc]);

        //BFS
        //定义方向
        int[] dx={1,0,0,-1};
        int[] dy={0,1,-1,0};

        int n = image.length;
        int m = image[0].length;

        int curColor = image[sr][sc];
        if(curColor==color){
            return image;
        }
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{sr,sc});
        image[sr][sc] = color;

        while(!queue.isEmpty()){
            int[] cell = queue.poll();
            int x = cell[0];
            int y = cell[1];

            for(int i=0; i<4 ;i++){
                int mx = x + dx[i];
                int my = y + dy[i];

                if(mx>=0 && mx<n && my>=0 && my<m && image[mx][my]==curColor){
                    image[mx][my] = color;
                    queue.offer(new int[]{mx,my});
                }
            }
        }
        return image;
    }

//    private static void floodFill_dfs(int[][] image, int i, int j, int color, int number){
//        if(i<0 || i>=image.length || j<0 || j>=image[0].length || image[i][j]==color || image[i][j]!=number){
//            return;
//        }else {
//            int temp = image[i][j];
//            image[i][j] = color;
//            floodFill_dfs(image,i-1,j,color,temp);
//            floodFill_dfs(image,i+1,j,color,temp);
//            floodFill_dfs(image,i,j-1,color,temp);
//            floodFill_dfs(image,i,j+1,color,temp);
//        }
//    }
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
//        //递归巧妙方法（利用二叉树原理，左边为小右边为大，root与两者相减后相乘为正数即为root，否则为其中一边的值）但处理不了负数情况
//        if ((root.val - p.val) * (root.val - q.val) <= 0)
//            return root;
//        //否则，p和q位于root的同一侧，就继续往下找
//        return lowestCommonAncestor(p.val < root.val ? root.left : root.right, p, q);

//        //巧妙的办法，但是不能处理负数情况
//        while ((root.val - p.val) * (root.val - q.val) > 0)
//            root = p.val < root.val ? root.left : root.right;
//        //如果相乘的结果是负数，说明p和q位于根节点的两侧，如果等于0，说明至少有一个就是根节点
//        return root;

//        //递归
//        if(root.val < p.val && root.val < q.val) return lowestCommonAncestor(root.right, p, q);
//        if(root.val > p.val && root.val > q.val) return lowestCommonAncestor(root.left, p, q);
//        return root;

        //官方一次遍历,其实就相当于递归，运用二叉树原理，左边就是小于root，右边大于root
        TreeNode ancestor = root;
        while (true) {
            if (p.val < ancestor.val && q.val < ancestor.val) {
                ancestor = ancestor.left;
            } else if (p.val > ancestor.val && q.val > ancestor.val) {
                ancestor = ancestor.right;
            } else {
                break;
            }
        }
        return ancestor;
    }
}
