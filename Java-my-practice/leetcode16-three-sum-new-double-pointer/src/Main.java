
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] nums = {0, 3, 97, 102,200};
        int target = 300;
        System.out.println(threeSumClosest(nums, target));
    }

    private static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int left;
        int right;
        int res = 1000001;
        for (int i = 0; i < nums.length; i++) {
            //排除重复项
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            //每一次进入之后重新赋值left跟right
            left = i + 1;
            right = nums.length - 1;
            while (left < right) {
                int temp = nums[i] + nums[left] + nums[right];

                //如果找到结果 直接跳出
                if(temp==target){
                    return target;
                }

                //储存最接近的结果
                if (Math.abs(temp - target) < Math.abs(res - target)) {
                    res = temp;
                }

                if (temp > target) {
                    right--;
                    while (left < right && nums[right] == nums[right + 1]) {
                        right--;
                    }
                } else {
                    left++;
                    while (left<right && nums[left] == nums[left-1]){
                        left++;
                    }
                }

            }
        }
        return res;
    }
}