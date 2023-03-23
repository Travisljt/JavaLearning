import java.lang.reflect.Array;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] candidates = {10,1,2,7,6,1,5};
        int target = 8;
        List<List<Integer>> ans = combinationSum2(candidates,target);
        for(List<Integer> list : ans){
            for(int i : list){
                System.out.print(i);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if(candidates==null || candidates.length==0){
            return ans;
        }
        Deque<Integer> combine = new ArrayDeque<>();
        Arrays.sort(candidates);
        dfs(ans,combine,target,0,candidates);
        return ans;
    }

    public static void dfs(List<List<Integer>> ans, Deque<Integer> combine, int target, int index,int[] candidates){
        if(target==0){
            ans.add(new ArrayList<>(combine));
            return;
        }

        for(int i=index;i<candidates.length;i++){
            if(target-candidates[i]<0){
                break;
            }
            if(i>index && candidates[i]==candidates[i-1]){
                continue;
            }
            combine.addLast(candidates[i]);
            dfs(ans,combine,target-candidates[i],i+1,candidates);
            combine.removeLast();
        }
    }
}