public class Trie {
    private Node root;
    public Trie() {
        root = new Node();
    }

    public void insert(String word) {
        if(word != null && !("".equals(word))) {
            char[] words = word.toCharArray();
            Node cur = root;
            for(char w : words){
                if(!cur.map.containsKey(w)){
                    cur.map.put(w,new Node());
                }
                cur = cur.map.get(w);
            }
            cur.setEnd(true);
        }
    }

    public boolean search(String word) {
        if(word != null && !("".equals(word))){
            char[] words = word.toCharArray();
            Node cur = root;
            for(char w : words){
                if(!cur.map.containsKey(w)){
                    return false;
                }
                cur = cur.map.get(w);
            }
            return cur.isEnd();
        }
        return false;
    }

    public boolean startsWith(String prefix) {
        if(prefix != null && !("".equals(prefix))){
            char[] prefixes = prefix.toCharArray();
            Node cur = root;
            for(char p : prefixes){
                if(!cur.map.containsKey(p)){
                    return false;
                }
                cur = cur.map.get(p);
            }
            return true;
        }
        return false;
    }
}
