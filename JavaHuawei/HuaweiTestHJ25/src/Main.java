import java.util.Scanner;
import java.util.Set;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        /*
        输入：
        15 123 456 786 453 46 7 5 3 665 453456 745 456 786 453 123
        5 6 3 6 3 0

        输出：
        30 3 6 0 123 3 453 7 3 9 453456 13 453 14 123 6 7 1 456 2 786 4 46 8 665 9 453456 11 456 12 786
        说明：
        将序列R：5,6,3,6,3,0（第一个5表明后续有5个整数）排序去重后，可得0,3,6。
        序列I没有包含0的元素。
        序列I中包含3的元素有：I[0]的值为123、I[3]的值为453、I[7]的值为3、I[9]的值为453456、I[13]的值为453、I[14]的值为123。
        序列I中包含6的元素有：I[1]的值为456、I[2]的值为786、I[4]的值为46、I[8]的值为665、I[9]的值为453456、I[11]的值为456、I[12]的值为786。
        最后按题目要求的格式进行输出即可。
         */
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int iNum = sc.nextInt(); //记录第一个数据：i的个数
            //读取I
            String[] array = new String[iNum];
            for (int i =0; i<iNum; i++) {
                array[i] = String.valueOf(sc.nextInt());
            }
            //读取R
            int rNum = sc.nextInt();//记录第一个数据：r的个数
            Set<Integer> set = new TreeSet<>(); //使用TreeSet去重排序
            for (int i =0; i<rNum; i++) {
                set.add(sc.nextInt());
            }


            StringBuilder result = new StringBuilder();
            int count = 0;//记录符合的个数
            //遍历 R
            for (int i : set) {
                int j = 0;//记录I的每个元素的index
                Map<Integer, String> map = new TreeMap<>();
                //在I中寻找符合要求的I[i]
                for (String str : array) {
                    if (str.contains(String.valueOf(i))){
                        map.put(j, str);
                    }
                    j++;
                }
                //如果Map非空 则存入result中
                if (!map.isEmpty()) {
                    if (count > 0) {
                        result.append(" ");
                    }
                    result.append(i).append(" ").append(map.size());
                    count +=2;//多两个数组
                    for (Map.Entry<Integer, String> entry : map.entrySet()) {
                        count+=2;
                        result.append(" ").append(entry.getKey()).append(" ").append(entry.getValue());
                    };
                }
            }
            if (count > 0) {
                System.out.println(count + " " + result);
            }
        }
    }
}