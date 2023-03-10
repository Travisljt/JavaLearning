import java.util.HashMap;

public class Node {
    private boolean end;
    public HashMap<Character,Node> map;

    public Node(){
        map = new HashMap<>();
        end = false;
    }

    public boolean isEnd(){
        return end;
    }

    public void setEnd(boolean end){
        this.end = end;
    }

}
