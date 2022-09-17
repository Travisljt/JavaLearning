package components;

import constant.StatementType;
import utils.StringUtil;

public class Insert extends Statement implements SqlParser{
    
    public Insert(String sql) {
        this.sql = sql;
        this.statementType = StatementType.INSERT;
    }

    public void parser() {
        parseTable();
        parseCols();
    }

    private void parseTable() {
        String regex = "(insert\\s+into)(.+)(values)";
        this.setTable(StringUtil.getMatchedContent(regex, this.sql));
    }

    private void parseCols() {
        String regex = "(values)(.+)(;$)";
        String columns = StringUtil.getMatchedContent(regex, this.sql);
        this.cols = StringUtil.extractField(columns);
    }
}
