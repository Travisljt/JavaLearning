package components;

import java.util.regex.Matcher;

import constant.StatementType;
import domain.Condition;
import domain.Where;
import utils.StringUtil;

public class Update extends Statement implements SqlParser{

    public Update(String sql) {
        this.sql = sql;
        this.statementType = StatementType.UPDATE;
        this.where = new Where();
    }

    @Override
    public void parser() {
        parseTable();
        parseOperation();
        parseConditions();
    }
    
    private void parseTable() {
        String regex = "(update)(.+)(set)";
        this.table = StringUtil.getMatchedContent(regex, this.sql);
    }

    private void parseOperation() {
        String regex;
        if (StringUtil.containsIgnoreCase(this.sql, "where")) {
            regex = "(set)(.+)(where)";
        } else {
            regex = "(set)(.+)(;$)";
        }
        this.cols = StringUtil.getMatchedContent(regex, this.sql);
    }

    private void parseConditions() {
        String regex = "(where)(.+)(;$)";
        this.where.setContent(StringUtil.getMatchedContent(regex, this.sql));
        regex = "(.+)(" + Statement.OPRATOR + ")(.+\\s+|.+)";
        Matcher matcher = StringUtil.getMatcher(regex, this.where.getContent());
        if (matcher != null && matcher.groupCount() == 3) {
            this.condition = new Condition(matcher.group(1).trim(), matcher.group(2).trim(), matcher.group(3).trim());
        }
    }
}
