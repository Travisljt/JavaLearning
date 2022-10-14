import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

//由于stack保证线程安全，所以会有所内存损耗，因此建议用deque代替stack
public class MyQueue {
//    Stack<Integer> s1;
//    Stack<Integer> s2;
//
//    public MyQueue(){
//        this.s1 = new Stack<>();
//        this.s2 = new Stack<>();
//    }
//
//    public void push(int x) {
//        s1.push(x);
//    }
//
//    public int pop() {
//        while(!s1.isEmpty()){
//            s2.push(s1.pop());
//        }
//        int num = s2.pop();
//
//        while (!s2.isEmpty()){
//            s1.push(s2.pop());
//        }
//        return num;
//    }
//
//    public int peek() {
//        while(!s1.isEmpty()){
//            s2.push(s1.pop());
//        }
//        int num = s2.peek();
//
//        while (!s2.isEmpty()){
//            s1.push(s2.pop());
//        }
//        return num;
//    }
//
//    public boolean empty() {
//        return s1.isEmpty();
//    }

    //官方双栈，不需要重复倒
    Deque<Integer> inStack;
    Deque<Integer> outStack;

    public MyQueue() {
        inStack = new ArrayDeque<>();
        outStack = new ArrayDeque<>();
    }

    public void push(int x) {
        inStack.push(x);
    }

    public int pop() {
        if (outStack.isEmpty()) {
            in2out();
        }
        return outStack.pop();
    }

    public int peek() {
        if (outStack.isEmpty()) {
            in2out();
        }
        return outStack.peek();
    }

    public boolean empty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    private void in2out() {
        while (!inStack.isEmpty()) {
            outStack.push(inStack.pop());
        }
    }
}
