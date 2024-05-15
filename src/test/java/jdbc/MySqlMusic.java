package jdbc;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class MySqlMusic {
    private final static String connectionURL = "jdbc:mysql://localhost:3306/music";

    public static void main(String[] args) {
        JPasswordField jpf = new JPasswordField();
        int ok = JOptionPane.showConfirmDialog(null, jpf, "Enter DB password", JOptionPane.OK_CANCEL_OPTION);
        System.out.println("OK:" + ok + ", " + JOptionPane.OK_OPTION);
        final char[] password = (ok == JOptionPane.OK_OPTION) ? jpf.getPassword() : null;
        try (Connection con = DriverManager.getConnection(connectionURL, "root", String.valueOf(password))) {
            System.out.println("You have successfully made you're connection");
            Arrays.fill(password, ' ');
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
