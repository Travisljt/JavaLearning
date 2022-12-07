import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    /*
    输入一个单向链表和一个节点的值，从单向链表中删除等于该值的节点，删除后如果链表中无节点则返回空指针。
    链表的值不能重复。
    构造过程，例如输入一行数据为:
    6 2 1 2 3 2 5 1 4 5 7 2 2
    则第一个参数6表示输入总共6个节点，第二个参数2表示头节点值为2，剩下的2个一组表示第2个节点值后面插入第1个节点值，为以下表示:
    1 2 表示为
    2->1
    链表为2->1

    3 2表示为
    2->3
    链表为2->3->1

    5 1表示为
    1->5
    链表为2->3->1->5

    4 5表示为
    5->4
    链表为2->3->1->5->4

    7 2表示为
    2->7
    链表为2->7->3->1->5->4

    最后的链表的顺序为 2 7 3 1 5 4

    最后一个参数为2，表示要删掉节点为2的值
    删除 结点 2

    则结果为 7 3 1 5 4
     */

        //用链表反而复杂，只需要使用用一个arraylist有add跟remove功能即可
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int total = sc.nextInt();
            int head = sc.nextInt();

            List<Integer> linkedList = new ArrayList<>();
            linkedList.add(head);
            for(int i=0;i<total-1;i++){
                int value = sc.nextInt();
                int target = sc.nextInt();
                linkedList.add(linkedList.indexOf(target)+1,value);
            }
            int remove = sc.nextInt();
            linkedList.remove((Integer) remove);
            for(int i : linkedList){
                System.out.print(i + " ");
            }
            System.out.println();
        }


    }
}