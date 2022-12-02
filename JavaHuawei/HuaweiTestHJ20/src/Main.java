import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        //密码要求:
        //1.长度超过8位
        //2.包括大小写字母.数字.其它符号,以上四种至少三种
        //3.不能有长度大于2的包含公共元素的子串重复 （注：其他符号不含空格或换行）

        //如果符合要求输出：OK，否则输出NG

        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            String str = sc.next();
            if(str.length()<=8){
                System.out.println("NG");
                continue;
            }
            if(getMatches(str)){
                System.out.println("NG");
                continue;
            }
            if(getStr(str,0,3)){
                System.out.println("NG");
                continue;
            }
            System.out.println("OK");
        }

    }

    private static boolean getStr(String str, int l, int r) {
        if(r>=str.length()){
            return false;
        }
        if(str.substring(r).contains(str.substring(l,r))){
            return true;
        }else {
            return getStr(str,l+1,r+1);//通过向后递归发现是否有三个重复字串
            //也可以通过正则 ： Pattern.compile("^.*(.{3,}).*\\1.*$").matcher(s).find()
        }
    }


    private static boolean getMatches(String str) {//判断是否含有大小写数字或其他符号
        int count = 0;
        Pattern p1 = Pattern.compile("[A-Z]");
        if(p1.matcher(str).find()){
            count++;
        }
        Pattern p2 = Pattern.compile("[a-z]");
        if(p2.matcher(str).find()){
            count++;
        }
        Pattern p3 = Pattern.compile("[0-9]");
        if(p3.matcher(str).find()){
            count++;
        }
        Pattern p4 = Pattern.compile("[^a-zA-Z0-9]");//其他符号
        if(p4.matcher(str).find()){
            count++;
        }
        return count<3;//至少三种以上
    }
}