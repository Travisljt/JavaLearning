package components;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import domain.Column;
import domain.Row;
import domain.Table;

public class DataManager {
    public List<String> printTable(Table table) {
        if (null == table) {
            return null;
        }
        List<String> columnNames = table.getColumnNames();
        List<String> results = new LinkedList<>();
        if (columnNames == null || columnNames.isEmpty()) {
            results.add("Not found the Column");
            return results;
        }
        ListIterator<String> colNameIter = columnNames.listIterator();
        StringBuilder sb = new StringBuilder();
        while (colNameIter.hasNext()) {
            sb.append(colNameIter.next()).append("\t");
        }
        results.add(sb.toString());

        int rowsLen = table.getRowNum();
        for (int i = 0; i < rowsLen; i++) {
            Row row = table.getRow(i);
            int colsLen = row.getColNum();
            sb = new StringBuilder();
            for (int j = 0; j < colsLen; j++) {
                sb.append(row.getColumn(j).getDataValue()).append("\t");
            }
            results.add(sb.toString());
        }
        return results;
    }

    public Table readFileToTable(File file) {
        Table table = new Table();

        // File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Not found file");
            return null;
        }

        FileReader fileReader = null;
        BufferedReader reader = null;
        try {
            fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line = null;
            int lineCount = 0;
            while (null != (line = reader.readLine()) && !line.equals("")) {
                String[] cols = line.split("\t");
                // first line need to record colName
                if (lineCount == 0) {
                    List<String> colNames = new ArrayList<>(Arrays.asList(cols));
                    table.setColumnNames(colNames);
                } else {
                    Row row = table.createRow();
                    for (String col : cols) {
                        Column column = row.createColumn();
                        column.setDataValue(col);
                    }
                }
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return table;
    }

    public void writeDataToFile(File file, Table table) {
        if (!file.exists()) {
            System.out.println("Not found file");
            return ;
        }

        FileWriter fileWriter = null;
        BufferedWriter writer = null;
        try {
            fileWriter = new FileWriter(file);
            writer = new BufferedWriter(fileWriter);
            // TODO
            List<String> colNames = table.getColumnNames();
            for (String colName : colNames) {
                writer.write(colName);
                writer.write("\t");
            }
            writer.newLine();
            int rowsLen = table.getRowNum();
            for (int i = 0; i < rowsLen; i++) {
                Row row = table.getRow(i);
                int colsLen = row.getColNum();
                for (int j = 0; j < colsLen; j++) {
                    Column column = row.getColumn(j);
                    writer.write(column.getDataValue());
                    writer.write("\t");
                }
                writer.newLine();
            }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            try {
                writer.close();
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public boolean mkdir(File file) {
        if (!file.exists()) {
            return file.mkdir();
        }
        return false;
    }

    public boolean createFile(Table table, String pathPrefix, String fileSuffix) {
        File file = new File(pathPrefix + table.getDatabase().getDatabaseName() + "/" + table.getTableName() + fileSuffix);

        if (file.exists()) {
            return false;
        }

        FileWriter fileWriter = null;
        BufferedWriter writer = null;
        try {
            fileWriter = new FileWriter(file);
            writer = new BufferedWriter(fileWriter);

            List<String> colNames = table.getColumnNames();
            for (String colName : colNames) {
                writer.write(colName);
                writer.write("\t");
            }
            writer.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                fileWriter.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Table selectSpecifiedcolumn(List<Integer> indexList, Table table) {
        List<String> colNames = table.getColumnNames();
        int colNum = colNames.size();
        for (int i = colNum - 1; i >= 0; i--) {
            if (!indexList.contains(i)) {
                colNames.remove(i);
            }
        }
        table.setColumnNames(colNames);
        
        int rowsLen = table.getRowNum();
        for (int i = 0; i < rowsLen; i++) {
            Row row = table.getRow(i);
            int colsLen = row.getColNum();
            for (int j = colsLen - 1; j >= 0; j--) {
                if (!indexList.contains(j)) {
                    row.removeColumn(j);
                }
            }
        }
        return table;
    }

    public Table deleteSpecifiedcolumn(int index, Table table) {
        List<String> colNames = table.getColumnNames();
        int colNum = colNames.size();
        for (int i = colNum - 1; i >= 0; i--) {
            if (index == i) {
                colNames.remove(i);
            }
        }
        table.setColumnNames(colNames);

        int rowsLen = table.getRowNum();
        for (int i = 0; i < rowsLen; i++) {
            Row row = table.getRow(i);
            int colsLen = row.getColNum();
            for (int j = colsLen - 1; j >= 0; j--) {
                if (index == j) {
                    row.removeColumn(j);
                }
            }
        }
        return table;
    }

    public boolean deleteFile(File file) {
        if (file.exists() && file.isFile()) {
            file.delete();
            return true;
        }
        return false;
    }

    public boolean deleteFolder(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                return deleteFile(file);
            } else {
                return deleteDir(file);
            }
        }
        return false;
    }

    public boolean deleteDir(File dir) {
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
                if (files[i].isFile()) {
                    if (!deleteFile(files[i])) {
                        return false;
                    }
                } else {
                    if (!deleteDir(files[i])) {
                        return false;
                    }
                }
            }
            return dir.delete();
        }
        return false;
    }
}
