import java.util.*;

public class Main {
    public static void main(String[] args) {
        //四则运算  之前学过  使用逆波兰会舒服很多
        //先转换为逆波兰表达式
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        List<String> infix = expressionToList(s);  // List
        List<String> suffix = infixToSuffix(infix); // 中缀转后缀
        Stack<String> stk = new Stack<>();    // 存储中间结果
        // 逆波兰计算器
        for (String tmp : suffix) {
            if (isOper(tmp)) {
                String num2 = stk.pop();
                String num1 = stk.pop();
                String reuslt = cal(num1, tmp, num2);
                stk.push(reuslt);
            } else { // 数字直接入栈
                stk.push(tmp);
            }
        }
        System.out.println(stk.pop());

    }

    private static String cal(String num1, String oper, String num2) {
        Long result = 0L;
        Long a = Long.parseLong(num1);
        Long b = Long.parseLong(num2);
        switch (oper) {
            case "+" -> result = a + b;
            case "-" -> result = a - b;
            case "*" -> result = a * b;
            case "/" -> result = a / b;
        }
        return String.valueOf(result);
    }

    private static boolean isOper(String str) {
        return "+".equals(str) || "-".equals(str) || "*".equals(str) || "/".equals(str) ||
                "(".equals(str) || ")".equals(str) || "[".equals(str) || "]".equals(str) ||
                "{".equals(str) || "}".equals(str);
    }

    private static List<String> infixToSuffix(List<String> infix) {
        List<String> suffix = new ArrayList<>();
        Stack<String> stack1 = new Stack<>();   // 只用于保存操作符
        Stack<String> stack2 = new Stack<>();   // 用于保存中间结果
        for (String tmp : infix) {
            if (isOper(tmp)) {   // 操作符要根据情况来入栈 1
                if (stack1.isEmpty() || "(".equals(tmp) || "[".equals(tmp) || "{".equals(tmp)) {
                    // 如果 stack1 是空的，或者 tmp 是左括号，直接入栈
                    stack1.push(tmp);
                } else { // stack1 不是空的，且 tmp 不是左括号
                    if (")".equals(tmp) || "]".equals(tmp) || "}".equals(tmp)) {
                        // tmp 是右括号，则把 stack1 遇到左括号之前，全部倒入 stack2
                        while (!("(".equals(stack1.peek()) || "[".equals(stack1.peek()) || "{".equals(stack1.peek()))) {
                            stack2.push(stack1.pop());
                        }
                        stack1.pop();   // 丢掉左括号
                    } else {
                        // tmp 是 +-*/ 其中之一
                        while (!stack1.isEmpty() && priority(stack1.peek()) >= priority(tmp)) {
                            // 在 tmp 能够碾压 stack1 的栈顶操作符之前，把 stack1 的栈顶操作符倒入 stack 2 中
                            stack2.push(stack1.pop());
                        }
                        // 离开 while 时，要么 stack1 已经倒空了，要么就是现在 tmp 可以压住 stack.peek() 了
                        stack1.push(tmp);
                    }
                }
            } else { // 操作数直接入栈 2
                stack2.push(tmp);
            }
        }

        // stack1 剩余操作符全部倒入 stack2
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }

        // stack2 的逆序才是结果，所以要再倒一次
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }

        // 现在 stack1 的元素倒出来的顺序就是后缀表达式
        while (!stack1.isEmpty()) {
            suffix.add(stack1.pop());
        }

        return suffix;
    }

    private static int priority(String oper) {
        if ("+".equals(oper) || "-".equals(oper)) {
            return 0;
        } else if ("*".equals(oper) || "/".equals(oper)) {
            return 1;
        } else {
            return -1;
        }
    }

    private static List<String> expressionToList(String expression) {
        List<String> list = new ArrayList<>();
        int len = expression.length();
        StringBuilder keepNum = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c)) {
                if (i != len - 1 && Character.isDigit(expression.charAt(i + 1))) {
                    // 如果下一个字符也是数字
                    keepNum.append(c);
                } else {
                    // 当前是最后一个字符，或者下一个开始不是数字
                    keepNum.append(c);
                    list.add(keepNum.toString());
                    keepNum = new StringBuilder();
                }
            }else if(c == '-'){
                if(i==0 || expression.charAt(i-1)=='(' || expression.charAt(i-1)=='[' || expression.charAt(i-1)=='{'){
                    keepNum.append(c);
                }else{
                    list.add(c + "");
                }
            }else{
                list.add(c + "");
            }
        }
        return list;
    }
}