import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        mp3模式：
        歌曲总数<=4的时候，不需要翻页，只是挪动光标位置。
        光标在第一首歌曲上时，按Up键光标挪到最后一首歌曲；光标在最后一首歌曲时，按Down键光标挪到第一首歌曲

        歌曲总数大于4的时候（以一共有10首歌为例）：
        特殊翻页：
        屏幕显示的是第一页（即显示第1 – 4首）时，光标在第一首歌曲上，
        用户按Up键后，屏幕要显示最后一页（即显示第7-10首歌），
        同时光标放到最后一首歌上。同样的，屏幕显示最后一页时，光标在最后一首歌曲上，
        用户按Down键，屏幕要显示第一页，光标挪到第一首歌上。
        一般翻页：
        屏幕显示的不是第一页时，光标在当前屏幕显示的第一首歌曲时，
        用户按Up键后，屏幕从当前歌曲的上一首开始显示，光标也挪到上一首歌曲。
        光标当前屏幕的最后一首歌时的Down键处理也类似。

        输入说明：
        1 输入歌曲数量
        2 输入命令 U或者D

        输出说明
        1 输出当前列表
        2 输出当前选中歌曲


        使用滑动窗口的方式解答
        滑动窗口，在本体中长度固定为4，可以看作是一个有限制长度的屏幕，
        只是这里需要把无法显示的所有内容都放到长度无限的页面上，通过对向上和向下操作进行优化。
        通过模拟可知，1、2、3、4这四个数中循环，
        假设当前位置为i，对于 U 操作指令，光标向上移动一格后的位置为 (i-1-1+n) % n+1，
        这个步骤可以自己在纸上模拟得出规律；光标向后移动一格后的位置为 i%n+1。
        对于特殊情况，如果移动后的光标不在窗口内，
        则需要滑动窗口。如果光标在窗口起始位置前，则把窗口的起始位置和光标对齐；
        如果光标在窗口后，则把窗口的末位置和光标对齐。

        算法流程：
        两个指针表示窗口页面的起始位置和末位置，一个指针作为光标的位置， 三个位置都是从1开始
        遍历输入的命令，根据向上移动和向下移动的公式，光标位置的变化
        移动光标后，判断滑动窗口的位置是否需要改变
         */
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int n = sc.nextInt();
            String cmd = sc.next();
            parseCmd(cmd, n);
        }
    }

    private static void parseCmd(String commands, int n) {
        // 页面的起始位置
        int start = 1;
        // 页面的末位置, 如果n小于4那就无所谓
        int end = Math.min(n, 4);
        //光标位置
        int index = 1;
        for (int i = 0; i < commands.length(); i++) {
            //根据向上与向下移动的公式，光标的位置变化
            if (commands.charAt(i) == 'U') {
                //注意是先计算 (index - 1 - 1 + n) % n，再＋1
                index = (index - 1 - 1 + n) % n + 1;
            } else if (commands.charAt(i) == 'D') {
                index = index % n + 1;
            }
            //判断滑动窗口的位置是否需要改变
            if(index<start){
                //光标在窗口之上
                start = index;
                end = start + 3;
            }else if(index > end){
                //光标在窗口之下
                end = index;
                start = end - 3;
            }
        }
        for(int i =start;i<=end;i++){
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println(index);
    }
}