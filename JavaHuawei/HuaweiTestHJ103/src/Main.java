import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    /*
    Redraiment是走梅花桩的高手。Redraiment可以选择任意一个起点，
    从前到后，但只能从低处往高处的桩子走。
    他希望走的步数最多，你能替Redraiment研究他最多走的步数吗？
    ***求最长子序列****

    输入：
    6
    2 5 1 5 4 5

    输出：
    3
    说明：
    6个点的高度各为 2 5 1 5 4 5
    如从第1格开始走,最多为3步, 2 4 5 ，下标分别是 1 5 6
    从第2格开始走,最多只有1步,5
    而从第3格开始走最多有3步,1 4 5， 下标分别是 3 5 6
    从第5格开始走最多有2步,4 5， 下标分别是 5 6
    所以这个结果是3。
     */
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int len = sc.nextInt();
            int[] nums = new int[len];
            for (int i = 0; i < len; i++) {
                nums[i] = sc.nextInt();
            }
            System.out.println(count(nums));
        }
    }

    private static int count(int[] nums) {
        int[] dp = new int[nums.length + 1];
        Arrays.fill(dp, 1);
        int max = 1;
        for (int i = 1; i < nums.length; i++ ) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
                max = Math.max(dp[i], max);
            }
        }
        return max;
    }
}