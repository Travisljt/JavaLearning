

import org.w3c.dom.Node;

import java.util.*;

//从Grind 75 的 2 开始
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

//        TreeNode root;
//        //二叉搜索树，查看leetcode

//        TreeNode root;
//        //平衡二叉树，查看leetcode

//        ListNode hascyle = new ListNode();
//        //has cycle, 查看leetcode
//
//        MyQueue obj = new MyQueue();
//        obj.push(1);
//        obj.push(2);
//        int param_2 = obj.pop();
//        int param_3 = obj.peek();
//        boolean param_4 = obj.empty();
//        System.out.println(param_2+"\n"+param_3+"\n"+param_4);

//        String ransomNote = "aa";
//        String magazine = "aab";
//        System.out.println(canConstruct(ransomNote,magazine));

//        int n = 45;
//        System.out.println(climbStairs(n));
//
//        String s = "aabbccd";
//        System.out.println(longestPalindrome(s));

        ListNode listNode1 = new ListNode(1);
        //题目查看leetcode
    }



    //20. Valid parentheses
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

    //21. Merge Two Sorted Lists
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

    //121. Best Time to Buy and Sell Stock
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

    //125. Valid Palindrome
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

    //226. Invert Binary Tree
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

    //242. Valid Anagram
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
        return hashtable(t, s);//详细见下文
    }

    //704. Binary Search
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

    //733. Flood Fill
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
    private static void floodFill_dfs(int[][] image, int i, int j, int color, int number){
        if(i<0 || i>=image.length || j<0 || j>=image[0].length || image[i][j]==color || image[i][j]!=number){
            return;
        }else {
            int temp = image[i][j];
            image[i][j] = color;
            floodFill_dfs(image,i-1,j,color,temp);
            floodFill_dfs(image,i+1,j,color,temp);
            floodFill_dfs(image,i,j-1,color,temp);
            floodFill_dfs(image,i,j+1,color,temp);
        }
    }

    //235. Lowest Common Ancestor of a Binary Search Tree
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

    //110. Balanced Binary Tree
    public static boolean isBalanced(TreeNode root){
//        //官方自顶向下递归
//        if(root == null){ //空节点也是平衡树
//            return true;
//        } else {
//            return Math.abs(depth(root.left)-depth(root.right)) <=1 && isBalanced(root.left) && isBalanced(root.right);
//        }

        //官方自底向上递归
        return height(root) >= 0;

    }
    //这就是树寻找节点高度的方式
    public static int depth(TreeNode root){
        if(root == null){
            return 0;
        }else{
            return Math.max(depth(root.left),depth(root.right))+1;
        }
    }
    public static int height(TreeNode root){
        if(root == null){
            return 0;
        }
        int leftHeight,rightHeight;
        //在判断里先算完左边节点递归的isBalance后，得出结果不为平衡树则不需要再递归右边节点，这可以更快！
        if((leftHeight = height(root.left)) == -1 || (rightHeight = height(root.right)) ==-1 || Math.abs(leftHeight-rightHeight)>1){
            return -1;
        }
        return Math.max(leftHeight,rightHeight) + 1;
    }//属于isBlance

    //141. Linked List Cycle
    public static boolean hasCycle(ListNode head){
//        //快慢指针
//        if(head==null || head.next==null){
//            return false;
//        }
//        ListNode pOne = head;
//        ListNode pTwo = head;
//
//        pOne = pOne.next;
//        pTwo = pTwo.next.next;
//
//        while (pTwo!=pOne){
//            if(pTwo==null || pTwo.next ==null){
//                return false;
//            }
//            pTwo = pTwo.next.next;
//            pOne = pOne.next;
//        }
//
//        return true;

        //hashset
        Set<ListNode> seen = new HashSet<ListNode>();
        while (head != null) {
            if (!seen.add(head)) {
                return true;
            }
            head = head.next;
        }
        return false;

    }

    //232. Implement Queue using Stacks  见myQueue

    //278. First Bad Version
    public static int firstBadVersion(int n) {
        int left = 1;
        int right = n;
        while(left<right){
            int mid = left + (right-left)/2;
            if(!isBadVersion(mid)){
                left = mid + 1;
            }else {
                right = mid;
            }
        }
        return left;
    }
    public static boolean isBadVersion(int version){
        return version==1;
    }//属于firstBadVersion

    //383. Ransom Note
    public static boolean canConstruct(String ransomNote, String magazine){
        //hashtable
        if(ransomNote.length()>magazine.length()){
            return false;
        }
        return hashtable(ransomNote, magazine);
    }

    private static boolean hashtable(String includeStr, String allStr) {
        int[] hashtable = new int[26];

        for(char str : allStr.toCharArray()){
            hashtable[str-'a']++;
        }
        for(char str : includeStr.toCharArray()){
            hashtable[str-'a']--;
            if(hashtable[str-'a']<0){
                return false;
            }
        }
        return true;
    } //属于canConstruct和isAnagram

    //70. Climbing Stairs
    public static int climbStairs(int n){
//       // dp思想 动态规划
//        int[] dp = new int[n + 1];
//        dp[0] = 1;
//        dp[1] = 1;
//        for(int i = 2; i <= n; i++) {
//            dp[i] = dp[i - 1] + dp[i - 2];
//        }
//        return dp[n];

//        //以下为动态规划优化
//        //确定了第一步跟第二步，第三步的结果为第一步跟第二步的和
//        if(n==1 || n == 2){
//            return n;
//        }
//        int a = 1, b = 2, tmp;
//
//        //第n步为n-1与n-2的和  即 dp[n] = dp[n-1] + dp[n-2]
//        //从第三步开始，一直到第n步， a为n-2，b为n-1，进行循环后a为n-1，b为n
//        for(int i = 3; i<=n ; i++){
//            tmp = a;
//            a = b;
//            b = tmp + b;
//        }
//        return b;

//        //官方斐波那契数学方式化简方程，通项公式
//        double sqrt5 = Math.sqrt(5);
//        double f = Math.pow((1 + sqrt5) / 2, n + 1) - Math.pow((1 - sqrt5) / 2, n + 1);
//        return (int) Math.round(f / sqrt5);

        //官方优化进阶矩阵快速幂，。。。好难。。。
        int[][] q = {{1, 1}, {1, 0}};
        int[][] res = pow(q, n);
        return res[0][0];
    }
    public static int[][] pow(int[][] a, int n) {
        int[][] ret = {{1, 0}, {0, 1}};
        while (n > 0) {
            if ((n & 1) == 1) {
                ret = multiply(ret, a);
            }
            n >>= 1;
            a = multiply(a, a);
        }
        return ret;
    } //属于climbStairs
    public static int[][] multiply(int[][] a, int[][] b) {
        int[][] c = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                c[i][j] = a[i][0] * b[0][j] + a[i][1] * b[1][j];
            }
        }
        return c;
    }//属于climbStairs

    //409. Longest Palindrome
    public static int longestPalindrome(String s) {
        //hashtable
        // 回文数的特性是  aabb 或 aacbb  所以有且只有一个字符是奇数能添加，其余均为偶数
        int[] table = new int[128];
        for(char st : s.toCharArray()){
            table[st]++;
        }

        int ans = 0;
        for(int i : table){
            ans += i/2 * 2; //根据int的特性，整除法，例如：a出现5次，实际上只要用4次而已
            if(i%2==1 && ans%2 == 0){//有且仅当i的值为奇数时，并且ans为偶数时
                ans++;
            }
        }

        return ans;
    }

    //206. Reverse Linked List
    public static ListNode reverseList(ListNode head) {
//        //迭代
//        ListNode prev = null;
//        ListNode curr = head;
//        while(curr!=null){
//            ListNode next = curr.next;
//            curr.next = prev;
//            prev = curr;
//            curr = next;
//        }
//        return prev;

        //递归
        if(head == null || head.next == null){
            return head;
        }
        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    //169. Majority Element
    public static int majorityElement(int[] nums) {
//        //Hashmap方法
//        HashMap<Integer,Integer> counts = allNumber(nums);
//        //Map.Entry是Map声明的一个内部接口， 它表示Map中的一个实体（一个key-value对）  可以理解为map是个数组，map.entry是其中一个数
//        Map.Entry<Integer,Integer> majorityEntry = null;
//        for(Map.Entry<Integer,Integer>  entry : counts.entrySet()){//只取entry进行遍历
//            if(majorityEntry==null || entry.getValue() > majorityEntry.getValue()){//相当于max 比较最大值
//                majorityEntry =  entry;
//            }
//        }
//        assert majorityEntry != null;
//        return majorityEntry.getKey();
//
//        //排序法
//        //太神奇了，其实就是这个题的特性，将数字排好序之后，中间出现的那个数字的值一定是最多的那个
//        Arrays.sort(nums);
//        return nums[nums.length / 2];

//        //随机化, 因为众数出现的概率最大 所以随机取很大概率就是多数   ps:完全看运气
//        Random rand = new Random();
//
//        int majorityCount = nums.length / 2;
//
//        while (true) {
//            int candidate = nums[randRange(rand, nums.length)];
//            if (countOccurrences(nums, candidate) > majorityCount) {
//                return candidate;
//            }
//        }

        //摩尔投票法  神奇！ 仅限于优先给定条件，给出的数组是一定有多数的，即多数的数量大于n/2
        //遇到相同票数+1，否则-1，因为题目给出多数的数量大于n/2，因此其他值相加小于n/2，因此多数的总和一定是大于其他数
        //总结，得出最后counter不为0的数，ret即多数
        int ret = 0;
        int counter = 0;
        for (int num : nums) {
            if (counter == 0) {
                ret = num;
                counter = 1;
            } else if (num == ret) {
                counter++;
            } else {
                counter--;
            }
        }
        return ret;
    }
    public static HashMap<Integer,Integer> allNumber(int[] nums){ //遍历数组 将每个元素出现的次数存入hashmap中
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        for(int i : nums){
            if(!hashMap.containsKey(i)){
                hashMap.put(i,1);
            }else{
                hashMap.put(i,hashMap.get(i)+1);
            }
        }
        return hashMap;
    }
    private static int countOccurrences(int[] nums, int num) {
        int count = 0;
        for (int j : nums) {
            if (j == num) {
                count++;
            }
        }
        return count;
    }
    private static int randRange(Random rand, int max) {
        return rand.nextInt(max);
    }

    //67. Add Binary
    public static String addBinary(String a, String b) {
        StringBuilder ans = new StringBuilder();
        int carry = 0;

        //同步遍历
        for(int i = a.length()-1, j = b.length()-1;i>=0 || j>=0;i--,j--){
            int sum = carry;

            //先选a中的各位值，再加上b中的各位值
            sum += i>=0 ? a.charAt(i)-'0' : 0;
            sum += j>=0 ? b.charAt(j)-'0' : 0;

            //计算单个位的值大，相加等于2时，carry为1，否则为0
            ans.append(sum%2);
            carry = sum/2;
        }
        //在尾巴加上carry位，最后反转过来就是答案
        ans.append(carry==1 ? carry : "");
        return ans.reverse().toString();
    }

    //543. Diameter of Binary Tree
    static int diameter_ans = 0;
    public static int diameterOfBinaryTree(TreeNode root) {
        //递归
        diameterOfBinaryTree_dfs(root);
        return diameter_ans;
    }

    public static int diameterOfBinaryTree_dfs(TreeNode root){
        if(root==null){
            return 0;//访问到空节点
        }
        int leftSize = diameterOfBinaryTree_dfs(root.left);//左儿子为根的深度
        int rightSize = diameterOfBinaryTree_dfs(root.right);//右儿子为跟的深度
        diameter_ans = Math.max(diameter_ans,leftSize + rightSize);//将每个节点最大直径（左树深度跟右树深度）与当前最大值进行比较
        return Math.max(leftSize,rightSize) + 1;//返回节点深度 +1是加上当前节点
    }

    //876. Middle of the Linked List
    public ListNode middleNode(ListNode head) {
//        //暴力解法（单指针）
//        ListNode cnt = head;
//        int counter = 0;
//        while(cnt!=null){
//            counter++;
//            cnt = cnt.next;
//        }
//        for(int i=0;i<counter/2;i++){
//            head = head.next;
//        }
//        return head;

        //快慢指针
        ListNode slow = head, fast = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    //104. Maximum Depth of Binary Tree
    public int maxDepth(TreeNode root) {
        //递归
        if(root==null){
            return 0;
        }
        int leftSize = maxDepth(root.left);
        int rightSize = maxDepth(root.right);
        return Math.max(leftSize,rightSize)+1;
    }

    //217. Contains Duplicate
    public boolean containsDuplicate(int[] nums) {
//        //hashset
//        HashSet<Integer> table = new HashSet<>();
//        for(int i : nums){
//            if(!table.add(i)) {
//                return true;
//            }
//        }
//        return false;

//        //排序法（很慢）
//        Arrays.sort(nums);
//        int n = nums.length;
//        for (int i = 0; i < n - 1; i++) {
//            if (nums[i] == nums[i + 1]) {
//                return true;
//            }
//        }
//        return false;

        //Stream流
        return Arrays.stream(nums).distinct().count() < nums.length;
    }

    //53. Maximum Subarray
    public int maxSubArray(int[] nums) {
        //动态规划
        int pre = 0, maxValue = nums[0];
        for(int i : nums){
            pre = Math.max(pre+i,i);
            maxValue = Math.max(pre,maxValue);
        }
        return maxValue;
    }

    //57. Insert Interval
    public int[][] insert(int[][] intervals, int[] newInterval) {
        //模拟解法
        int left = newInterval[0];
        int right = newInterval[1];
        List<int[]> ansList = new ArrayList<>();
        boolean placed = false;//标记符  确认插入前后
        for(int[] interval : intervals){
            if(interval[0]>right){
                if(!placed){
                    ansList.add(new int[]{left,right});
                    placed = true;
                }
                ansList.add(interval);
            }else if (interval[1]<left){
                ansList.add(interval);
            }else{
                left = Math.min(left,interval[0]);
                right = Math.max(right,interval[1]);
            }
        }
        if(!placed){
            ansList.add(new int[]{left,right});
        }
        int[][] ans = new int[ansList.size()][2];
        for(int i=0;i<ansList.size();i++){
            ans[i] = ansList.get(i);
        }
        return ans;
    }

    //542. 01 Matrix

}
