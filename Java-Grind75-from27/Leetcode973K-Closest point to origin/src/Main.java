import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Main {
    Random rand = new Random();
    public static void main(String[] args) {
        int[][] test = {{1,3},{2,2}};
        int[][] kClosest1 = kClosest(test,1);
        for(int[] a : kClosest1){
            System.out.println(a[0]+" "+a[1]);
        }
    }

    public static int[][] kClosest(int[][] points, int k){
        int len = points.length;
        int[][] ans = new int[len][2];
        for(int i=0;i<len;i++){
            //平方跟开方转换很麻烦，不如就比较平方过后的值，且一定为int
            int temp1 = (int)Math.pow(points[i][0], 2);
            int temp2 = (int)Math.pow(points[i][1],2);
            ans[i][0] = temp1 + temp2;
            ans[i][1] = i;
        }
        Arrays.sort(ans, Comparator.comparingInt(o -> o[0]));
        int[][] realAns = new int[k][2];
        for(int i=0;i<k;i++){
            int temp = ans[i][1];
            realAns[i][0] = points[temp][0];
            realAns[i][1] = points[temp][1];
        }

        return realAns;

        //快速排序的方式
        /*
        int n = points.length;
        random_select(points, 0, n - 1, k);
        return Arrays.copyOfRange(points, 0, k);
         */
    }

    public void random_select(int[][] points, int left, int right, int k) {
        int pivotId = left + rand.nextInt(right - left + 1);
        int pivot = points[pivotId][0] * points[pivotId][0] + points[pivotId][1] * points[pivotId][1];
        swap(points, right, pivotId);
        int i = left - 1;
        for (int j = left; j < right; ++j) {
            int dist = points[j][0] * points[j][0] + points[j][1] * points[j][1];
            if (dist <= pivot) {
                ++i;
                swap(points, i, j);
            }
        }
        ++i;
        swap(points, i, right);
        // [left, i-1] 都小于等于 pivot, [i+1, right] 都大于 pivot
        if (k < i - left + 1) {
            random_select(points, left, i - 1, k);
        } else if (k > i - left + 1) {
            random_select(points, i + 1, right, k - (i - left + 1));
        }
    }

    public void swap(int[][] points, int index1, int index2) {
        int[] temp = points[index1];
        points[index1] = points[index2];
        points[index2] = temp;
    }
}