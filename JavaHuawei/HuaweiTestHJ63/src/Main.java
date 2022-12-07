import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
     /*
        描述
        一个 DNA 序列由 A/C/G/T 四个字母的排列组合组成。 G 和 C 的比例（定义为 GC-Ratio ）是序列中 G 和 C 两个字母的总的出现次数除以总的字母数目（也就是序列长度）。在基因工程中，这个比例非常重要。因为高的 GC-Ratio 可能是基因的起始点。

        给定一个很长的 DNA 序列，以及限定的子串长度 N ，请帮助研究人员在给出的 DNA 序列中从左往右找出 GC-Ratio 最高且长度为 N 的第一个子串。
        DNA序列为 ACGT 的子串有: ACG , CG , CGT 等等，但是没有 AGT ， CT 等等
      */

        Scanner sc =new Scanner(System.in);
        while (sc.hasNext()){
            String str = sc.nextLine();
            int n = Integer.parseInt(sc.nextLine());
            double len = 0.0;
            String result = "";
            for(int i=0; i<str.length()-n+1;i++){
                String aim = str.substring(i,i+n);
                //将所有非CG替换成空字符
                String res = aim.replaceAll("[^CG]","");
                double cur = (double) res.length()/(double) n;
                if(cur>len){
                    len = cur;
                    result = aim;
                }
            }
            System.out.println(result);
        }
    }
}