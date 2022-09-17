package components;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import constant.AlterType;
import constant.ObjectType;
import constant.Status;
import domain.Condition;
import domain.Database;
import domain.Row;
import domain.Table;
import utils.StringUtil;
import vo.Response;


public class QueryManager {

    private static final String PATH_PREFIX = "resources/";

    private static final String FILE_SUFFIX = ".tab";

    private static final String ALL = "*";

    private String sql;

    private Database database;

    private DataManager dm;

    private static QueryManager qm;

    private QueryManager(){};

    /**
     * 单例模式
     * @param sql
     * @return
     */
    public static QueryManager getInstance(String sql) {
        if (qm == null) {
            qm = new QueryManager();
        }
        qm.dm = new DataManager();
        qm.sql = sql;
        return qm;
    }

    /**
     * 查询语句解析
     */
    public Response queryParser() {
        Response response = new Response();
        preProcess();
        if (!isEndWithSemicolon()) {
            response.getResults().add("Semi colon missing at end of line");
            response.setStatus(Status.ERROR);
            return response;
        }
        String key = getStatementType();
        switch (key) {
            case "USE":
                Use userStatement = new Use(this.sql);
                userStatement.parser();
                String databaseName = userStatement.getDatabase();
                File[] files = new File(PATH_PREFIX).listFiles();
                boolean flag = false;
                for (File file : files) {
                    if (file.isDirectory()) {
                        if (databaseName.equals(file.getName())) {
                            flag = true;
                            this.database = new Database(databaseName);
                            response.setStatus(Status.OK);
                        }
                    }
                }
                if (!flag) {
                    this.database = null;
                    response.getResults().add("Unknown database");
                    response.setStatus(Status.ERROR);
                }
                break;
            case "CREATE":
                Create createStatement = new Create(this.sql);
                createStatement.parser();
                String createType = createStatement.getCreateType();
                if (ObjectType.DATABASE.equals(createType)) {
                    File file = new File(PATH_PREFIX + createStatement.getDatabase());
                    if (dm.mkdir(file)) {
                        response.setStatus(Status.OK);
                    } else {
                        response.getResults().add("Create failed. Check if it exists database with the same name");
                        response.setStatus(Status.ERROR);
                    }
                } else if (ObjectType.TABLE.equals(createType)) {
                    if (null == this.database) {
                        response.getResults().add("Unknown database. Use a database first!");
                        response.setStatus(Status.ERROR);
                    } else {
                        Table table = new Table();
                        table.setDatabase(this.database);
                        table.setTableName(createStatement.getTable());
                        String cols = "id," + createStatement.getCols();
                        List<String> colNames = Arrays.asList(cols.split(","));
                        table.setColumnNames(colNames);
                        if (dm.createFile(table, PATH_PREFIX, FILE_SUFFIX)) {
                            response.setStatus(Status.OK);
                        } else {
                            response.getResults().add("Create failed. Check if it exists table with the same name");
                            response.setStatus(Status.ERROR);
                        }
                    }
                }
                break;
            case "DROP":
                Drop dropStatement = new Drop(this.sql);
                dropStatement.parser();
                String dropType = dropStatement.getDropType();
                if (ObjectType.DATABASE.equals(dropType)) {
                    File dir = new File(PATH_PREFIX + dropStatement.getDatabase());
                    if (dm.deleteDir(dir)) {
                        if (dropStatement.getDatabase().equals(this.database.getDatabaseName())) {
                            this.database = null;
                        }
                        response.setStatus(Status.OK);
                    } else {
                        response.getResults().add("Drop failed");
                        response.setStatus(Status.ERROR);
                    }
                    
                } else if (ObjectType.TABLE.equals(dropType)) {
                    if (null == this.database) {
                        response.getResults().add("Unknown database. Use a database first!");
                        response.setStatus(Status.ERROR);
                    } else {
                        File file = new File(PATH_PREFIX + this.database.getDatabaseName() + "/" + dropStatement.getTable() + FILE_SUFFIX);
                        if (dm.deleteFile(file)) {
                            response.setStatus(Status.OK);
                        } else {
                            response.getResults().add("Drop failed");
                            response.setStatus(Status.ERROR);
                        }
                    }
                }
                break;
            case "ALTER":
                Alter alterStatement = new Alter(this.sql);
                alterStatement.parser();
                if (null == this.database) {
                    response.getResults().add("Unknown database. Use a database first!");
                    response.setStatus(Status.ERROR);
                } else {
                    String databasePath = this.database.getDatabaseName() + "/";
                    File file = new File(PATH_PREFIX + databasePath + alterStatement.getTable() + FILE_SUFFIX);
                    Table table = dm.readFileToTable(file);
                    if (AlterType.ADD.equals(alterStatement.getAlterType())) {
                        List<String> colNames = table.getColumnNames();
                        colNames.add(alterStatement.getCols());
                        int rowsLen = table.getRowNum();
                        for (int i = 0; i < rowsLen; i++) {
                            Row row = table.getRow(i);
                            row.createColumn(" ");
                        }
                    } else if (AlterType.DROP.equals(alterStatement.getAlterType())) {
                        int index = table.getColIndex(alterStatement.getCols());
                        if (index == -1) {
                            response.getResults().add("Attribute does not exist");
                            response.setStatus(Status.ERROR);
                            return response;
                        }
                        table = dm.deleteSpecifiedcolumn(index, table);
                    }
                    dm.writeDataToFile(file, table);
                    response.setStatus(Status.OK);
                }
                break;
            case "INSERT":
                Insert insertStatement = new Insert(this.sql);
                insertStatement.parser();
                if (null == this.database) {
                    response.getResults().add("Unknown database. Use a database first!");
                    response.setStatus(Status.ERROR);
                } else {
                    String databasePath = this.database.getDatabaseName() + "/";
                    File file = new File(PATH_PREFIX + databasePath + insertStatement.getTable() + FILE_SUFFIX);
                    Table table = dm.readFileToTable(file);
                    String id = idIncrement(table);
                    if (!StringUtil.isEmpty(id)) {
                        Row row = table.createRow();
                        String colsStr = id + "," + insertStatement.getCols();
                        String[] colsArray = colsStr.split(",");
                        for (int i = 0; i < colsArray.length; i++) {
                            row.createColumn();
                            row.getColumn(i).setDataValue(colsArray[i]);
                        }
                        dm.writeDataToFile(file, table);
                        response.setStatus(Status.OK);
                    } 
                }
                break;
            case "SELECT":
                if (null == this.database) {
                    response.getResults().add("Unknown database. Use a database first!");
                    response.setStatus(Status.ERROR);
                    return response;
                }
                System.out.println("Use database: " + this.database.getDatabaseName());
                Select selectStatement = new Select(this.sql);
                selectStatement.parser();
                String tableName = selectStatement.getFrom().getContent();
                if (StringUtil.isEmpty(tableName)) {
                    response.setStatus(Status.ERROR);
                    response.getResults().add("Invalid query");
                    return response;
                }
                String databasePath = this.database.getDatabaseName() + "/";
                File file = new File(PATH_PREFIX + databasePath + selectStatement.getFrom().getContent() + FILE_SUFFIX);
                Table table = dm.readFileToTable(file);
                if (null == table) {
                    response.getResults().add("Table does not exist");
                    response.setStatus(Status.ERROR);
                } else {
                    // 条件过滤行
                    if (!StringUtil.isEmpty(selectStatement.getWhere().getContent())) {
                        Condition condition = selectStatement.getCondition();
                        if (condition != null) {
                            String col = condition.getFieldName();
                            String val = condition.getValue();
                            if (val.contains("'")) {
                                if (StringUtil.isWithDoubleQuotation(val)) {
                                    condition.setValue(StringUtil.trimQuotation(val));
                                } else {
                                    response.setStatus(Status.ERROR);
                                    response.getResults().add("Invalid query");
                                    return response;
                                }
                            }
                            int colIndex = table.getColIndex(col);
                            if (colIndex == -1) {
                                response.getResults().add("Attribute does not exist");
                                response.setStatus(Status.ERROR);
                                return response;
                            }
                            int rowsLen = table.getRowNum();
                            for (int i = rowsLen-1; i >= 0; i--) {
                                Row row = table.getRow(i);
                                String colValue = row.getColumn(colIndex).getDataValue();
                                if (!condition.isTrue(colValue)) {
                                    table.removeRow(i);
                                }
                            }
                        } else {
                            response.setStatus(Status.ERROR);
                            response.getResults().add("Invalid query");
                            return response;
                        }
                    } 
                    if (!ALL.equals(selectStatement.getCols())) {
                        // 查询指定列
                        List<Integer> indexList = new ArrayList<>();
                        String[] colsArr = selectStatement.getCols().replaceAll(" ", "").split(",");
                        for (int i = 0; i < colsArr.length; i++) {
                            int index = table.getColIndex(colsArr[i]);
                            if (index == -1) {
                                response.getResults().add("Attribute does not exist");
                                response.setStatus(Status.ERROR);
                                return response;
                            }
                            indexList.add(index);
                        }
                        table = dm.selectSpecifiedcolumn(indexList, table);
                    }
                    response.setResults(dm.printTable(table));
                    response.setStatus(Status.OK);
                }
                break;
            case "UPDATE":
                response = update(response);             
                break;
            case "DELETE":
                response = delete(response);
                break;
            case "JOIN":
                break;
            default:
                response.setStatus(Status.ERROR);
                response.getResults().add("Syntax error!");
                break;
        }
        if (null == response.getStatus()) {
            response.setStatus(Status.ERROR);
            response.getResults().add("Invalid query");
        }
        return response;
    }

