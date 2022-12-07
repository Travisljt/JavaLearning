import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        /*
          矩阵乘法的运算量与矩阵乘法的顺序强相关。
        例如：
        A是一个50×10的矩阵，B是10×20的矩阵，C是20×5的矩阵
        计算A*B*C有两种顺序：((AB)C)或者(A(BC))，前者需要计算15000次乘法，后者只需要3500次。
        编写程序计算不同的计算顺序需要进行的乘法次数。

        输入：
        3
        50 10
        10 20
        20 5
        (A(BC))

        输出：
        3500

         */
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            int[][] arr = new int[n][2];
            for (int i = 0; i < n; i++) {
                arr[i][0] = sc.nextInt();
                arr[i][1] = sc.nextInt();
            }
            int count = 0;
            String str = sc.next();
            char[] chars = str.toCharArray();
            Stack<Integer> s = new Stack<>();
            for (char aChar : chars) {
                //遇到左括号不做操作
                if (aChar == '(') {
                    continue;
                } else if (aChar == ')') {
                    int x = s.pop();
                    int y = s.pop();
                    int z = s.pop();//z = y
                    int m = s.pop();
                    count += x * y * m;
                    s.push(m);
                    s.push(x);
                } else {
                    s.push(arr[aChar - 'A'][0]);
                    s.push(arr[aChar - 'A'][1]);
                }
            }
            System.out.println(count);
        }


    }
}