public class Main {
    public static void main(String[] args) {
        MinStack obj = new MinStack();
        obj.push(1);
        obj.push(2);
        obj.pop();
        System.out.println(obj.top());//1
        obj.push(3);
        System.out.println(obj.getMin());//1
    }
}