    /**
     * SQL预处理，删掉指令中多余的空格
     */
    private void preProcess() {
        this.sql = StringUtil.rmRedundancy(this.sql);
    }

    /**
     * 从SQL中获取指令类型
     * @return
     */
    private String getStatementType() {
        String regex = "^[a-z]+";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(this.sql);
        while (matcher.find()) {
            return matcher.group().toUpperCase();
        }
        return null;
    }

    /**
     * 是否以分号结尾
     * @return
     */
    private boolean isEndWithSemicolon() {
        return this.sql.endsWith(";");
    }

    private String idIncrement(Table table) {
        int rowNUm = table.getRowNum();
        String newId = null;
        if (rowNUm > 0) {
            Row row = table.getRow(rowNUm - 1);
            int num = Integer.parseInt(row.getColumn(0).getDataValue()) + 1;
            newId = String.valueOf(num);
        } else {
            newId = "1";
        }
        
        return newId;
    }

    private Response delete(Response response) {
        if (null == this.database) {
            response.getResults().add("Unknown database. Use a database first!");
            response.setStatus(Status.ERROR);
            return response;
        }
        Delete deleteStatement = new Delete(this.sql);
        deleteStatement.parser();
        String tableName = deleteStatement.getFrom().getContent();
        if (StringUtil.isEmpty(tableName)) {
            response.setStatus(Status.ERROR);
            response.getResults().add("Invalid query");
            return response;
        }
        String databasePath = this.database.getDatabaseName() + "/";
        File file = new File(PATH_PREFIX + databasePath + tableName + FILE_SUFFIX);
        Table table = dm.readFileToTable(file);
        if (null == table) {
            response.getResults().add("Table does not exist");
            response.setStatus(Status.ERROR);
        } else {
            if (!StringUtil.isEmpty(deleteStatement.getWhere().getContent())) {
                Condition condition = deleteStatement.getCondition();
                if (condition != null) {
                    String col = condition.getFieldName();
                    String val = condition.getValue();
                    if (val.contains("'")) {
                        if (StringUtil.isWithDoubleQuotation(val)) {
                            condition.setValue(StringUtil.trimQuotation(val));
                        } else {
                            response.setStatus(Status.ERROR);
                            response.getResults().add("Invalid query");
                            return response;
                        }
                    }
                    int colIndex = table.getColIndex(col);
                    if (colIndex == -1) {
                        response.getResults().add("Attribute does not exist");
                        response.setStatus(Status.ERROR);
                        return response;
                    }
                    int rowsLen = table.getRowNum();
                    for (int i = rowsLen-1; i >= 0; i--) {
                        Row row = table.getRow(i);
                        String colValue = row.getColumn(colIndex).getDataValue();
                        if (condition.isTrue(colValue)) {
                            table.removeRow(i);
                        }
                    }
                    dm.writeDataToFile(file, table);
                    response.setStatus(Status.OK);
                }
            }
        }
        return response;
    }

