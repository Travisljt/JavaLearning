import java.util.Scanner;

public class Main {
    static char[] bigNum = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
    static char[] unit = {' ', '拾', '佰', '仟'};
    static char[] unit2 = {'元', '万', '亿'};

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Double money = Double.valueOf(
                in.nextLine());   //用double 以防输入的数字是0开头的
        System.out.println(convert(String.valueOf(money)));
    }
    public  static String convert(String money) {
        StringBuilder res = new StringBuilder();  //最后结果
        //先添加人民币
        res.append("人民币");
        String[] moneyForConvert =
                money.split("\\.");    //分成两部分整数部分和小数部分
        res.append(convertBig(
                moneyForConvert[0]));    //  分别处理小数还有整数部分
        res.append(convertSmall(moneyForConvert[1]));
        return res.toString();
    }
    //处理整数
    public static String convertBig(String big) {
        if (big.equals("0")) return ""; //说明是小数
        StringBuilder res = new StringBuilder();
        int index = 0;
        while (big.length() > 0) {
            String cur = big.length() > 4 ? big.substring(big.length() - 4) : big;
            res.insert(0, readNum(cur) + unit2[index++]);
            big = big.length() > 4 ? big.substring(0, big.length() - 4) : "";
        }
        //返回
        return res.toString();
    }
    //分成四位返回
    public  static String readNum(String s) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            //先加数字后添加单位
            int u = s.length() - 1 - i;
            int b = Character.getNumericValue(s.charAt(i));
            //进行拦截以防多加0
            if (b == 0 && i + 1 < s.length() && s.charAt(i + 1) == '0') {
                continue;
            }
            //如果是10那就不加1
            if (!(b == 1 && i + 1 < s.length() && u == 1)) res.append(bigNum[b]);
            if (b != 0) res.append(unit[u]);
        }
        String resNum = res.toString().trim();
        if (resNum.charAt(resNum.length() - 1) == '零') resNum = resNum.substring(0,
                resNum.length() - 1);
        return resNum;
    }
    //处理小数
    public static String convertSmall(String small) {
        if (small.equals("0")) return "整"; //说明是整数
        int jNum = Character.getNumericValue(small.charAt(0));  //  角
        if (small.length() == 2) {
            int fNum = Character.getNumericValue(small.charAt(1));  //  分
            //分成两种情况，有分和没有分的
            if (jNum == 0) return bigNum[fNum] + "分";   //有分但是没有角
            else return bigNum[jNum] + "角" + bigNum[fNum] + "分";
        } else {
            return bigNum[jNum] + "角";
        }
    }
}