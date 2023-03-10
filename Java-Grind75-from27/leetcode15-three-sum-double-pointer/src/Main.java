
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
     /*
     给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，
     同时还满足 nums[i] + nums[j] + nums[k] == 0 。请你返回所有和为 0 且不重复的三元组。

      */
        int[] nums = {-1,0,1,2,-1,-4};
        List<List<Integer>> answer = threeSum(nums);
        System.out.println(answer);
    }

    private static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> lists = new ArrayList<>();
        for(int i=0;i<nums.length;i++){
            //排序后没有负数，那么这个数组怎么加都不会等于0
            if(nums[i]>0) return lists;
            //如果后一个值跟前一个值一样，如数组[-1,-1,0,2]，那么第二个-1的结果跟之前是一样的，去掉重复
            if(i>0 && nums[i]==nums[i-1]) continue;

            int left = i+1;
            int right = nums.length -1;
            while(left<right){
                if(nums[i]+nums[left]+nums[right]>0){
                    right--;
                }else if(nums[i]+nums[left]+nums[right]<0){
                    left++;
                }else{
                    List<Integer> l = new ArrayList<>();
                    l.add(nums[i]);
                    l.add(nums[left]);
                    l.add(nums[right]);
                    lists.add(l);

                    //将左指针和右指针移动前对左右指针进行判断
                    //如果重复，则不用计算

                    //因为i不变，当此时left取的数的值与前一个数相同，所以不用再计算，跳过这个left
                    while(left<right && nums[left+1]==nums[left]) left++;
                    //因为i不变，当此时right取的数的值与后一个数相同，所以不用再计算，跳过这个right
                    while(left<right && nums[right-1] == nums[right]) right--;

                    //完成操作后左右移动指针，继续寻找当i不变时可能存在的其他和为0的数组
                    left++;
                    right--;
                }
            }
        }
        return lists;
    }
}