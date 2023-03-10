import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
    }
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        // 作用：存储每个邮箱属于哪个账户 ，同时 在遍历邮箱时，判断邮箱是否出现过[去重]
        // 格式：<邮箱，账户id>
        Map<String, Integer> emailToId = new HashMap<>();
        int n = accounts.size();//id个数
        UnionFind myUnion = new UnionFind(n);
        for(int i = 0; i < n; i++){
            int num = accounts.get(i).size();
            for(int j = 1; j < num; j++){
                String curEmail = accounts.get(i).get(j);
                //当前邮箱没有出现过
                if(!emailToId.containsKey(curEmail)){
                    emailToId.put(curEmail, i);
                }else{//当前邮箱已经出现过，那么代表这两个用户是同一个
                    myUnion.union(i, emailToId.get(curEmail));
                }
            }
        }

        //进行完上面的步骤，同一个用户的所有邮箱已经属于同一个连通域了，但是就算在同一个连通域，不同的邮箱还是可能会对应不同的id
        // 作用： 存储每个账户下的邮箱
        // 格式： <账户id, 邮箱列表> >
        // 注意：这里的key必须是账户id，不能是账户名称，名称可能相同，会造成覆盖
        Map<Integer, List<String>> idToEmails = new HashMap<>();
        //将同一个连通域内的邮箱对应到同一个id【也就是第一次出现的id，比如4、5在同一个连通域，那么这个连通域对应的id就是4】
        for(Map.Entry<String, Integer> entry : emailToId.entrySet()){
            int id = myUnion.find(entry.getValue());
            List<String> emails = idToEmails.getOrDefault(id, new ArrayList<>());
            emails.add(entry.getKey());
            idToEmails.put(id,emails);
        }
        //经过上面的步骤，已经做到了id和邮箱集合对应起来，接下来把用户名对应起来就可以了
        List<List<String>> res = new ArrayList<>();
        for(Map.Entry<Integer, List<String>> entry : idToEmails.entrySet()){
            List<String> emails = entry.getValue();
            Collections.sort(emails);
            List<String> tmp = new ArrayList<>();
            tmp.add(accounts.get(entry.getKey()).get(0));//先添加用户名
            tmp.addAll(emails);
            res.add(tmp);
        }
        return res;
    }
}

class UnionFind {
    int[] parent;

    public UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;//初始为根结点
        }
    }

    public void union(int index1, int index2) {//合并两个根结点，对index1，index2没有任何要求
        parent[find(index2)] = find(index1);
    }

    public int find(int index) {//查找父节点
        if (parent[index] != index) {//如果不是根结点，接着找
            parent[index] = find(parent[index]);
        }
        return parent[index]; //直到找到根结点后返回
    }
}
