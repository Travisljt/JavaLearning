import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        System.out.println(Arrays.toString(twoSum(nums, target)));
    }

    private static int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[]{};
        }
        Map<Integer, Integer> map = new HashMap<>();
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int ln0 = nums[left];
            int rn0 = nums[right];
            int ln1 = target - ln0;
            int rn1 = target - rn0;
            if (map.containsKey(ln1)) {
                return new int[]{left, map.get(ln1)};
            } else {
                map.put(ln0, left++);

            }
            if (map.containsKey(rn1)) {
                return new int[]{right,map.get(rn1)};
            } else {
                map.put(rn0, right--);
            }
        }
        return new int[]{};
    }
}