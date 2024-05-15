package jdbc;

import java.sql.*;
import java.util.Arrays;

public class MusicDML {
    public static void main(String[] args) {
        // this format is used prior to JDBC 4.0
//        try {
//            Class.forName("com.mysql.jdbc.D river");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/music",
                "root", System.getenv("MySql_Password"));
             Statement statement = connection.createStatement()) {
//            String text = "bharath";
//            String text = "Argus";
//            String query = "Select *from artists where artist_name= '%s'".formatted(text);
//            boolean result = statement.execute(query);  //.execute always returns true when its used with SELECT Statement
//            // means this boolean result cant be used to test the existing of a record in a table.
//            System.out.println("Result: " + result);
//
//            var rs = statement.getResultSet();
//            boolean found = (rs != null && rs.next());
//            System.out.println("Artist Name : " + (found ? " Found " : " Not Found "));

            String tableName = "music.artists";
            String columName = "artist_name";
            String columnValue = "Argus";
            if (!executeSelect(statement, tableName, columName, columnValue)) {
                System.out.println("This records is not found please add the record!");
                insertRecord(statement, tableName, new String[]{columName}, new String[]{columnValue});
            } else {
//                deleteRecord(statement, tableName, columName, columnValue);
//                updateRecord(statement, tableName, columName, columnValue, columName, columnValue.toUpperCase());
                try {
                    deleteArtistAlbum(connection, statement, columnValue, columnValue);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                executeSelect(statement, "music.albumview", "album_name", columName);
                executeSelect(statement, "music.albums", "album_name", columName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean printRecords(ResultSet resultSet) throws SQLException {
        boolean foundData = false;
        var meta = resultSet.getMetaData();
        System.out.println("=".repeat(90));
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            System.out.printf("%-15s", meta.getColumnName(i).toUpperCase());
        }
        System.out.println();
        while (resultSet.next()) {
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                System.out.printf("%-15s", resultSet.getString(i));
            }
            System.out.println();
            foundData = true;
        }
        return foundData;
    }

    public static boolean executeSelect(Statement stat, String table, String colName, String colValue) throws SQLException {
        String query = "Select * from %s where %s='%s'".formatted(table, colName, colValue);
        var rs = stat.executeQuery(query);
        if (rs != null)
            return printRecords(rs);
        else
            return false;
    }

    private static boolean insertRecord(Statement statement, String table,
                                        String[] columnNames, String[] columnValues)
            throws SQLException {

        String colNames = String.join(",", columnNames);
        String colValues = String.join("','", columnValues);
        String query = "INSERT INTO %s (%s) VALUES ('%s')"
                .formatted(table, colNames, colValues);
        System.out.println(query);
        boolean insertResult = statement.execute(query);
        System.out.println("Result: " + insertResult);
        int recordsInserted = statement.getUpdateCount();
        if (recordsInserted > 0) {
            executeSelect(statement, table,
                    columnNames[0], columnValues[0]);
        }
        return recordsInserted > 0;
    }

    public static boolean deleteRecord(Statement stat, String table, String colName, String colValue) throws SQLException {
        String query = "Delete from %s where %s ='%s'".formatted(table, colName, colValue);
        System.out.println(query);
        stat.execute(query);
        int recordsDeleted = stat.getUpdateCount();
        if (recordsDeleted > 0) {
            executeSelect(stat, table, colName, colValue);
        }
        return recordsDeleted > 0;
    }

    public static boolean updateRecord(Statement stat, String table,
                                       String matchedCol, String matchValue, String colName, String colValue) throws SQLException {
        String query = "Update %s SET %s = '%s' where %s ='%s'".formatted(table, colName, colValue, matchedCol, matchValue);
        System.out.println(query);
        stat.execute(query);
        int recordsUpdated = stat.getUpdateCount();
        if (recordsUpdated > 0) {
            executeSelect(stat, table, colName, colValue);
        }
        return recordsUpdated > 0;
    }

    private static void deleteArtistAlbum(Connection conn, Statement statement,
                                          String artistName, String albumName)
            throws SQLException {

        try {
            System.out.println("AUTOCOMMIT = " + conn.getAutoCommit());
            conn.setAutoCommit(false);
            String deleteSongs = """
                    DELETE FROM music.songs WHERE album_id =
                    (SELECT ALBUM_ID from music.albums WHERE album_name = '%s')"""
                    .formatted(albumName);

            String deleteAlbums = "DELETE FROM music.albums WHERE album_name='%s'"
                    .formatted(albumName);
            String deleteArtist = "DELETE FROM music.artists WHERE artist_name='%s'"
                    .formatted(artistName);
            statement.addBatch(deleteSongs);
            statement.addBatch(deleteAlbums);
            statement.addBatch(deleteArtist);
            int[] results = statement.executeBatch();
            System.out.println(Arrays.toString(results));
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();   // this will set the state of the temporary storage back to original storage state when the transaction started
        }
        conn.setAutoCommit(true);
    }
}
