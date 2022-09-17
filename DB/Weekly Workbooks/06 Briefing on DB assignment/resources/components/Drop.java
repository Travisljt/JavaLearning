package components;

import constant.ObjectType;
import constant.StatementType;
import utils.StringUtil;

public class Drop extends Statement implements SqlParser{

    private String dropType;

    public Drop(String sql) {
        this.sql = sql;
        this.statementType = StatementType.DROP;
    }

    public void parser() {
        parseDropObject();
    }

    private void parseDropObject() {
        if (this.sql.toUpperCase().startsWith("DROP DATABASE")) {
            this.dropType = ObjectType.DATABASE;
            String regex = "(drop\\s+database)(.+)(;$)";
            this.database = StringUtil.getMatchedContent(regex, this.sql);
        } else if (this.sql.toUpperCase().startsWith("DROP TABLE")) {
            this.dropType = ObjectType.TABLE;
            String regex = "(drop\\s+table)(.+)(;$)";
            this.table = StringUtil.getMatchedContent(regex, this.sql);
        }
    }

    public String getDropType() {
        return dropType;
    }
}
