import java.util.Arrays;

public class MinStack {
    private int pointer;
    private int[] stack;
    public MinStack() {
        pointer = -1;
        stack = new int[10000];
    }

    public void push(int val) {
        pointer++;
        stack[pointer] = val;
    }

    public void pop() {
        stack[pointer]=0;
        pointer--;
    }

    public int top() {
        return stack[pointer];
    }

    public int getMin() {
        int[] set = Arrays.copyOf(stack,pointer+1);
        Arrays.sort(set);
        return set[0];
    }
}
