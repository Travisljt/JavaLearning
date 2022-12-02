import java.util.*;

public class Main {

    public static void main(String[] args) {
        /*
        描述
        题目描述
        若两个正整数的和为素数，则这两个正整数称之为“素数伴侣”，如2和5、6和13，它们能应用于通信加密。现在密码学会请你设计一个程序，从已有的 N （ N 为偶数）个正整数中挑选出若干对组成“素数伴侣”，挑选方案多种多样，例如有4个正整数：2，5，6，13，如果将5和6分为一组中只能得到一组“素数伴侣”，而将2和5、6和13编组将得到两组“素数伴侣”，能组成“素数伴侣”最多的方案称为“最佳方案”，当然密码学会希望你寻找出“最佳方案”。
        输入:
        有一个正偶数 n ，表示待挑选的自然数的个数。后面给出 n 个具体的数字。

        输出:
        输出一个整数 K ，表示你求得的“最佳方案”组成“素数伴侣”的对数。

        输入描述：
        输入说明
        1 输入一个正偶数 n
        2 输入 n 个整数

        输出描述：
        求得的“最佳方案”组成“素数伴侣”的对数。
         */

        //匈牙利算法
        /*
        首先定义两个list容器，分别存储输入整数中的奇数和偶数。
        然后利用匈牙利算法找到“素数伴侣”对数最多时的配对数。匈牙利算法的核心思想是先到先得，能让就让。
        最后输出“素数伴侣”最多时的对数
         */
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int[] arr = new int[n];
            List<Integer> odds = new ArrayList<>();
            List<Integer> evens = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
                //判断奇数偶数
                if(arr[i]%2==1){
                    odds.add(arr[i]);
                }
                if(arr[i]%2==0){
                    evens.add(arr[i]);
                }
            }
            //matchEven下标对应已经匹配的偶数下标，值对应这个偶数的伴侣
            int[] matchEven = new int[evens.size()];
            //记录伴侣对数
            int count =0;
            for (Integer odd : odds) {
                //用于标记对应的偶数是否已经查找过
                boolean[] flag = new boolean[evens.size()];
                if (canFind(odd, matchEven, evens, flag)) {//匹配上就加1
                    count++;
                }
            }
            System.out.println(count);
        }
    }

    //判断奇数x是否能找到伴侣
    private static boolean canFind(int integer, int[] matchEven, List<Integer> evens, boolean[] flag) {
        for(int i=0;i<evens.size();i++){
            //该位置的偶数没有被访问过并且能与integer组成素数伴侣
            if(isPrime(integer + evens.get(i) )&& !flag[i]){
                flag[i] = true;//访问
                //如果i位置偶数没有伴侣，则与integer形成伴侣；如果已经拥有伴侣，且这个伴侣能够重新找到新的伴侣，
                //则把原来的伴侣让给别人，自己与integer组成伴侣
                if(matchEven[i]==0 || canFind(matchEven[i],matchEven,evens,flag)){
                    matchEven[i] = integer;
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isPrime(int i) {
        //判断是否为素数的基本方法
        if(i==1) return false;
        int sqrt = (int)Math.sqrt(i);
        for(int j=2;j<=sqrt;j++){
            if(i%j==0){
                return false;
            }
        }
        return true;
    }
}