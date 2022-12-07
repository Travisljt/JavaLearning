import java.util.*;

public class Main {
    public static void main(String[] args) {
        //迷宫问题 （BFS）
        /*
        定义一个二维数组 N*M ，如 5 × 5 数组下所示：


        int maze[5][5] = {
        0, 1, 0, 0, 0,
        0, 1, 1, 1, 0,
        0, 0, 0, 0, 0,
        0, 1, 1, 1, 0,
        0, 0, 0, 1, 0,
        };


        它表示一个迷宫，其中的1表示墙壁，0表示可以走的路，
        只能横着走或竖着走，不能斜着走，要求编程序找出从左上角到右下角的路线。
        入口点为[0,0],既第一格是可以走的路。

        输入：
        5 5
        0 1 0 0 0
        0 1 1 1 0
        0 0 0 0 0
        0 1 1 1 0
        0 0 0 1 0

        输出：
        (0,0)
        (1,0)
        (2,0)
        (2,1)
        (2,2)
        (2,3)
        (2,4)
        (3,4)
        (4,4)

        广度优先基本思路：
        public void bfs(int m, int n) {  // m n 分别为 目标点的 横坐标与纵坐标
        起点入队 //也可以在先决条件里设置起点
        while（队列不为空）{
        出队
        if 若到目标点—>跳出 （也可以输出需要的步数）
        else 判断上下左右能否访问，若能则入队
        }
        输出路径（可以通过链表反向输出路径）
        }
        }

        深度优先的基本思路： (也可以用boolean类型)
        public void dfs(int m, int n, int rounds) { //m n 分别为当前点的横坐标与纵坐标 rounds为回合数
        if（m==终点 && n==终点）{
        输出路径和步数
        }
        if（m n节点的值为可以访问的值：例如数字0）{
        将值从0变为1 // 访问过不再访问
        判断上下左右是否能访问，能访问就调用四个dfs（）递归
        回溯 （将数字1变为数字0） //将不能找到答案的值全部变回原来的样子
        }
        }

         */
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int row = sc.nextInt();
            int col = sc.nextInt();
            int[][] grid = new int[row][col];
            for (int i = 0; i < row; ++i) {
                for (int j = 0; j < col; ++j) {
                    grid[i][j] = sc.nextInt();
                }
            }
            Queue<Point> que = new LinkedList<>();
            //定位起点
            que.offer(new Point(0, 0, null));
            grid[0][0] = 1; //令第一个点为障碍也无任何影响 可能会影响向左与上搜索的速度而已
            Point pos;
            while (true) {
                pos = que.poll();
                int px = pos.px;
                int py = pos.py;
                if (px == row - 1 && py == col - 1) break;//抵达终点
                else {
                    //向下搜索
                    if (px + 1 < row && grid[px + 1][py] == 0) {
                        grid[px + 1][py] = 1;
                        que.offer(new Point(px + 1, py, pos));
                    }
                    //向左搜索
                    if (py - 1 >= 0 && grid[px][py - 1] == 0) {
                        grid[px][py - 1] = 1;
                        que.offer(new Point(px, py - 1, pos));
                    }
                    //向上搜索
                    if (px - 1 >= 0 && grid[px - 1][py] == 0) {
                        grid[px - 1][py] = 1;
                        que.offer(new Point(px - 1, py, pos));
                    }
                    //向右搜索
                    if (py + 1 < col && grid[px][py + 1] == 0) {
                        grid[px][py + 1] = 1;
                        que.offer(new Point(px, py + 1, pos));
                    }
                }
            }
            //用stack反推
            Stack<Point> stack = new Stack<>();
            while (pos != null) {
                stack.push(pos);
                pos = pos.father;
            }
            while (!stack.isEmpty()) {
                Point temp = stack.peek();
                stack.pop();
                System.out.println("(" + temp.px + "," + temp.py + ")");
            }

            //也可以用递归的方式反向打印指针
            //print(pos);
        }
    }

    public static void print(Point p) {
        if (p != null) {
            print(p.father);
            System.out.println("(" + p.px + "," + p.py + ")");
        }
    }
}

class Point {
    int px;
    int py;
    Point father;

    Point(int px, int py, Point father) {
        this.px = px;
        this.py = py;
        this.father = father;
    }

    Point() {
    }
}