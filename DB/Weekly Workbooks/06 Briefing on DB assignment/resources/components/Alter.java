package components;

import java.util.regex.Matcher;

import constant.AlterType;
import constant.StatementType;
import utils.StringUtil;

public class Alter extends Statement implements SqlParser{
    
    private String alterType;
    
    public Alter(String sql) {
        this.sql = sql;
        this.statementType = StatementType.ALTER;
    }

    public void parser() {
        parseAlterCols();
    }

    private void parseAlterCols() {
        String regex = "(alter\\s+table)(.+)(add|drop)(.+)(;$)";
        Matcher matcher = StringUtil.getMatcher(regex, this.sql);
        if (matcher != null) {
            this.setTable(matcher.group(2).trim());
            String type = matcher.group(3).toUpperCase();
            this.cols = matcher.group(4).trim();
            if ("ADD".equals(type)) {
                this.alterType = AlterType.ADD;
            } else if ("DROP".equals(type)) {
                this.alterType = AlterType.DROP;
            }
        }
    }

    public String getAlterType() {
        return this.alterType;
    }
}
