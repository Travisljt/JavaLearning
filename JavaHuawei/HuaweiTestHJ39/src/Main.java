import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String mask = in.nextLine();
            String ip1 = in.nextLine();
            String ip2 = in.nextLine();
            if(!isVaildMask(mask) || !isVaildip(ip1) || !isVaildip(ip2)){
                System.out.println(1);
            }else if(isSameSonIp(mask,ip1,ip2)){
                System.out.println(0);
            }else{
                System.out.println(2);
            }
        }
    }
    public static boolean isSameSonIp(String mask,String ip1,String ip2){
        String[] maski = mask.split("\\.");
        String[] ip1i = ip1.split("\\.");
        String[] ip2i = ip2.split("\\.");
        for(int i=0;i<maski.length;i++){
            if( ( Integer.parseInt(maski[i]) & Integer.parseInt(ip1i[i]) ) !=
                    ( Integer.parseInt(maski[i]) & Integer.parseInt(ip2i[i]) ) ){
                return false;
            }
        }
        return true;
    }
    public static boolean isVaildip(String ip){
        String[] ipi = ip.split("\\.");
        if(ipi.length != 4){
            return false;
        }
        for(String ss:ipi){
            if(Integer.parseInt(ss) < 0 || Integer.parseInt(ss) > 255){
                return false;
            }
        }
        return true;
    }
    public static boolean isVaildMask(String mask){
        if(!isVaildip(mask)){
            return false;
        }
        String[] maski = mask.split("\\.");
        //将mask的每个网络号和主机号转化为2进制串，不足8位的前面补
        StringBuilder sb = new StringBuilder();
        for(String ss:maski){
            String binary = Integer.toBinaryString(Integer.parseInt(ss));
            binary = String.format("%08d",Integer.parseInt(binary));//不足的8位的补齐
            sb.append(binary);
        }
        //最后一个1在第一个0之前
        return sb.toString().lastIndexOf("1") < sb.toString().indexOf("0");
    }
}