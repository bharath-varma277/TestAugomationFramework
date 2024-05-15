package jdbc.callablestatement;

import com.mysql.cj.jdbc.MysqlDataSource;
import jdbc.PreparedStatementMain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Map;
import java.util.stream.Collectors;

public class MusicMain {
    private static final int Artist_Column = 0;
    private static final int Album_Column = 1;
    private static final int SONG_COLUMN = 3;

    public static void main(String[] args) {

        Map<String, Map<String, String>> albums = null;
        try (var lines = Files.lines(Path.of("NewAlbums.csv"))) {
            albums = lines.map(s -> s.split(","))
                    .collect(Collectors.groupingBy(s -> s[Artist_Column], Collectors.groupingBy(s -> s[Album_Column],
                            Collectors.mapping(s -> s[SONG_COLUMN]
                                    , Collectors.joining("\",\"", "[\"", "\"]")))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        albums.forEach((a, aa) -> {
            aa.forEach((k, v) -> {
                System.out.println(k + ": " + v);
            });
        });


        var dataSource = new MysqlDataSource();

        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("music");

        try (Connection connection = dataSource.getConnection(
                "root",
                System.getenv("MySql_Password"))
        ) {
            CallableStatement cs = connection.prepareCall("CALL music.addAlbum(?,?,?)");
            albums.forEach((a, aa) -> {
                aa.forEach((album, s) -> {
                    try {
                        cs.setString(1, a);
                        cs.setString(2, album);
                        cs.setString(3, s);
                        cs.execute();
                    } catch (SQLException e) {
                        System.err.println(e.getErrorCode() + " " + e.getMessage());
                    }
                });
            });
            String sql = "SELECT * FROM music.albumview WHERE artist_name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "Bob Dylan");
            ResultSet resultSet = ps.executeQuery();
            PreparedStatementMain.printRecords(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
