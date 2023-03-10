public class Main {
    public static void main(String[] args) {
        Trie obj = new Trie();
        obj.insert("word");
        boolean param_2 = obj.search("word");
        boolean param_3 = obj.startsWith("wo");
        System.out.println(param_2);
        System.out.println(param_3);
    }
}