import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        //给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
        //
        //题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
        //
        //请不要使用除法，且O(n) 时间复杂度内完成此题。
        //
        int[] nums = {1,2,3,4};
        System.out.println(Arrays.toString(productExceptSelf(nums)));

    }

    private static int[] productExceptSelf(int[] nums) {
        //空间复杂度O(N) 的左右相乘法
//        int len = nums.length;
//        int[] left = new int[len];
//        int[] right = new int[len];
//        left[0] = 1;
//        right[len-1] = 1;
//        for(int i=1;i<len;i++){
//            left[i] = nums[i-1] * left[i-1];
//        }
//        for(int i=len-2;i>=0;i--){
//            right[i] = nums[i+1] * right[i+1];
//        }
//        int[] ans = new int[len];
//        for(int i=0;i<len;i++){
//            ans[i] = right[i] * left[i];
//        }
//        return ans;


        //进阶优化 空间复杂度O(1)
        int[] answer = new int[nums.length];
        answer[0] = 1;
        for(int i=1;i<nums.length;i++){
            answer[i] = nums[i-1] * answer[i-1];
        }
        int r = 1;
        for(int i=nums.length-1;i>=0;i--){
            //在前缀求和的answer中 直接在循环每一步与r相乘，并更新r的值
            answer[i] = answer[i] * r;
            r = r * nums[i];
        }
        return answer;

    }
}