package components;

import domain.Condition;
import domain.From;
import domain.GroupBy;
import domain.OrderBy;
import domain.Where;

public abstract class Statement {

    protected static final String OPRATOR = "==|>=|<=|>|<|!=|LIKE";

    protected String statementType;

    protected String sql;

    protected String database;

    protected String table;

    protected String cols;

    protected From from;

    protected Where where;

    protected GroupBy groupBy;

    protected OrderBy orderBy;

    protected Condition condition;

    public String getStatementType() {
        return statementType;
    }

    public void setStatementType(String statementType) {
        this.statementType = statementType;
    }

    public String getCols() {
        return cols;
    }

    public void setCols(String cols) {
        this.cols = cols;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public Where getWhere() {
        return where;
    }

    public void setWhere(Where where) {
        this.where = where;
    }

    public GroupBy getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(GroupBy groupBy) {
        this.groupBy = groupBy;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
    
}
