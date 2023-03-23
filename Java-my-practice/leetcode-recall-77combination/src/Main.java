import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<List<Integer>> ans = combine(4,2);
        for(List<Integer> list : ans){
            for(int i : list){
                System.out.print(i);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static List<List<Integer>> combine(int n, int k){
        List<List<Integer>> ans = new ArrayList<>();
        if(n==0 || k==0){
            return ans;
        }
        List<Integer> combine = new ArrayList<>();
        dfs(ans,combine,1,n,k);
        return ans;
    }

    public static void dfs(List<List<Integer>> ans, List<Integer> combine, int index, int n, int k){
        if(combine.size()==k){
            ans.add(new ArrayList<>(combine));
            return;
        }
        for(int i=index;i<=n;i++){
            combine.add(i);
            dfs(ans,combine,i+1,n,k);
            combine.remove(combine.size()-1);
        }
    }
}