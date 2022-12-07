import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] NUMS = {"zero", "one", "two", "three", "four", "five", "six", "seven",
                "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen",
                "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};
        String[] NUMSSHI = {"zero", "ten", "twenty", "thirty", "forty", "fifty",
                "sixty", "seventy", "eighty", "ninety"};
        String[] POWER = {"", "hundred", "thousand", "million", "billion"};
        while(in.hasNext()){
            String line = in.nextLine();
            StringBuilder sb = new StringBuilder();
            ArrayList<String> lists = new ArrayList<>();
            if(!line.matches("\\d+")){//如果匹配的不是数字
                System.out.println("error");
            }
            int linenum = Integer.parseInt(line);
            int power = 1;//单位
            while(linenum != 0){
                if(power != 1){
                    lists.add(POWER[power]);//添加单位
                }
                int t = linenum % 1000;//取低三位
                //注意小于20，直接读
                if(t % 100 <= 20){
                    if(t % 100 != 0){//十位和个位是零的话就不需要读数了
                        lists.add(NUMS[t % 100]);
                    }
                    if(t / 100 != 0){//有百位
                        if(t % 100 != 0){//十位和个位是零的话就不需要添加and了
                            lists.add("and");
                        }
                        lists.add("hundred");
                        lists.add(NUMS[t / 100]);
                    }
                }else{//大于20
                    // 有个位
                    if(t % 10 != 0){
                        lists.add(NUMS[t % 10]);
                    }
                    t /= 10;
                    // 有十位
                    if(t % 10 != 0){
                        lists.add(NUMSSHI[t % 10]);
                    }
                    t /= 10;
                    // 有百位
                    if(t % 10 != 0){
                        lists.add("and");
                        lists.add("hundred");
                        lists.add(NUMS[t % 10]);
                    }
                }
                linenum /= 1000;//每次缩小1000倍
                power++;//单位*1000
            }
            //添加的时候，先添加低位，读数的时候先读高位，倒着读
            for(int i = lists.size() - 1; i >= 0; i--){
                if(i!=0){
                    sb.append(lists.get(i)).append(" ");
                }else{
                    sb.append(lists.get(i));//最后一个不加空格
                }
            }
            System.out.println(sb.toString());
        }
    }
}
