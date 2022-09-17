package components;


import java.util.regex.Matcher;

import constant.StatementType;
import domain.Condition;
import domain.From;
import domain.GroupBy;
import domain.OrderBy;
import domain.Where;
import utils.StringUtil;

public class Select extends Statement implements SqlParser{

    public Select(String sql) {
        this.sql = sql;
        this.statementType = StatementType.SELECT;
        this.from = new From();
        this.where = new Where();
        this.groupBy = new GroupBy();
        this.orderBy = new OrderBy();
    }

    public void parser() {
        parseCols();
        parseTables();
        parseConditions();
        parseGroupCols();
        parseOrderCols();
    }

    private void parseCols() {
        String regex = "(select)(.+)(from)";
        this.cols = StringUtil.getMatchedContent(regex, this.sql);
    }

    private void parseTables() {
        String regex;
        if (StringUtil.containsIgnoreCase(this.sql, " where ")) {
            regex = "(from)(.+)(where)";
        } else {
            regex = "(from)(.+)(;$)";
        }
        this.from.setContent(StringUtil.getMatchedContent(regex, this.sql));
    }

    private void parseConditions() {
        String regex;
        if (StringUtil.containsIgnoreCase(this.sql, " where ")) {
            if (StringUtil.containsIgnoreCase(this.sql, "group by")) {
                regex = "(where)(.+)(group\\s+by)";
            } else if (StringUtil.containsIgnoreCase(this.sql, "order by")) {
                regex = "(where)(.+)(order\\s+by)";
            } else {
                regex = "(where)(.+)(;$)";
            }
        } else {
            return ;
        }

        this.where.setContent(StringUtil.getMatchedContent(regex, this.sql));
        regex = "(.+)(" + Statement.OPRATOR + ")(.+\\s+|.+)";
        Matcher matcher = StringUtil.getMatcher(regex, this.where.getContent());
        if (matcher != null && matcher.groupCount() == 3) {
            this.condition = new Condition(matcher.group(1).trim(), matcher.group(2).trim(), matcher.group(3).trim());
        }
    }

    private void parseGroupCols() {
        String regex;

        if (StringUtil.containsIgnoreCase(this.sql, "group by")) {
            if (StringUtil.containsIgnoreCase(this.sql, "order by")) {
                regex = "(group\\s+by)(.+)(order\\s+by)";
            } else {
                regex = "(group\\s+by)(.+)(;$)";
            }
        } else {
            return ;
        }

        this.groupBy.setContent(StringUtil.getMatchedContent(regex, this.sql));
    }

    private void parseOrderCols() {
        String regex;
        
        if (StringUtil.containsIgnoreCase(this.sql, "order by")) {
            regex = "(order\\s+by)(.+)(;$)";
        } else {
            return ;
        }

        this.orderBy.setContent(StringUtil.getMatchedContent(regex, this.sql));
    }
}
