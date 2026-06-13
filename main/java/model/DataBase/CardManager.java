package model.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardManager {
    private Connection connection;


    public CardManager(String dbName) throws SQLException {
        String url = "jdbc:sqlite:" + dbName + ".db";
        this.connection = DriverManager.getConnection(url);
    }

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS cards (" +
                "card_name TEXT NOT NULL)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void saveCard(String cardName) throws SQLException {
        String sql = "INSERT INTO cards(card_name) VALUES(?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cardName);
            pstmt.executeUpdate();
        }
    }

    public List<String> loadAllCardNames() throws SQLException {
        List<String> cardNames = new ArrayList<>();

        String sql = "SELECT card_name FROM cards";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String cardName = rs.getString("card_name");
                cardNames.add(cardName);
            }
        }

        return cardNames;
    }

    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }


    public void deleteAllCards()  {
        String sql = "DELETE FROM cards";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (Exception e){
            e.fillInStackTrace();
        }
    }
    public void connect(String dbName) throws SQLException {
        String url = "jdbc:sqlite:" + dbName + ".db";
        connection = DriverManager.getConnection(url);
    }
}

