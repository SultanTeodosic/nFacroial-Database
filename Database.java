import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Sultan
public class Database {
    private Map<String, Table> tables;
    private String name;

    public Database(String name) {
        tables = new HashMap<>();
        this.name = name;
    }

    public void addTable(String tableName, String[] columns, Class[] columnTypes,
                         String[] constraints) {
        tables.put(tableName, new Table(constraints, tableName, columnTypes, columns));
    }

    public Table getTable(String tableName) {
        return tables.get(tableName);
    }

    public void keep() {
    }

    public static Database send(String name) {
        return new Database(name);
    }
}

class Table {
    private List<Map<String, Object>> rows;
    private String[] constraints;
    private Class[] columnTypes;
    private String[] columns;
    private String name;

    public Table(String[] constraints, String name, Class[] columnTypes, String[] columns) {
        rows = new ArrayList<>();
        this.constraints = constraints;
        this.name = name;
        this.columnTypes = columnTypes;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public String[] getColumns() {
        return columns;
    }

    public Class[] getColumnTypes() {
        return columnTypes;
    }

    public String[] getConstraints() {
        return constraints;
    }

    public void addRow(Object[] values) {
        if (values.length != columns.length) {
            throw new IllegalArgumentException(
                    name + "->" + " В таблице неправильное к-во значений");
        }
        Map<String, Object> row = new HashMap<>();
        for (int i = 0; i < columns.length; i++) {
            row.put(columns[i], values[i]);
        }
        rows.add(row);
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }
}

class TableOperator {
    private Database database;

    public TableOperator(Database database) {
        this.database = database;
    }

    public void create(String tableName, String[] columns, Class[] columnTypes,
                       String[] constraints) {
        database.addTable(tableName, columns, columnTypes, constraints);
    }

    public void add(String tableName, Object[] values) {
        Table table = database.getTable(tableName);
        table.addRow(values);
    }

    // nFactorial Incubator 2023 applicant- Sultan Kadyr, Astana, KZ.
    public List<Map<String, Object>> select(String tableName, String[] columns, String filter,
                                            String[] params) {
        Table table = database.getTable(tableName);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> row : table.getRows()) {
            if (filter == null || filter.isEmpty()) {
                result.add(row);
            }
            else {
                boolean condition = evaluateFilter(filter, row, table.getColumnTypes(), params);
                if (condition) {
                    result.add(row);
                }
            }
        }
        return result;
    }

    private boolean evaluateFilter(String filter, Map<String, Object> row, Class[] columnTypes,
                                   String[] params) {
        return true;
    }

    public void update(String tableName, String filter, Object[] values) {
        Table table = database.getTable(tableName);
        List<Map<String, Object>> rows = table.getRows();
        for (Map<String, Object> row : rows) {
            if (filter == null || filter.isEmpty()) {
                for (int i = 0; i < values.length; i++) {
                    row.put(table.getColumns()[i], values[i]);
                }
            }
            else {
                boolean condition = evaluateFilter(filter, tableName, values, values);
            }
        }
    }
}


