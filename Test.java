import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        // createDatabase("test2");
        String tableName = "User";
        String[] values = new String[]{"USER_NAME varchar(20) not null", "PASSWORD varchar(20) not null"};
        //createTable(tableName, values);
        //setPrimaryKey(tableName, "USER_NAME");
        String[] insertedValues = new String[]{"User5", "123456"};
        //insertData(tableName, insertedValues);
        String[] updateArgs = new String[]{"PASSWORD"};
        String[] updateValues = new String[]{"User4", "123"};
        //updateData(tableName, "USER_NAME", "User3", updateArgs, updateValues);
        //deleteData(tableName, "USER_NAME", "User2");
        //dropTable(tableName);
        //List<List<String>> list = Database.queryData(tableName, null, "123", updateArgs);
        //System.out.println(list);
    }
}
