import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final static String driver ="com.mysql.jdbc.Driver";
    private final static String url ="jdbc:MySQL://localhost:3306/test?useSSL=false";
    private final static String user ="root";
    private final static String password ="123456";

    public static void createDatabase(String database) {
        String createDatabaseSql = "CREATE SCHEMA " + database;
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection("jdbc:MySQL://localhost:3306/mysql?useSSL=false", user, password);
            Statement statement = conn.createStatement();
            statement.executeUpdate(createDatabaseSql);
            statement.close();
            conn.close();
        } catch(ClassNotFoundException e) {
            System.out.println("Sorry, cannot find the Driver!");
            e.printStackTrace();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTable(String tableName, String[] args) {
        StringBuilder sbCreate = new StringBuilder();
        sbCreate.append("CREATE TABLE ");
        sbCreate.append(tableName);
        sbCreate.append("(");
        for (String arg : args) {
            sbCreate.append(arg);
            sbCreate.append(",");
        }
        sbCreate.deleteCharAt(sbCreate.length() - 1); // delete the last comma
        sbCreate.append(")");
        String createTableSql = sbCreate.toString();
        executeSQL(createTableSql);
    }

    public static void setPrimaryKey(String tableName, String primaryKey) {
        if (primaryKey != null) {
            String primaryKeySql = "ALTER TABLE " + tableName + " ADD PRIMARY KEY (" + primaryKey + ")";
            executeSQL(primaryKeySql);
        }
    }

    public static void setForeignKey(String tableName, String foreignKey, String targetTable, String targetArg) {
        if (foreignKey != null) {
            String foreignKeySql = "ALTER TABLE " + tableName + " ADD FOREIGN KEY (" + foreignKey + ") REFERENCES " + targetTable + "(" + targetArg + ")";
            executeSQL(foreignKeySql);
        }
    }

    public static boolean hasTable(String tableName) {
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, password);
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet set = meta.getTables(null, null, tableName, null);
            if (set.next()) {
                return true;
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry, cannot find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void dropTable(String tableName) {
        String dropSql = "DROP TABLE " + tableName;
        executeSQL(dropSql);
    }

    public static void insertData(String tableName, String[] values) {
        StringBuilder sbInsert = new StringBuilder();
        sbInsert.append("INSERT INTO ");
        sbInsert.append(tableName);
        sbInsert.append(" VALUES(");
        for (String value : values) {
            sbInsert.append("'");
            sbInsert.append(value);
            sbInsert.append("'");
            sbInsert.append(",");
        }
        sbInsert.deleteCharAt(sbInsert.length() - 1); // delete the last comma
        sbInsert.append(")");
        String insertSql = sbInsert.toString();
        executeSQL(insertSql);
    }

    public static void deleteData(String tableName, String index, String value) {
        StringBuilder sbDelete = new StringBuilder();
        sbDelete.append("DELETE FROM ");
        sbDelete.append(tableName);
        sbDelete.append(" WHERE ");
        sbDelete.append(index);
        sbDelete.append("='");
        sbDelete.append(value);
        sbDelete.append("'");
        String deleteSql = sbDelete.toString();
        executeSQL(deleteSql);
    }

    public static void updateData(String tableName, String index, String value, String[] args, String[] updateValues) {
        StringBuilder sbUpdate = new StringBuilder();
        sbUpdate.append("UPDATE ");
        sbUpdate.append(tableName);
        sbUpdate.append(" SET ");
        for (int i = 0; i < args.length; i++) {
            sbUpdate.append(args[i]);
            sbUpdate.append("='");
            sbUpdate.append(updateValues[i]);
            sbUpdate.append("',");
        }
        sbUpdate.deleteCharAt(sbUpdate.length() - 1);
        sbUpdate.append(" WHERE ");
        sbUpdate.append(index);
        sbUpdate.append("='");
        sbUpdate.append(value);
        sbUpdate.append("'");
        String updateSql = sbUpdate.toString();
        executeSQL(updateSql);
    }

    public static List<List<String>> queryData(String tableName, String index, String value, String[] args) {
        StringBuilder sbQuery = new StringBuilder();
        sbQuery.append("SELECT ");
        for (String arg : args) {
            sbQuery.append(arg);
            sbQuery.append(",");
        }
        sbQuery.deleteCharAt(sbQuery.length() - 1);
        sbQuery.append(" FROM ");
        sbQuery.append(tableName);
        sbQuery.append(" WHERE ");
        sbQuery.append(index);
        sbQuery.append("='");
        sbQuery.append(value);
        sbQuery.append("'");
        String querySql = sbQuery.toString();
        List<List<String>> list = new ArrayList<>();
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(querySql);
            while (resultSet.next()) {
                List<String> list1 = new ArrayList<>();
                for (String arg : args) {
                    list1.add(resultSet.getString(arg));
                }
                list.add(list1);
            }
            statement.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry, cannot find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static void executeSQL(String sql) {
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry, cannot find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
