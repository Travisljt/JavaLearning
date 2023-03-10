public class Main {
    public static void main(String[] args) {
        int[] nums = {4,5,6,7,8,0,1,2,3};
        System.out.println(search(nums,0));
    }

    //二分查找
    public static int search(int[] nums, int target) {
        /*
        如果中间的数小于最右边的数，则右半段是有序的，若中间数大于最右边数，则左半段是有序的，
        我们只要在有序的半段里用首尾两个数组来判断目标值是否在这一区域内，这样就可以确定保留哪半边了
         */
        int n = nums.length;
        if (n == 0) {
            return -1;
        }
        if (n == 1) {
            return nums[0] == target ? 0 : -1;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            //二分法先分区域
            //这段表示左边有序，右边无序
            if(nums[mid]>=nums[left]){
                //值在左边（只需要管有序的一边判断，否则就直接else
                if(nums[mid]> target && target>=nums[left] ){
                    right = mid - 1;
                }else {
                    left = mid + 1;
                }
            }
            //左边无序，右边有序
            else {
                //值在右边
                if(nums[mid]< target && nums[right] >= target){
                    left = mid + 1;
                }else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }
}