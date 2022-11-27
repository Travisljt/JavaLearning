import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //请解析IP地址和对应的掩码，进行分类识别。要求按照A/B/C/D/E类地址归类，不合法的地址和掩码单独归类。
        //
        //所有的IP地址划分为 A,B,C,D,E五类
        //A类地址从1.0.0.0到126.255.255.255;
        //B类地址从128.0.0.0到191.255.255.255;
        //C类地址从192.0.0.0到223.255.255.255;
        //D类地址从224.0.0.0到239.255.255.255；
        //E类地址从240.0.0.0到255.255.255.255
        //
        //私网IP范围是：
        //从10.0.0.0到10.255.255.255
        //从172.16.0.0到172.31.255.255
        //从192.168.0.0到192.168.255.255
        //
        //子网掩码为二进制下前面是连续的1，然后全是0。（例如：255.255.255.32就是一个非法的掩码）
        //（注意二进制下全是1或者全是0均为非法子网掩码）
        //注意：
        //1. 类似于【0.*.*.*】和【127.*.*.*】的IP地址不属于上述输入的任意一类，也不属于不合法ip地址，计数时请忽略
        //2. 私有IP地址和A,B,C,D,E类地址是不冲突的

        //输入示例：
        //10.70.44.68~255.254.255.0
        //1.0.0.1~255.0.0.0
        //192.168.0.2~255.255.255.0
        //19..0.~255.255.255.0

        //输出
        //1 0 1 0 0 2 1
        Scanner sc = new Scanner(System.in);
        int aNum = 0;//a类
        int bNum = 0;//b类
        int cNum = 0;//c类
        int dNum = 0;//d类
        int eNum = 0;//e类
        int errNum = 0;//错误类
        int pNum = 0;//私有类
        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            String[] strArr = str.split("~");
            int ipFirst = getIpSeg(strArr[0], 0);
            if (ipFirst == 0 || ipFirst == 127) { //查看ip第一段是否为“0”或“127”，若是忽略；
                continue;
            }
            if (maskIsInvalid(strArr[1])) {
                //判断子网掩码是否合法，如果满足下列条件之一即为非法掩码
                //数字段数不为4
                //网关特点：
                //在二进制下，不满足前面连续是1，然后全是0
                //在二进制下，全为0或全为1也是非法子网掩码

                errNum++;
                continue;
            }
            if (ipIsInvalid(strArr[0])) {
                //判断IP地址是否合法，如果满足下列条件之一即为非法地址
                //数字段数不为4，比如存在空段，即【192..1.0】这种；
                //某个段的数字大于255
                errNum++;
                continue;
            }

            if(ipFirst >=1 && ipFirst <=126){
                aNum++;
            }
            if(ipFirst >= 128 && ipFirst<=191){
                bNum++;
            }
            if (ipFirst >= 192 && ipFirst <= 223) {
                cNum++;
            }
            if (ipFirst >= 224 && ipFirst <= 239) {
                dNum++;
            }
            if (ipFirst >= 240 && ipFirst <= 255) {
                eNum++;
            }
            //ipSecond是第二个数字
            //判断ip是否私有地址。 私网IP范围是：
            // 10.0.0.0～10.255.255.255
            // 172.16.0.0～172.31.255.255
            // 192.168.0.0～192.168.255.255
            int ipSecond = getIpSeg(strArr[0],1);
            if (ipFirst == 10 || (ipFirst == 172 && ipSecond >= 16 && ipSecond <=31) || (ipFirst == 192 && ipSecond == 168)) {
                pNum++;
            }
        }
        System.out.println(aNum + " " + bNum + " " + cNum + " " + dNum + " " + eNum + " " + errNum + " " + pNum);
    }

    private static boolean ipIsInvalid(String ip) {
        String[] ipArr = ip.split("\\.");
        if (ipArr.length != 4) {
            return true;
        }
        //大于255非法
        return Integer.parseInt(ipArr[0]) > 255 || Integer.parseInt(ipArr[1]) > 255 || Integer.parseInt(ipArr[2]) > 255 || Integer.parseInt(ipArr[3]) > 255;
    }

    private static boolean maskIsInvalid(String mask) {
        String[] maskArr = mask.split("\\.");
        if (maskArr.length != 4) {
            return true;
        }
        //网关必须得为先1后0
        String maskBinary = toBinary(maskArr[0]) + toBinary(maskArr[1]) + toBinary(maskArr[2]) + toBinary(maskArr[3]);
        return !maskBinary.matches("1+0+");
    }

    private static String toBinary(String num) {
        StringBuilder numBinary = new StringBuilder(Integer.toBinaryString(Integer.parseInt(num)));
        while (numBinary.length() < 8) {
            numBinary.insert(0, "0");
        }
        return numBinary.toString();
    }

    private static int getIpSeg(String ip, int index) {
        String[] ipArr = ip.split("\\.");
        return Integer.parseInt(ipArr[index]);
    }
}