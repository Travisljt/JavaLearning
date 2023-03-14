import java.util.*;

public class Main {

    static int minCount = Integer.MAX_VALUE;
    public static void main(String[] args) {
        //游戏中角色有N个技能，每个技能伤害是skill[i]（i<N),每个技能可以不限次释放，目标是一共造成M伤害（正好为M）。
        // 最少需要放多少次技能才能做到伤害正好为M？
        // 输入int[] skill(为每个技能伤害的集合），int n (为技能个数），
        // int m （为一共需要造成的伤害量）。使用java语言并用深度优先算法写出以上算法
        int[] skill = {1,5,3};
        int allDamage = 10;
        dfs(skill,allDamage,0,0);
        System.out.println(minCount);
        System.out.println(minNum(skill,allDamage));


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