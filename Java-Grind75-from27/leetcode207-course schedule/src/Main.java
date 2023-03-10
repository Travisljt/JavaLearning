import java.util.List;
import java.util.*;

public class Main {
    private static boolean[] marked;
    private static boolean[] onStack;
    private static boolean hasCycle = false;
    public static void main(String[] args) {
        int numCourses = 2;
        int[][] prerequisites = {{1,0}};
        System.out.println(canFinish_DFS(numCourses,prerequisites));
    }
 //拓扑图，拓扑算法
    private static boolean canFinish_BFS(int numCourses, int[][] prerequisites) {
        int[] indegrees = new int[numCourses];
        List<List<Integer>> adjacency = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++)
            adjacency.add(new ArrayList<>());
        for (int[] cp : prerequisites) {
            indegrees[cp[0]]++;//所需要的未探索过的节点入度
            adjacency.get(cp[1]).add(cp[0]);//在对应位置标记u跟v之间的连接
        }
        for (int i = 0; i < numCourses; i++)
            if (indegrees[i] == 0) queue.offer(i);//将所有初始节点加入队列中
        while (!queue.isEmpty()) {
            int pre = queue.poll();//先前的节点先出队
            numCourses--;
            for (int cur : adjacency.get(pre))
                //如果在这个节点上还存在链接，就将他们接着加入队列
                //如果cur在减掉他的依赖后，入度为0，则可以看做新的初始节点，重新加入队列
                if (--indegrees[cur] == 0) queue.offer(cur);
        }
        return numCourses == 0;//最后判断是否所有的课都已经被选中，不然就存在环
    }

    private static boolean canFinish_DFS(int numCourses, int[][] prerequisites){
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++)
            graph.add(new ArrayList<>());
        for (int[] prerequisite : prerequisites)
            graph.get(prerequisite[1]).add(prerequisite[0]);
        marked = new boolean[numCourses];
        onStack = new boolean[numCourses];
        for (int i = 0; i < numCourses; i++)
            if (!marked[i]) dfs(graph, i);
        return !hasCycle;
    }

    private static void dfs(List<List<Integer>> graph, int v){
        marked[v] = true;
        onStack[v] = true;
        for (int w : graph.get(v)) {
            if (hasCycle) return;
            else if (!marked[w]) dfs(graph, w);
            else if (onStack[w]) {
                hasCycle = true;
                return;
            }
        }
        onStack[v] = false;
    }

}