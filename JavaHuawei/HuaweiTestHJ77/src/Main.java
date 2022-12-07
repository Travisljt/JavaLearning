import java.util.*;

public class Main {
    static List<String> l = new ArrayList<>();
    public static void main(String[] args) {
         /*
         给定一个正整数N代表火车数量，0<N<10，接下来输入火车入站的序列，
         一共N辆火车，每辆火车以数字1-9编号，火车站只有一个方向进出，同时停靠在火车站的列车中，
         只有后进站的出站了，先进站的才能出站。
        要求输出所有火车出站的方案，以字典序排序输出。
        输入：
        3
        1 2 3

        输出：
        1 2 3
        1 3 2
        2 1 3
        2 3 1
        3 2 1

        说明：
        第一种方案：1进、1出、2进、2出、3进、3出
        第二种方案：1进、1出、2进、3进、3出、2出
        第三种方案：1进、2进、2出、1出、3进、3出
        第四种方案：1进、2进、2出、3进、3出、1出
        第五种方案：1进、2进、3进、3出、2出、1出
        请注意，[3,1,2]这个序列是不可能实现的。
          */
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            l.clear();//静态变量，每次先清空
            int nums = sc.nextInt();
            int[] id = new int[nums];
            Stack<Integer> stack = new Stack<>();
            for(int i=0;i<nums;i++){
                id[i] = sc.nextInt();
            }
            trainOut(id,0,stack,"",0);
            Collections.sort(l);
            for(String str : l ){//l表示火车出站的可能顺序
                System.out.println(str);
            }
        }
    }

    private static void trainOut(int[] id, int i, Stack<Integer> s, String str, int n) {
        //i表示进站序列的位置，n表示出站序列的位置
        if(n==id.length){//终止条件 递归结束添加一行
            l.add(str);
        }

        if(!s.isEmpty()){//栈不为空表示还有火车尚未出站，或未满栈
            int temp = s.pop();//火车出战
            trainOut(id,i,s,str+temp+" ",n+1);
            s.push(temp);//恢复现场
        }

        if(i<id.length){//还有尚未进站的火车
            s.push(id[i]);//将火车推入栈
            trainOut(id,i+1,s,str,n);//看在这个状态下还有几种情况
            s.pop();//恢复现场
        }
    }
}