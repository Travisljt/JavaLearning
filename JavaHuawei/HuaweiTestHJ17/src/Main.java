import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //输入：
        //合法坐标为A(或者D或者W或者S) + 数字（两位以内）
        //坐标之间以;分隔。
        //非法坐标点需要进行丢弃。如AA10;  A1A;  $%$;  YAD; 等。
        //下面是一个简单的例子 如：
        //A10;S20;W10;D30;X;A1A;B10A11;;A10;
        // 处理过程：
        //起点（0,0）
        //+   A10   =  （-10,0）
        //+   S20   =  (-10,-20)
        //+   W10  =  (-10,-10)
        //+   D30  =  (20,-10)
        //+   x    =  无效
        //+   A1A   =  无效
        //+   B10A11   =  无效
        //+  一个空 不影响
        //+   A10  =  (10,-10)
        //结果 （10， -10）
        String str = "";
        while (sc.hasNext()) {
            str = sc.next();
        }
        String[] strs = str.split(";");
        int x = 0;
        int y = 0;

        for(String s : strs){
            if(!s.matches("([WASD])([0-9]{1,2})")){//匹配WASD紧接着0-9任意数字+1到2位数
                continue;
            }
            int val = Integer.parseInt(s.substring(1));//1位置为字母跟数字的中线，即W|10，取10
            switch (s.charAt(0)){//这是java 17的功能，不要改， java 1.8未必支持
                case 'W':
                    y += val;
                    break;
                case 'S':
                    y -= val;
                    break;
                case 'A':
                    x -= val;
                    break;
                case 'D':
                    x += val;
                    break;
            }
        }

        System.out.println(x + "," + y);

    }
}