public class Main {
    public static void main(String[] args) {
        int k = lengthOfLongestSubstring("abcabcbb");
        System.out.println(k);
    }
    public static int lengthOfLongestSubstring(String s) {
        //暴力解法
        int max=0;
        if(s.length()==1){
            return 1;
        }
        for(int i=0;i<s.length();i++){
            boolean[] table = new boolean[128];
            table[s.charAt(i)] = true;
            for(int j=i+1;j<s.length();j++){
                if(table[s.charAt(j)]){
                    max = Math.max(max,j-i);
                    break;
                }else {
                    table[s.charAt(j)] = true;
                }
                if(j==s.length()-1){
                    max = Math.max(max,j+1-i);
                }
            }
        }
        return max;

        //hashtable思路  其实思路一样的
        //相比以上的解法，速度要快一些
        /*
        if (s.length()==0) return 0;
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        int max = 0;
        int left = 0;
        for(int i = 0; i < s.length(); i ++){
            if(map.containsKey(s.charAt(i))){
                left = Math.max(left,map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i),i);
            max = Math.max(max,i-left+1);
        }
        return max;
         */
    }
}