import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public static int numIslands_BFS(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int numOfIslands = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    numOfIslands++;
                    grid[i][j] = '0';
                    Queue<Integer> queue = new LinkedList<>();
                    queue.offer((i * grid[0].length + j));
                    while (!queue.isEmpty()) {
                        int id = queue.poll();
                        int row = id / grid[0].length;
                        int col = id % grid[0].length;
                        if (row - 1 >= 0 && grid[row - 1][col] == '1') {
                            queue.offer((row - 1) * grid[0].length + col);
                            grid[row - 1][col] = '0';
                        }
                        if (row + 1 < grid.length && grid[row + 1][col] == '1') {
                            queue.offer((row + 1) * grid[0].length + col);
                            grid[row + 1][col] = '0';
                        }
                        if (col - 1 >= 0 && grid[row][col - 1] == '1') {
                            queue.offer(row * grid[0].length + (col - 1));
                            grid[row][col - 1] = '0';
                        }
                        if (col + 1 < grid[0].length && grid[row][col + 1] == '1') {
                            queue.offer(row * grid[0].length + (col + 1));
                            grid[row][col + 1] = '0';
                        }
                    }
                }
            }
        }
        return numOfIslands;
    }

    public static int numIslands_DFS(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int numOfIslands = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    numOfIslands++;
                    dfs(grid, i, j);
                }
            }
        }
        return numOfIslands;
    }

    private static void dfs(char[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == '0') {
            return;
        }
        grid[i][j] = '0';
        dfs(grid, i - 1, j);
        dfs(grid, i + 1, j);
        dfs(grid, i, j - 1);
        dfs(grid, i, j + 1);
    }
}