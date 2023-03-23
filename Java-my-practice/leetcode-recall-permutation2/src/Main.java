import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        int[] nums = {1,1,2};
        List<List<Integer>> list = permuteUnique(nums);
        for(List<Integer> ans : list){
            for(int i : ans){
                System.out.print(i);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        if(nums==null || nums.length==0){
            return ans;
        }
        List<Integer> combine = new ArrayList<>();
        Arrays.sort(nums);
        boolean[] visited = new boolean[nums.length];
        dfs(ans,combine,visited,0,nums);
        return ans;
    }

    public static void dfs(List<List<Integer>> ans, List<Integer> combine,boolean[] visited, int index, int[] nums){
        if(index==nums.length){
            ans.add(new ArrayList<>(combine));
            return;
        }
        for(int i = 0;i<nums.length;i++){
            if(visited[i]){
                continue;
            }
            if(i>0 && nums[i]==nums[i-1] && !visited[i-1]){
                continue;
            }
            combine.add(nums[i]);
            visited[i]= true;
            dfs(ans,combine,visited,index+1,nums);
            visited[i] = false;
            combine.remove(combine.size()-1);
        }
    }
}