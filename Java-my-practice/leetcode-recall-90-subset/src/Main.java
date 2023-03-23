import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int[] nums = {1,2,2};
        List<List<Integer>> ans = subsetsWithDup(nums);
        for (List<Integer> list : ans) {
            for (int i : list) {
                System.out.print(i);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static List<List<Integer>> subsetsWithDup(int[] nums){
        List<List<Integer>> ans = new ArrayList<>();
        if(nums==null || nums.length==0){
            return ans;
        }
        List<Integer> combine = new ArrayList<>();
        Arrays.sort(nums);
        dfs(ans,combine,0,nums);
        return ans;
    }

    public static void dfs(List<List<Integer>> ans, List<Integer> combine, int begin, int[] nums){
        ans.add(new ArrayList<>(combine));

        for(int i = begin;i<nums.length;i++){
            if(i>begin && nums[i]==nums[i-1]){
                continue;
            }
            combine.add(nums[i]);
            dfs(ans,combine,i+1,nums);
            combine.remove(combine.size()-1);
        }
    }
}