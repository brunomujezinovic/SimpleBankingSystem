package banking;


import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DataBaseManager {
    private Connection conn;
    Map<String, Account> creditCardDetails = new HashMap<>();


    public void makeConnection() {
        String createTable = "CREATE TABLE IF NOT EXISTS card (\n"
                + "    id integer PRIMARY KEY,\n"
                + "    number text NOT NULL UNIQUE,\n"
                + "    pin text NOT NULL,\n"
                + "    balance integer\n"
                + ");";

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:card.s3db");
            Statement stmt = conn.createStatement();

            // create a new table if it doesn't exist
            stmt.execute(createTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addAccount(Account a) {
        String sql = "INSERT INTO card (number, pin, balance) VALUES(?, ?, ?)";
        RuntimeException exception = new RuntimeException();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, a.getCardNumber());
            pstmt.setString(2, a.getPin());
            pstmt.setInt(3, a.getBalance());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        creditCardDetails.put(a.getCardNumber(), a);
    }

    public boolean isCardInDatabase(String number, String pin) {
        String sql = "SELECT number, pin FROM card";
        try {
            Connection conn = this.conn;
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (number.equals(resultSet.getString("number")) &&
                        pin.equals(resultSet.getString("pin"))) {
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}