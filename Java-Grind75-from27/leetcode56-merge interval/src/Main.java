import jdk.incubator.vector.IntVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public static int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][2];
        }
        //根据interval的第一项进行排序
        Arrays.sort(intervals, Comparator.comparingInt(interval -> interval[0]));

        List<int[]> merges = new ArrayList<>();
        for (int[] interval : intervals) {
            int l = interval[0], r = interval[1];
            if (merges.size() == 0 || merges.get(merges.size() - 1)[1] < l) { //选择最小的作为左边间
                merges.add(new int[]{l, r});
            } else {
                merges.get(merges.size() - 1)[1] = Math.max(merges.get(merges.size() - 1)[1], r);//将最大的赋值给右边界
            }
        }
        return merges.toArray(new int[merges.size()][]);
    }
}