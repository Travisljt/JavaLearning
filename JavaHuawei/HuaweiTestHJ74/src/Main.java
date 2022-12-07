import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
       在命令行输入如下命令：

        xcopy /s c:\\ d:\\e，

        各个参数如下：

        参数1：命令字xcopy

        参数2：字符串/s

        参数3：字符串c:\\

        参数4: 字符串d:\\e

        请编写一个参数解析程序，实现将命令行各个参数解析出来。
        对于用""包含起来的参数，如果中间有空格，不能解析为多个参数。
        比如在命令行输入xcopy /s "C:\\program files" "d:\"时，参数仍然是4个，
        第3个参数应该是字符串C:\\program files，而不是C:\\program，
        注意输出参数时，需要将""去掉，引号不存在嵌套情况。
         */
        Scanner sc  = new Scanner(System.in);
        String nextLine = sc.nextLine();
        StringBuilder sb = new StringBuilder();
        ArrayList<String> arr = new ArrayList<>();
        boolean flag = false;
        for(int i=0;i<nextLine.length();i++){
            char c = nextLine.charAt(i);
            if(String.valueOf(c).equals("\"")){
                flag = !flag;
                continue;
            }

            if(String.valueOf(c).equals(" ") && !flag){
                arr.add(sb.toString());
                sb = new StringBuilder();
            }else{
                sb.append(c);
            }
        }
        arr.add(sb.toString());
        System.out.println(arr.size());
        for(String s : arr){
            System.out.println(s);
        }
    }
}