import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Map<String, Integer> map = new HashMap<>() {{
        put("2", 2);
        put("3", 3);
        put("4", 4);
        put("5", 5);
        put("6", 6);
        put("7", 7);
        put("8", 8);
        put("9", 9);
        put("10", 10);
        put("J", 11);
        put("Q", 12);
        put("K", 13);
        put("A", 1);
    }};

    public static void main(String[] args) {
        /*
        24点游戏 但是是用扑克牌

        输入：
        4 2 K A
        输出：
        K-A*4/2
        说明：
        A+K*2-4也是一种答案，输出任意一种即可

        不能凑成输出“NONE”，存在大小王则输出“ERROR”

        ****存在问题，ACM能跑过，但idea跑不过
         */
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        if (str.contains("joker")) {
            System.out.println("ERROR");
        } else {
            if (!myDFS(str.split(" "), 0, "", 0)) {
                System.out.println("NONE");
            }
        }
    }

    private static boolean myDFS(String[] nums, int res, String exp, int n) {
        for (int k = 0; k < nums.length; k++) {
            String temp = nums[k];
            if (!temp.equals("")) {
                nums[k] = ""; //已经使用过的牌标记为空字符串
                int a = map.get(temp);
                if (n == 0) {
                    if (myDFS(nums, a, exp + temp, n + 1) || myDFS(nums, a, exp + temp, n + 1) || myDFS(nums, a, exp + temp, n + 1) || myDFS(nums, a, exp + temp, n + 1) || myDFS(nums, a, exp + temp, n + 1))
                    {
                        return true;
                    }
                } else {
                    if (myDFS(nums, res + a, exp + "+" + temp, n + 1) ||
                            myDFS(nums, res - a, exp + "-" + temp, n + 1) ||
                            myDFS(nums, res * a, exp + "*" + temp, n + 1) ||
                            myDFS(nums, res / a, exp + "/" + temp, n + 1)) {
                        return true;
                    }
                }
                nums[k] = temp; //恢复现场
            }
        }
        if (res == 24 && n == nums.length) {
            System.out.println(exp);
            return true;
        }

        return false;
    }
}