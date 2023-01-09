
import java.util.Random;
import java.util.Scanner;

public class Main {
    static int count = 0;
    public static void main(String[] args) {
        /*
        抽奖程序：
        主体设置若干奖项，奖项获得数始终小于20
        因此可以假设一等奖两位，二等奖5位，三等奖7位；其余没获奖的人，获得安慰奖。
        ps：这种方式可以有效保证如果有其他人来，可以立刻安排更多的安慰奖红包以防万一

        第一步：先完成一个随机数抽取名字的程序，每抽取一个，便在hashMap中删除一个；
              可通过输入r来进行抽取，并打印名字
        第二步：实现满足调节，print出三等奖，然后实现第一步的程序，当满足7个之后，自动变成二等奖，依次类推
        第三步：允许中间加入其他名字，并进行抽奖
        第四步：窗体程序（需要查阅资料）
         */
        NameList list = new NameList();
        boolean flag = true;
        boolean check = true;
        do {
            if(count < 7){
                System.out.println("三等奖");
            }else if(count >=7 && count < 12){
                System.out.println("二等奖");
            }else if(count >=12 && count <15){
                System.out.println("一等奖");
            }else{
                System.out.println("奖品已经颁完");
                break;
            }

            System.out.println("开始抽奖：1");
            System.out.println("退出程序：0");
            System.out.println("增加名字：2");
            System.out.println("列出名字：3");
            Scanner sc = new Scanner(System.in);
            int key = sc.nextInt();
            switch (key) {
                case 3 -> {
                    list.printList();
                }
                case 2 -> {
                    System.out.println("请输入名字：");
                    String name = sc.next();
                    list.addNameInList(name);
                }
                case 1 -> {
                    check = luckDraw(list);
                    count++;
                }
                case 0 -> flag = false;
            }

        } while (flag && check);
        System.out.println("程序退出");
    }

    private static boolean luckDraw(NameList list) {
        if(list.getListSize()<1){
            System.out.println("表单已经空了");
            System.out.println();
            return false;
        }else{
            int key = randomSelect(list);
            String name = list.getSpecNameByID(key);
            System.out.println("中奖者:" + name);
            correctRemove(list,name);
            return true;
        }

    }

    private static int randomSelect(NameList list) {
        int num = list.getListSize();
        Random rand = new Random();
        return rand.nextInt(num);
    }

    private static void correctRemove(NameList nameList, String target){
        int size = nameList.getListSize();
        for(int i = size -1;i>=0;i--){
            String item = nameList.getSpecNameByID(i);
            if(target.equals(item)){
                nameList.removeNameByName(item);
            }
        }
    }


}