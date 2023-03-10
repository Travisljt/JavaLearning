import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int[] nums = {1,2,3};
        List<List<Integer>> res = permute(nums);
        System.out.println(res);
    }

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        boolean[] used = new boolean[nums.length];//需要一个标记数组，标记哪些用过了哪些没用过，回溯
        dfs(ans,path,nums,0,used);
        return ans;
    }

    private static void dfs(List<List<Integer>> ans, List<Integer> path, int[] nums, int depth, boolean[] used) {
        if(nums.length==0){
            return;
        }
        if(depth == nums.length){ //选完3个后即加进答案
            ans.add(new ArrayList<>(path));
        }

        for(int i=0;i<nums.length;i++){
            if(!used[i]){
                path.add(nums[i]);
                used[i] = true;
                dfs(ans,path,nums,depth+1,used);
                //回溯，从深层到回浅层，代码是对称的
                used[i] = false;
                path.remove(path.size()-1);
            }
        }

    }
}