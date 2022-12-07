import java.util.*;

public class Main {
    public static void main(String[] args) {
        /*
        姓名编号排序：
        注：0代表从高到低，1代表从低到高

        输入：
        3
        0
        fang 90
        yang 50
        ning 70
        输出：
        fang 90
        ning 70
        yang 50

        输入：
        3
        1
        fang 90
        yang 50
        ning 70
        输出：
        yang 50
        ning 70
        fang 90

        对于基本类型的数组如int[], double[], char[] ,
        Arrays类只提供了默认的升序排列，没有降序，需要传入自定义比较器，
        使用Arrays.sort(num,c)，传入一个实现了Comparator接口的类的对象c。
        例如：
        Array.sort(num,new Comparator<Integer>(){
           public int compare(Integer a, Integer b){
                return b - a;
             }
         }

         如果使用lambda就更简单了，例如：
         Array.sort(nums,(Integer a, Integer b) -> { return b - a;});
         */
        Scanner sc = new Scanner(System.in);
        HashMap<Integer, String> map = new HashMap<>();
        while (sc.hasNextLine()) {
            int n = Integer.parseInt(sc.nextLine());
            int flag = Integer.parseInt(sc.nextLine());//1是升序，0是降序
            int[][] score = new int[n][2];//姓名编号，成绩
            for (int i = 0; i < n; i++) {
                String[] nameAndScore = sc.nextLine().split("\\s+");
                score[i][0] = i;
                score[i][1] = Integer.parseInt(nameAndScore[1]);
                map.put(i, nameAndScore[0]);
            }
            //o1 o2 分别为object对象
            Arrays.sort(score, (o1, o2) -> {
                if (flag == 0) {
                    return o2[1] - o1[1];//按第二列降序排列,如果相等的话，返回0，顺序不变
                } else {
                    return o1[1] - o2[1];//按第二列升序
                }
            });
            for (int i = 0; i < n; i++) {
                System.out.println(map.get(score[i][0]) + " " + score[i][1]);
            }
        }
    }
}