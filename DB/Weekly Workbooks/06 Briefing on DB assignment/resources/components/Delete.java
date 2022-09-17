package components;

import java.util.regex.Matcher;

import constant.StatementType;
import domain.Condition;
import domain.From;
import domain.Where;
import utils.StringUtil;

public class Delete extends Statement implements SqlParser{

    public Delete(String sql) {
        this.sql = sql;
        this.statementType = StatementType.DELETE;
        this.where = new Where();
        this.from = new From();
    }

    @Override
    public void parser() {
        parseTable();
        parseConditions();
    }
    
    private void parseTable() {
        String regex = "(delete\\s+from)(.+)(where)";
        this.from.setContent(StringUtil.getMatchedContent(regex, this.sql));
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
