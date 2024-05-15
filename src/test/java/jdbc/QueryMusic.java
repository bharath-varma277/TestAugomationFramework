package jdbc;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.Properties;

public class QueryMusic {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(Files.newInputStream(Path.of("music.properties"), StandardOpenOption.READ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var dataSource = new MysqlDataSource();
        dataSource.setServerName(properties.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(properties.getProperty("port")));
        dataSource.setDatabaseName(properties.getProperty("databaseName"));

//        String query = "Select * from albums";
        String query = "SELECT * FROM albumview where album_name = \"Argus\";";
        try (Connection connection = dataSource.getConnection(properties.getProperty("user"), System.getenv("MySql_Password"))) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                System.out.printf("%s %s %d %s %n", resultSetMetaData.getColumnName(i),
                        resultSetMetaData.getColumnTypeName(i), i, resultSetMetaData.getColumnTypeName(i));
            }
            System.out.println("-".repeat(90));
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(3) + " " + resultSet.getString("album_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
