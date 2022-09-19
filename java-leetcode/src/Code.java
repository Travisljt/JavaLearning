import java.util.Stack;


public class Code {
    public static void main(String[] args) {
        String input1 = "{([])}";
        boolean check = isStackValid(input1);
//        System.out.println(check); //Valid parentheses

        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(3);


        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(2);


        ListNode ans = mergeTwoLists(l1,l2);
        while(ans!=null){
            System.out.println(ans.val + "\n");
            ans = ans.next;
        }
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
}
