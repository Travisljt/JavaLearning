import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public static int orangesRotting(int[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int freshOrange = 0;
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    freshOrange++;
                } else if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                }
            }
        }
        int round = 0;
        while (freshOrange > 0 && !queue.isEmpty()) {
            round++;
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                int[] id = queue.poll();
                int row = id[0];
                int col = id[1];
                if (row - 1 >= 0 && grid[row - 1][col] == 1) {
                    grid[row - 1][col] = 2;
                    freshOrange--;
                    queue.offer(new int[]{row - 1, col});
                }
                if (row + 1 < grid.length && grid[row + 1][col] == 1) {
                    grid[row + 1][col] = 2;
                    freshOrange--;
                    queue.offer(new int[]{row + 1, col});
                }
                if (col - 1 >= 0 && grid[row][col - 1] == 1) {
                    grid[row][col - 1] = 2;
                    freshOrange--;
                    queue.offer(new int[]{row, col - 1});
                }
                if (col + 1 < grid[0].length && grid[row][col + 1] == 1) {
                    grid[row][col + 1] = 2;
                    freshOrange--;
                    queue.offer(new int[]{row, col + 1});
                }
            }
        }
        if (freshOrange > 0) {
            return -1;
        } else {
            return round;
        }
    }

    /*
    求最短路径的方式：
    基本方式：
    while queue 非空:
	node = queue.pop()
    for node 的所有相邻结点 m:
        if m 未访问过:
            设置为访问
            queue.push(m)

    但是存在一种重复运算，因此，一次对所有点进行运算

    depth = 0 # 记录遍历到第几层
    while queue 非空:
    depth++
    n = queue 中的元素个数
    循环 n 次:
        node = queue.pop()
        for node 的所有相邻结点 m:
            if m 未访问过:
                设置为访问
                queue.push(m)
     */

}