
import java.awt.*;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public static Node cloneGraph_BFS(Node node){
        //此题因为避免寻找的无限循环 所以需要使用hashmap进行记录保存
        if(node == null){
            return null;
        }
        Map<Node,Node> hashMap = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        Node newNode = new Node(node.val);
        //初始化
        hashMap.put(node,newNode);
        queue.offer(node);
        while (!queue.isEmpty()){
            Node temp = queue.poll();
            for(Node n : temp.neighbors){
                if(!hashMap.containsKey(n)){
                    //如果没有，创建一个新的地址，将它放入hashmap，并加入队列
                    hashMap.put(n,new Node(n.val));
                    queue.offer(n);
                }
                //无论结果与否，都需要将temp的neighbors加上
                hashMap.get(temp).neighbors.add(hashMap.get(n));
            }
        }
        return newNode;
    }

    public static Node cloneGraph_dfs(Node node){
        Map<Node,Node> hashMap = new HashMap<>();
        return dfs(node,hashMap);
    }

    private static Node dfs(Node node, Map<Node,Node> hashMap) {
        if(node==null) return null;
        if(hashMap.containsKey(node)) {
            return hashMap.get(node);
        }
        Node clone = new Node(node.val);
        hashMap.put(node, clone);
        for(Node n : node.neighbors){
            clone.neighbors.add(dfs(n,hashMap));
        }
        return clone;
    }
}

class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}