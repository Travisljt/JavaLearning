package edu.uob.Analyzer;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringRegex {
  public static String removeSpaces(String str) {
    return str.replaceAll("\\s+", " ");
  }

  public static String getMatchTokenSpecial(String regex, String token, int number) {
    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(token);
    if (matcher.find()) {
      return matcher.group(number).trim();
    }
    return null;
  }

  public static Matcher getMatcher(String regex, String token) {
    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(token);
    if (matcher.find()) {
      return matcher;
    }
    return null;
  }

  public static String keyCommand(String command) {
    String regex = "^[a-zA-Z]+";
    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(command);
    if (matcher.find()) {
      return matcher.group().toUpperCase();
    }
    return null;
  }

  public static String removeSymbol(String token) {
    return token.replaceAll("\\s", "").replaceAll("'", "").replace("(", "").replace(")", "");
  }

  public static boolean optionalKeyword(String content, String match){
    Pattern pattern = Pattern.compile(Pattern.quote(match),Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(content);
    return matcher.find();
  }
  public static boolean optionalKeywordFromRegex(String content, String regex){
    Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(content);
    return matcher.find();
  }
  public static int getPriority(String str){
    if(str==null){
      return 0;
    }
    int x = 0;
    switch (str) {
      case "(" -> x = 1;
      case "+" -> x = 2;
      case "*" -> x = 3;
      default -> {
      }
    }
    return x;
  }

  public static String toSufferString(String expr){
    StringBuilder sb = new StringBuilder();
    Stack<String> operator = new Stack<>();
    operator.push(null);
    Pattern pattern = Pattern.compile("(?<!\\d)-?\\d+(\\.\\d+)?|[+\\-*/()]");
    Matcher matcher = pattern.matcher(expr);
    while(matcher.find()){
      String temp = matcher.group();
      if(temp.matches("[+\\-*/()]")){
        if(temp.equals("(")){
          operator.push(temp);
        }else if(temp.equals(")")){
          String top;
          while(!(top = operator.pop()).equals("(")){
            sb.append(top).append(",");
          }
        }else{
          while(getPriority(temp)<=getPriority(operator.peek())){
            sb.append(operator).append(",");
          }
          operator.push(temp);
        }
      }else {
        sb.append(temp).append(",");
      }
    }
    String top;
    while((top =  operator.pop())!=null){
      sb.append(top).append(",");
    }
    return sb.toString();
  }

  public static boolean isNumeric(String str){
    Pattern pattern = Pattern.compile("[0-9]*");
    Matcher isNum = pattern.matcher(str);
    return isNum.matches();
  }
}
