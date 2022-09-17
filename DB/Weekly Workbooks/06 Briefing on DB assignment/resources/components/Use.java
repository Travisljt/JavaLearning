package components;

import constant.StatementType;
import utils.StringUtil;

public class Use extends Statement implements SqlParser{
    
    public Use(String sql) {
        this.sql = sql;
        this.statementType = StatementType.USE;
    }
    
    public void parser() {
        parseDatabase();
    }

    private void parseDatabase() {
        String regex = "(use)(.+)(;$)";
        this.database = StringUtil.getMatchedContent(regex, this.sql);
    }
}
