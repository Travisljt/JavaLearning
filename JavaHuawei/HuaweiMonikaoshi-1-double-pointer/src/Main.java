import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        /*
        相近题目：
        已知一个大容器里拥有4个小桶，每个小桶的体积为32。需要往小桶中加入若干数量的砖头，每个砖头的体积在1到32之间。
        小桶里需要尽可能填满砖头。已知砖头有10个，体积分别为2，32，30，24，32，2，32，8，9，14。请问至少需要多少个大容器。

        使用双指针方法实现此算法需要以下步骤：

        先将砖头从小到大排序。
        初始化两个指针，左指针指向第一个砖头，右指针指向第二个砖头。
        将左指针和右指针所指的砖头体积相加，若小于32，则将右指针右移，相加结果依然小于32则继续右移；若大于32，则说明已经没有可以与左指针所指的砖头搭配的砖头了，将左指针右移，同时将砖头个数加一。
        当左指针大于等于右指针时，说明已经遍历完所有砖头，所需容器数就是已经添加的砖头个数除以4向上取整（因为每个容器有4个小桶）。
         */
        int[] bricks = new int[]{2, 32, 28, 24, 32, 2, 32, 8, 18, 14, 6, 7, 8, 9, 32, 32, 32, 23};
//        int container = minContainers(bricks);
        int container = tanXin(bricks);
//        int container = dp(bricks);
        System.out.println("需要最少的大容器数：" + container);
    }

    private static int minContainers(int[] bricks) {
        Arrays.sort(bricks);
        int l = 0, r = bricks.length - 1, sum = 0, container = 0;
        while (l <= r) {
            //sum作为一个过度值，类似于carry
            //当值较小时，从左边加
            //当值大于32时，较大的值可以抛出，继续计算sum
            if (sum == 0) {
                if (bricks[l] + bricks[r] <= 32) {
                    sum = bricks[l] + bricks[r];
                    l++;
                } else {
                    container++;
                    r--;
                }
            } else {
                if (sum + bricks[l] <= 32) {
                    sum += bricks[l];
                    l++;
                } else {
                    container++;
                    r--;
                    sum = 0;
                }
            }
        }

        if (sum > 0) {
            container++;
        }
        return (int) Math.ceil(container / 4.0);
    }

    public static int tanXin(int[] bricks) {
        //需要给足bucketCount的空间
        //未经验证，但效果较好——> 贪心算法
        //倒叙找到合适的空间
        int bucketSize = 32;
        int bucketCount = 5;
        int minContainer = 0;

        Arrays.sort(bricks);

        int[] buckets = new int[bucketCount];

        for (int i = bricks.length - 1; i >= 0; i--) {
            for (int j = 0; j < bucketCount; j++) {
                //如果小于剩余空间，则填入
                if (bricks[i] <= (bucketSize - buckets[j])) {
                    buckets[j] += bricks[i];
                    //如果空间刚好满，则可以更新原空间，然后minContainer++
                    if (buckets[j] == 32) {
                        buckets[j] = 0;
                        minContainer++;
                    }
                    break;
                }
            }
        }
        int index = 0;
        for (int i = 0; i < bucketCount; i++) {
            if (buckets[i] != 0) {
                index += buckets[i];
            }
        }
        //将剩下杂乱的空间相加，看是否能够刚好完成一个或2个空间
        minContainer += Math.ceil(index / 32.0);

        return (int) Math.ceil(minContainer / 4.0);
    }

    public static int dp(int[] bricks) {
        int[] container = new int[4];
        int maxVolume = 32 * 4;
        int containers = 1;
        for (int brick : bricks) {
            for (int i = 0; i < 4; i++) {
                if (container[i] + brick <= 32) {
                    container[i] += brick;
                    break;
                }
                if (i == 3) {
                    containers++;
                    container[0] = brick;
                }
            }
        }
        return containers;
    }
}