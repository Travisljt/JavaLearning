public class Main {
    public static void main(String[] args) {
        int[] nums = {2,3,1,1,4};
        System.out.println(jump(nums));
    }

    public static boolean canJump(int[] nums){
        int k = 0;
        for(int i =0;i<nums.length;i++){
            if(i>k) return false;
            k = Math.max(k,i+nums[i]);
        }
        return true;
    }

    //主要是题意表示一定能够抵达nums[n-1]
    public static int jump(int[] nums){
        int border =0;
        int ans = 0;
        int maxP = 0;
        for(int i=0;i<nums.length-1;i++){
            maxP = Math.max(maxP,i+nums[i]);
            if(i==border){
                border = maxP;
                ans++;
            }
        }
        return ans;
    }
}