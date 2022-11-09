import java.util.*;

public class Main {
    public static void main(String[] args) {

        //HJ3
        //明明生成了NN个1到500之间的随机整数。请你删去其中重复的数字，即相同的数字只保留一个，
        //把其余相同的数去掉，然后再把这些数从小到大排序，按照排好的顺序输出。
        //数据范围：1≤n≤1000  ，输入的数字大小满足 1≤val≤500

        //输入描述：
        //第一行先输入随机整数的个数 N 。 接下来的 N 行每行输入一个整数，代表明明生成的随机数。 具体格式可以参考下面的"示例"。

        //输出描述：
        //输出多行，表示输入数据处理后的结果

        //示例
        //输入：
        //3
        //2
        //2
        //1
        //输出：
        //1
        //2

//        //自己写的hashtable 满分
//        Scanner in = new Scanner(System.in);
//
//        int n = in.nextInt();
//        ArrayList<Integer> arr = new ArrayList<>();
//        for(int i=0;i<n;i++){
//            arr.add(in.nextInt());
//        }
//
//        int[] table = new int[501];
//        for (Integer integer : arr) {
//            table[integer]++;
//        }
//
//        for(int i=0;i<table.length;i++){
//            if(table[i]>0){
//                System.out.printf("%d\n",i);
//            }
//        }

        //别人的Treeset
        Scanner sc = new Scanner(System.in);
        //获取个数
        int num = sc.nextInt();
        //创建TreeSet进行去重排序
        TreeSet<Integer> set = new TreeSet<>();
        //输入
        for(int i =0 ; i < num ;i++){
            set.add(sc.nextInt());
        }

        //输出
        for (Object o : set) {
            System.out.println(o);
        }



    }
}