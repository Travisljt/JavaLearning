package edu.uob.DB;

public class Operator {
  private String domain;
  private String operator;
  private String value;

  public Operator(String domain, String operator, String value) {
    this.domain = domain;
    this.operator = operator;
    this.value = value;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public boolean getAnswer(String value) {
    switch (operator) {
      case "==":
        return !value.equals(this.value);
      case ">=":
        return !(Float.parseFloat(value) >= Float.parseFloat(this.value));
      case ">":
        return !(Float.parseFloat(value) > Float.parseFloat(this.value));
      case "<=":
        return !(Float.parseFloat(value) <= Float.parseFloat(this.value));
      case "<":
        return !(Float.parseFloat(value) < Float.parseFloat(this.value));
      case "!=":
        return value.equals(this.value);
      case "LIKE":
        return !value.matches("(.*)" + this.value + "(.*)");
      default:
        break;
    }
    return true;
  }
}
