import java.util.*;

public class Main {

    static int minCount = Integer.MAX_VALUE;

    public static void doi(Integer i){
        i = new Integer(2);
    }
    public static void main(String[] args) {
        //游戏中角色有N个技能，每个技能伤害是skill[i]（i<N),每个技能可以不限次释放，目标是一共造成M伤害（正好为M）。
        // 最少需要放多少次技能才能做到伤害正好为M？
        // 输入int[] skill(为每个技能伤害的集合），int n (为技能个数），
//        // int m （为一共需要造成的伤害量）。使用java语言并用深度优先算法写出以上算法
//        int[] skill = {1,5,3};
//        int allDamage = 10;
//        dfs(skill,allDamage,0,0);
//        System.out.println(minCount);
//        System.out.println(minNum(skill,allDamage));


    }

    public static String[] solution(String[] flight_history, String[] flight_itineraries){
        if(flight_history.length==0){

        }
        if(flight_itineraries.length==0){
            return new String[]{};
        }
        HashMap<String,String> airlineToDiscount = new HashMap<>();
        List<String> itineraries_airlineList = new ArrayList<>();
        List<String> itineraries_pricesList = new ArrayList<>();
        List<String> itineraries_classList = new ArrayList<>();


    }

    public static HashMap<String,String> getDiscount(String[] flight_history){
        List<String> history_milesList = new ArrayList<>();
        List<String> history_classList = new ArrayList<>();
        List<String> history_airlineList = new ArrayList<>();
        HashMap<String,String> hashMap = new HashMap<>();
        for(String s : flight_history){
            String[] strs = s.split("-");
            history_milesList.add(strs[2]);
            history_airlineList.add(strs[3]);
            history_classList.add(strs[4]);
        }
        
        for(int i=0;i<flight_history.length;i++){
            if(history_classList.get(i).equals("E")){
                double discount = (Double.parseDouble(history_milesList.get(i)) / 4.0) * 0.14;
                hashMap.put(history_airlineList.get(i),String.valueOf(discount));
            }else if(history_classList.get(i).equals("B")){
                double discount = Double.parseDouble(history_milesList.get(i)) * 0.14;
                hashMap.put(history_airlineList.get(i),String.valueOf(discount));
            }
        }
        return hashMap;
    }

    //我的回溯法
    public static void dfs(int[] skill, int allDamage, int count, int index){
        if(index == skill.length){
            return;
        }
        if(allDamage == 0){
            minCount = Math.min(minCount,count);
            return;
        }

        dfs(skill,allDamage,count,index+1);

        if(allDamage-skill[index] >= 0){
            count++;
            dfs(skill,allDamage-skill[index],count,index);
            count--;
        }
    }

    //bing的动态规划法
    public static int minNum(int[] skill, int target){
        int[] dp = new int[target+1];
        Arrays.fill(dp,target+1);
        dp[0] = 0;
        for (int k : skill) {
            for (int j = k; j <= target; j++) {
                dp[j] = Math.min(dp[j], dp[j - k] + 1);
            }
        }
        return dp[target] > target ? 0 : dp[target];
    }

    //bing的回溯法
    public static int minNum2(int[] skill, int target){
        return backtrack(skill,target,0);
    }

    private static int backtrack(int[] skill, int target, int count) {
        if(target==0){
            return count;
        }
        if(target<0){
            return Integer.MAX_VALUE;
        }
        int min123 = Integer.MAX_VALUE;
        for(int i=0;i<skill.length;i++){
            min123 = Math.min(min123,backtrack(skill,target-skill[i],count+1));
        }
        return min123;
    }

}