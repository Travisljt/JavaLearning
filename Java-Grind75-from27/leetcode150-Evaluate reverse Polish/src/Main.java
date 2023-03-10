import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
    public static void main(String[] args) {
        String[] tokens = {"2","1","+","3","*"};
        System.out.println(evalRPN(tokens));
    }

    public static int evalRPN(String[] tokens){
        Deque<Integer> stack = new ArrayDeque<>();
        int s1,s2;
        for(String s : tokens){
            switch (s) {
                case "+" -> {
                    s1 = stack.pop();
                    s2 = stack.pop();
                    stack.push(s1 + s2);
                }
                case "-" -> {
                    s1 = stack.pop();
                    s2 = stack.pop();
                    stack.push(s2 - s1);
                }
                case "*" -> {
                    s1 = stack.pop();
                    s2 = stack.pop();
                    stack.push(s1 * s2);
                }
                case "/" -> {
                    s1 = stack.pop();
                    s2 = stack.pop();
                    stack.push(s2 / s1);
                }
                default -> stack.push(Integer.parseInt(s));
            }
        }
        return stack.pop();
    }
}