package components;

import java.util.regex.Matcher;

import constant.ObjectType;
import constant.StatementType;
import utils.StringUtil;

public class Create extends Statement implements SqlParser{

    private String createType;

    public Create(String sql) {
        this.sql = sql;
        this.statementType = StatementType.CREATE;
    }

    public void parser() {
        parseCreateObject();
    }

    private void parseCreateObject() {
        if (this.sql.toUpperCase().startsWith("CREATE DATABASE")) {
            this.createType = ObjectType.DATABASE;
            String regex = "(create\\s+database)(.+)(;$)";
            this.database = StringUtil.getMatchedContent(regex, this.sql);
        } else if (this.sql.toUpperCase().startsWith("CREATE TABLE")) {
            this.createType = ObjectType.TABLE;
            String regex = "(create\\s+table)(.+)(\\(.+\\))";
            Matcher matcher = StringUtil.getMatcher(regex, this.sql);
            if (matcher != null) {
                this.cols = StringUtil.extractField(matcher.group(3));
                this.setTable(matcher.group(2).trim());
            }
        }
    }

    public String getCreateType() {
        return createType;
    }

}
