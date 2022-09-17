package domain;

public class Condition {
    private String fieldName;
    private String operator;
    private String value;

    public Condition(String fieldName, String oprator, String value) {
        this.fieldName = fieldName;
        this.operator = oprator;
        this.value = value;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isTrue(String value) {
        switch(this.operator) {
            case "==":
                return value.equals(this.value);
            case ">=":
                return Float.parseFloat(value) >= Float.parseFloat(this.value);
            case ">":
                return Float.parseFloat(value) > Float.parseFloat(this.value);
            case "<=":
                return Float.parseFloat(value) <= Float.parseFloat(this.value);
            case "<":
                return Float.parseFloat(value) < Float.parseFloat(this.value);
            case "!=":
                return !value.equals(this.value);
            case "LIKE":
                return value.matches(this.value);
            default:
                break;
        }
        return false;
    }
}
