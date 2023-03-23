public class Main {
    public static void main(String[] args) {
        int[] nums = {6,9,10,21,5,4,11,13,5,20};
        quickSort(nums,0,nums.length-1);
        for(int i : nums){
            System.out.print(i);
            System.out.print(" ");
        }

    }

    private static void quickSort(int[] nums, int start, int end) {
        if(start > end){
            return;
        }
        int left = start;
        int right = end;
        int baseValue = nums[start];
        int temp;
        while(left<right){
            while(nums[right]>=baseValue && left<right){
                right--;
            }
            while(nums[left]<=baseValue && left<right){
                left++;
            }
            if(left<= right) {
                temp = nums[right];
                nums[right] = nums[left];
                nums[left] = temp;

            }
        }
        nums[start] = nums[left];
        nums[left] = baseValue;
        quickSort(nums,start,left-1);
        quickSort(nums,left+1,end);
    }
}