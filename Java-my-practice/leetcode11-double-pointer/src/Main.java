

public class Main {
    public static void main(String[] args) {
        //题目为装更多的水
        //选择两块木板，以最短那边作为高度，两者之间距离为长度，相乘求出area
        String s = "1,8,6,2,5,4,8,3,7";
        String[] ss = s.split(",");
        int[] height = new int[ss.length];
        for(int i=0;i<ss.length;i++){
            height[i] = Integer.parseInt(ss[i]);
        }
        System.out.println(maxArea(height));
    }

    private static int maxArea(int[ ] height) {
        int left = 0;
        int right = height.length-1;
        int max = 0;
        while (left<right){
            if(height[left]>height[right]){
                int tmp = height[right] * (right - left);
                max = Math.max(max,tmp);
                right--;
            }else{
                int tmp = height[left] * (right - left);
                max = Math.max(max,tmp);
                left++;
            }
        }
        return max;
    }
}