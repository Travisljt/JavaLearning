import java.util.*;
import java.util.function.LongFunction;

public class TimeMap {
//    Map<String, TreeMap<Integer, String>> hashMap;
//
//    public TimeMap() {
//        this.hashMap = new HashMap<>();
//    }
//
//    public void set(String key, String value, int timestamp) {
//        if (!hashMap.containsKey(key)) {
//            TreeMap<Integer, String> treeMap = new TreeMap<>();
//            treeMap.put(timestamp, value);
//            hashMap.put(key, treeMap);
//        } else {
//            TreeMap<Integer, String> treeMap = hashMap.get(key);
//            treeMap.put(timestamp, value);
//        }
//    }
//
//    public String get(String key, int timestamp) {
//        if (!hashMap.containsKey(key)) {
//            return "";
//        } else {
//            Set<Integer> keyset = hashMap.get(key).keySet();
//            int ans = close(timestamp, keyset);
//            if (ans > timestamp) {
//                return "";
//            } else {
//                return hashMap.get(key).get(ans);
//            }
//        }
//    }
//
//    public int close(int timestamp, Set<Integer> keyset) {
//        Integer[] a = keyset.toArray(new Integer[]{});
//        int[] arr = new int[keyset.size()];
//        for (int i = 0; i < keyset.size(); i++) {
//            arr[i] = a[i];
//        }
//        Arrays.sort(arr);
//        int left = 0;
//        int right = arr.length - 1;
//        while (left<right){
//            int mid = left + (right - left)/2;
//            if(compareTo(arr[mid],timestamp)<=0){
//                left = mid + 1;
//            }else {
//                right = mid;
//            }
//        }
//        return arr[left];
//    }
//
//    private int compareTo(int i, int timestamp) {
//        if(i != timestamp){
//            return i - timestamp;
//        }else {
//            return timestamp;
//        }
//    }

    //floor 和 ceiling 方法，TreeMap 和 TreeSet 都有各自的实现
    //
    //可以用来求 大于等于 和 小于等于，比一般的二分好用
    //
    //如果是平时练习二分的边界处理也就算了，写业务和比赛的时候建议直接用这两个 api
    //floorEntry(key) -> 返回指定的Key大于或等于的最小值的元素，如果没有，则返回null
    //ceilingEntry(key) -> 与ceilingEntry()方法相反，是返回小于等于key的最大Key的元素
    Map<String, TreeMap<Integer, String>> data;
    public TimeMap() {
        data = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        TreeMap<Integer, String> v;
        if (!data.containsKey(key)) {
            v = new TreeMap<>();
        } else {
            v = data.get(key);
        }
        v.put(timestamp, value);
        data.put(key, v);

    }

    public String get(String key, int timestamp) {
        if (!data.containsKey(key)) {
            return "";
        }
        Map.Entry<Integer, String> res = data.get(key).floorEntry(timestamp);
        return res == null ? "" : res.getValue();
    }


}
