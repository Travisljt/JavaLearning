import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] nums = {2,0,1,1,2,0};
        sortColors(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static  void sortColors(int[] nums){
//        if(nums.length==0){
//            return;
//        }
//        int[] saveNums = new int[3];
//        for(int i : nums){
//            switch (i){
//                case 0:saveNums[0]++;break;
//                case 1:saveNums[1]++;break;
//        case 2:saveNums[2]++;break;
//    }
//}
//        Arrays.fill(nums,0);
//                for(int i =saveNums[0];i<saveNums[0]+saveNums[1];i++){
//        nums[i] = 1;
//        }
//        for(int i = saveNums[1]+saveNums[0];i<nums.length;i++){
//        nums[i] = 2;
//        }

        if(nums.length<2){
            return;
        }

        /*
         all in [0, p0) = 0
         all in [p0,i) = 1
         all in [p2,len -1] = 2;
         */

        int p0 = 0;
        int i = 0;
        int p2 = nums.length-1;

        while(i<=p2){
            if(nums[i]==0){
                swap(nums,i,p0);
                i++;
                p0++;
            }else if(nums[i]==1){
                i++;
            }else {
                swap(nums,i,p2);
                p2--;
            }
        }

    }

    public static void swap(int[] nums, int i, int j){
        int tmp= nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}