    private Response update(Response response) {
        if (null == this.database) {
            response.getResults().add("Unknown database. Use a database first!");
            response.setStatus(Status.ERROR);
            return response;
        }
        Update updateStatement = new Update(this.sql);
        updateStatement.parser();
        String tableName = updateStatement.getTable();
        if (StringUtil.isEmpty(tableName)) {
            response.setStatus(Status.ERROR);
            response.getResults().add("Invalid query");
            return response;
        }
        String databasePath = this.database.getDatabaseName() + "/";
        File file = new File(PATH_PREFIX + databasePath + tableName + FILE_SUFFIX);
        Table table = dm.readFileToTable(file);
        if (null == table) {
            response.getResults().add("Table does not exist");
            response.setStatus(Status.ERROR);
        } else {
            String regex = "(.+)(=)(.+)";
            Matcher matcher = StringUtil.getMatcher(regex, updateStatement.getCols());
            if (matcher != null) {
                String updateCol = matcher.group(1).trim();
                String updateVal = matcher.group(3).trim();
                int updateColIdx = table.getColIndex(updateCol);
                if (updateColIdx == -1) {
                    response.getResults().add("Attribute does not exist");
                    response.setStatus(Status.ERROR);
                    return response;
                }
                if (!StringUtil.isEmpty(updateStatement.getWhere().getContent())) {
                    Condition condition = updateStatement.getCondition();
                    if (condition != null) {
                        String col = condition.getFieldName();
                        String val = condition.getValue();
                        if (val.contains("'")) {
                            if (StringUtil.isWithDoubleQuotation(val)) {
                                condition.setValue(StringUtil.trimQuotation(val));
                            } else {
                                response.setStatus(Status.ERROR);
                                response.getResults().add("Invalid query");
                                return response;
                            }
                        }
                        int colIndex = table.getColIndex(col);
                        if (colIndex == -1) {
                            response.getResults().add("Attribute does not exist");
                            response.setStatus(Status.ERROR);
                            return response;
                        }
                        int rowsLen = table.getRowNum();
                        for (int i = rowsLen-1; i >= 0; i--) {
                            Row row = table.getRow(i);
                            String colValue = row.getColumn(colIndex).getDataValue();
                            if (condition.isTrue(colValue)) {
                                row.getColumn(updateColIdx).setDataValue(updateVal);
                            }
                        }
                    } else {
                        response.setStatus(Status.ERROR);
                        response.getResults().add("Invalid query");
                        return response;
                    }
                } else {
                    int rowsLen = table.getRowNum();
                    for (int i = rowsLen-1; i >= 0; i--) {
                        Row row = table.getRow(i);
                        row.getColumn(updateColIdx).setDataValue(updateVal);
                    }
                }
                dm.writeDataToFile(file, table);
                response.setStatus(Status.OK);
            }
        }
        return response;
    }
}
