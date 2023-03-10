import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    /*
    定义递归函数dfs（target，combine，index）表示在当前candidates数组的第index位下，还剩下target要组合，已经组合的列表为combine
    递归的终止条件为target<=0 或 candidate数组被全部用完。那么在当前函数中，我们可以执行dfs（target，combine，index+1）跳过不用index这个数
    或者选择第index数，执行（target-candidate[index]，combine，index）。因为数组中的数可以无限重复使用，所以下标依旧为index
     */
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> combine = new ArrayList<>();
        dfs(candidates,target,ans,combine,0);
        return ans;
    }

    private static void dfs(int[] candidates, int target, List<List<Integer>> ans, List<Integer> combine, int index) {
        if(index == candidates.length){
            return;
        }
        if(target == 0){
            ans.add(new ArrayList<>(combine));
            return;
        }
        //跳过当前这个index
        dfs(candidates,target,ans,combine,index+1);

        //选择当前数进行计算
        if(target - candidates[index] >=0){
            combine.add(candidates[index]);
            dfs(candidates,target-candidates[index],ans,combine,index);
            combine.remove(combine.size()-1);//选择完后清空combine
        }
    }
}