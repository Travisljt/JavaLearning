import java.util.*;

public class Main {
    private static int[] arr;//用于接收传入的4个整数
    private static int[] visited;//用于判断对应序号的整数有没有被使用。
    public static void main(String[] args) {
        /*
        24点游戏，输入4个数字【1-10】，问是否能够通过加减乘除运算得到数字24
        重复数字3 3 4 4 是合法输入，但每个3只用一次
         */
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            arr=new int[4];
            visited=new int[4];
            for(int i=0;i<4;i++){
                arr[i]=sc.nextInt();
            }
            System.out.println(canGet24(0,0));
        }
    }

    private static boolean canGet24(int cnt, double tmpres) {
        if(cnt==4 && tmpres==24){//如果用了4个了，且结果已经是24了，那就说明24运算成功。
            return true;
        }
        if(cnt==0){//对于还没开始运算的情况，接收的第一个数值直接作为tmpres
            for(int i=0;i<4;i++){
                visited[i]=1;
                if(canGet24(1,arr[i])){
                    return true;
                }
                visited[i]=0;//每一轮循环都要把访问记录恢复
            }
            return false;//所有数字都试过了还没有得到24，说明不可能再得到。
        }
        if(cnt==2){
            //对于已经在两个数值参加运算的情况，要考虑两种可能
            //1.另两个数字参加运算后再和当前结果运算。
            int a=0,b=0;//剩下两个数值取到a,b中
            for(int i=0;i<4;i++){
                if(visited[i]==0){
                    if(a==0){
                        a=arr[i];
                    }else{
                        b=arr[i];
                    }
                }
            }
            for(double n:getAnyRes(a,b)){//对所有可能的ab运算结果进行判断
                for(double m:getAnyRes(tmpres,n)){//对所有可能的tmpres和n的运算结果进行判断
                    if(m==24){
                        return true;
                    }
                }
            }
            //2.当前结果与第三个数值参加运算。
            for(int i=0;i<4;i++){
                if(visited[i]==0){
                    visited[i]=1;
                    if(canGet24(3,tmpres+arr[i])||canGet24(3,tmpres*arr[i])||//加和乘计算
                            canGet24(3,tmpres-arr[i])||canGet24(3,arr[i]-tmpres)){//减法计算
                        return true;
                    }
                    if(tmpres!=0 && canGet24(3,arr[i]/tmpres)
                            ||arr[i]!=0 && canGet24(3,tmpres/arr[i])){//除法计算
                        return true;
                    }
                    visited[i]=0;
                }
            }
            return false;//所有情况都试过了，还是没有24出现，返回false
        }
        if(cnt==1||cnt==3){
            for(int i=0;i<4;i++){
                if(visited[i]==0){
                    visited[i]=1;
                    if(canGet24(cnt+1,tmpres+arr[i])||canGet24(cnt+1,tmpres*arr[i])||//加和乘计算
                            canGet24(cnt+1,tmpres-arr[i])||canGet24(cnt+1,arr[i]-tmpres)){//减法计算
                        return true;
                    }
                    if(tmpres!=0 && canGet24(cnt+1,arr[i]/tmpres)||
                            arr[i]!=0 && canGet24(cnt+1,tmpres/arr[i])){//除法计算
                        return true;
                    }
                    visited[i]=0;
                }
            }
        }//不是1~4的返回false
        return false;
    }
    private static List<Double> getAnyRes(double a,double b){
        List<Double> res = new ArrayList<>();
        res.add(a+b);
        res.add(a*b);
        res.add(a-b);
        res.add(b-a);
        if(a!=0){
            res.add(b/a);
        }
        if(b!=0){
            res.add(a/b);
        }
        return res;
    }
}