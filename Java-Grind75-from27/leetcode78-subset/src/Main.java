import java.util.*;

public class Main {
    public static void main(String[] args) {

    }
    static List<Integer> list = new ArrayList<>();
    static List<List<Integer>> ans = new ArrayList<>();
    public static List<List<Integer>> subsets(int[] nums) {
        dfs(0,nums);
        return ans;
    }

    public static void dfs(int current, int[] nums){
        if(current==nums.length){
            ans.add(new ArrayList<>(list));
            return;
        }
        list.add(nums[current]);
        dfs(current+1,nums);
        list.remove(list.size()-1);
        dfs(current+1,nums);
    }

}