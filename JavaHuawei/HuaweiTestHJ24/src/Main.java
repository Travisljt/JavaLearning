import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        通俗来说，能找到一个同学，他的两边的同学身高都依次严格降低的队形就是合唱队形。
        例子：
        123 124 125 123 121 是一个合唱队形
        123 123 124 122不是合唱队形，因为前两名同学身高相等，不符合要求
        123 122 121 122不是合唱队形，因为找不到一个同学，他的两侧同学身高递减。

        你的任务是，已知所有N位同学的身高，计算最少需要几位同学出列，可以使得剩下的同学排成合唱队形。
        注意：不允许改变队列元素的先后顺序 且 不要求最高同学左右人数必须相等

        输入描述：
        用例两行数据，第一行是同学的总数 N ，第二行是 N 位同学的身高，以空格隔开

        输出描述：
        最少需要几位同学出列
         */

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }
            int[] left = new int[n];
            int[] right = new int[n];
            left[0] = 1;
            right[n - 1] = 1;
            for (int i = 0; i < n; i++) {//从左往右最大递增序列
                left[i] = 1;
                for (int j = 0; j < i; j++) {//从0开始比较，将最大的递增数存在left对应的位置上
                    if (arr[i] > arr[j]) {
                        //+1是因为包括第一个人，计算是从1开始而不是从0
                        left[i] = Math.max(left[j] + 1, left[i]);
                    }
                }
            }
            for (int i = n - 1; i >= 0; i--) {
                right[i] = 1;
                for (int j = n - 1; j > i; j--) {//从n-1开始算最大递减数列存在right对应的位置上
                    if (arr[i] > arr[j]) {
                        right[i] = Math.max(right[i], right[j] + 1);
                    }
                }
            }

            int[] result = new int[n];
            for (int i = 0; i < n; i++) {
                //因为位置i计算了两次，所以要减一
                result[i] = left[i] + right[i] - 1;
            }
            int max = 1;//max设置0或1都可以，因为后面需要比较max大小
            for (int i = 0; i < n; i++) {
                max = Math.max(result[i], max);
            }
            System.out.println(n - max);//返回所有人数-最大增减序列人数=需要出队的人数
        }




    }
}