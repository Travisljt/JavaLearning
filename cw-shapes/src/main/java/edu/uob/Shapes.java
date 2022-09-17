package edu.uob;

import java.util.Stack;

public class Shapes {

    // TODO use this class as then entry point; play around with your shapes, etc
    public static void main(String[] args) {

        String input1 = "{([])}";
        boolean check = isStackValid(input1);
        System.out.println(check); //Valid parentheses


//        String test = "player 1: a";
//        String[] tests = test.split(":");
//        System.out.println(tests[0]);
//        System.out.println(Arrays.toString(tests));
//
//        ArrayList<String> command = new ArrayList<>();
//        command.add("axe");
//        command.remove("axe");
//        for(String c : command){
//            System.out.println(c);
//        }
////        System.out.println(command);
//        String content = "JOIN coursework AND marks ON grade AND id;";
//        //String regex = "([(])(.+)([)]\\s+)(and|or)(\\s+[(])(.+)([)])";
//        String regex = "(join)(.+)(and)(.+)(on)(.+)(and)(.+)(;$)";
//        String result = "Nothing";
//        String result2 = "Nothing";
//        String result3 = "Nothing";
//        String result4 = "Nothing";
//        int number = 2;
//        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(content.toUpperCase());
//        if (matcher.lookingAt()) {
//            result = matcher.group(number).trim().toLowerCase();
//            result2 = matcher.group(4).trim().toLowerCase();
//            result3 = matcher.group(6).trim().toLowerCase();
//            result4 = matcher.group(8).trim().toLowerCase();
//        }
//        String regex2 = "(create\\s+table)(.+)(;$)";
//        Pattern pattern2 = Pattern.compile(regex2, Pattern.CASE_INSENSITIVE);
//        Matcher matcher2 = pattern2.matcher(content.toUpperCase());
//        if (matcher2.find()) {
//            result2 = matcher2.group(number).trim().toLowerCase();
////        }
//        System.out.println("After that: " + result);
//        System.out.println("After that: " + result2);
//        System.out.println("After that: " + result3);
//        System.out.println("After that: " + result4);
//        System.out.println("Replace: "+ result.replace("(", "").replace(")", "").replaceAll("'", "").replaceAll("\\s", ""));
//        TwoDimensionalShape shape = new Triangle(3, 4, 6);
//        System.out.println(shape.calculatePerimeterLength());
//        MultiVariantShape s = new Triangle(3, 4, 5);
//        Circle shape2 = new Circle(6);
//        if (shape2 instanceof MultiVariantShape) {
//            System.out.println("This shape has multiple variants");
//        } else {
//            System.out.println("This shape has only one variant");
//        }
//        System.out.println((s.getVariant()));
//
////        System.out.println(p);
//        Colour firstColour = Colour.BLUE;
//        shape.setColour(firstColour);
//        System.out.println((shape.getColour()));
////        shape = new Circle(5);
////        System.out.println((shape));
////        shape = new Rectangle(8, 9);
////        System.out.println(shape);
//        TwoDimensionalShape[] shapes = new TwoDimensionalShape[100];
//
//        int cnt = 0;
//        for (int i = 0; i < 100; i++) {
//
//            int r = (int) (Math.random() * 4);
//            // int p1 = (int) (Math.random()*10);
//            if (r == 0) {
//                int p = (int) (Math.random() * 10) + 1;
//                shapes[i] = new Circle(p);
//            } else if (r == 1) {
//                int p1 = (int) (Math.random() * 10) + 1;
//                int p2 = (int) (Math.random() * 10) + 1;
//                shapes[i] = new Rectangle(p1, p2);
//            } else {
//                int p1 = (int) (Math.random() * 10) + 1;
//                int p2 = (int) (Math.random() * 10) + 1;
//                int p3 = (int) (Math.random() * 10) + 1;
//                shapes[i] = new Triangle(p1, p2, p3);
//            }
//            if (shapes[i] instanceof MultiVariantShape) {
//                cnt++;
//                System.out.println("The No." + (i+1) + " is the " + cnt + " as same as " +shapes[i].getPopulationSize() +" "+((MultiVariantShape) shapes[i]).getVariant() + " triangle, " + shapes[i]);
//            } else {
//                System.out.println("The No." +(i+1)+" is "+shapes[i]);
//            }
//
//        }
//        System.out.println("There are " + cnt + " 's triangles");
    }

    public static boolean isStackValid(String s) {

        Stack<Character> stk = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == ')' && !stk.isEmpty() && stk.peek() == '(') {
                stk.pop();
            } else if (c == ']' && !stk.isEmpty() && stk.peek() == '[') {
                stk.pop();
            } else if (c == '}' && !stk.isEmpty() && stk.peek() == '{') {
                stk.pop();
            } else {
                stk.push(c);
            }
        }
        return stk.isEmpty();
    }
}
