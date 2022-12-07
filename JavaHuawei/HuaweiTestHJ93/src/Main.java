import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        描述
        输入int型数组，询问该数组能否分成两组，使得两组中各元素加起来的和相等，
        并且，所有5的倍数必须在其中一个组中，所有3的倍数在另一个组中（不包括5的倍数），
        不是5的倍数也不是3的倍数能放在任意一组，可以将数组分为空数组，
        能满足以上条件，输出true；不满足时输出false。

        *******数学*********
        先把三和五的倍数都挑出来，算好两边的和sum3和sum5，所有数总和为sum，
        不是3或5倍数的剩余的数放在集合中。
        求出target = sum/2-sum3或者target=sum/2-sum5作为目标数，
        看list中找能不能凑出target。
        在剩余集合中找target是一个dfs过程

        输入：
        4
        1 5 -5 1

        输出：
        true
        说明：
        第一组：5 -5 1
        第二组：1

        输入：
        3
        3 5 8

        输出：
        false
        说明：
        由于3和5不能放在同一组，所以不存在一种分法。
         */
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            List<Integer> list = new LinkedList<>();
            int n = sc.nextInt();
            int sum5 = 0, sum3 = 0, sum = 0;
            //根据输入先算出sum3，sum5和sum，同时吧不是5和3倍数的剩余数放入集合list
            for (int i = 0; i < n; i++) {
                int cur = sc.nextInt();
                if (cur % 5 == 0) {
                    sum5 += cur;
                } else if (cur % 3 == 0) {
                    sum3 += cur;
                } else {
                    list.add(cur);
                }
                sum += cur;
            }
            //特例，总和不是2的倍数，不可以分2份和相等的数字
            if (sum % 2 != 0) {
                System.out.println("false");
            } else {//否则，在剩余数字中找目标数字
                //因为sum/2就是将值对半分了，因此sum3 + target1 = sum5 + target2，
                //如果list里能找出n个数字凑出这个target1，那么剩下的数就是target2
                int target = sum / 2 - sum3;
                System.out.println(helper(list, target, 0));
            }

        }
    }

    //终止条件，用完集合数，返回target==0
    //
    //两种递归情况
    //
    //--选择start位置，递归找新目标数target-list.get(start)
    //
    //--不选择start位置，在start+1和以后位置找target
    private static boolean helper(List<Integer> list, int target, int start) {
        if (start == list.size()) return target == 0;
        //两种情况，
        //1.如果target等于13，list里刚好存在4，9，就调用第一种情况返回true
        //2.如果target等于13，list里刚好存在13，就调用第二种情况返回true
        return helper(list, target - list.get(start), start + 1) || helper(list, target, start + 1);
    }
}