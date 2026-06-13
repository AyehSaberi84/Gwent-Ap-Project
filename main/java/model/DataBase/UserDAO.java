package model.DataBase;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        this.connection = DatabaseConnector.connect();
    }

    public boolean registerUser(String username, String password, String nickName, String email, String answer) {
        String sql = "INSERT INTO users(username, password, nickname, email, answer) VALUES (?, ?, ?, ?, ?)";
        boolean result = false;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, nickName);
            pstmt.setString(4, email);
            pstmt.setString(5, answer);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) result = true;
            else System.out.println("Error in registering!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public boolean checkUsernameExistence(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        boolean result = false;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public void makeAllUsers (){
        String sql = "SELECT * FROM users";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            ArrayList<User> users = new ArrayList<>();
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String nickname = rs.getString("nickname");
                String email = rs.getString("email");
                String answer = rs.getString("answer");

                User user = new User(username, password, nickname, email, answer);
                users.add(user);
            }
            User.setAllUsers(users);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateUsername(String oldUsername, String newUsername) {
        String sql = "UPDATE users SET username = ? WHERE username = ?";
        boolean result = false;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newUsername);
            pstmt.setString(2, oldUsername);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            } else {
                System.out.println("Error in updating username!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updatePassword(String username, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        boolean result = false;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            } else {
                System.out.println("Error in updating password!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateNickname(String username, String newNickname) {
        String sql = "UPDATE users SET nickname = ? WHERE username = ?";
        boolean result = false;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newNickname);
            pstmt.setString(2, username);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            } else {
                System.out.println("Error in updating nickname!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateEmail(String username, String newEmail) {
        String sql = "UPDATE users SET email = ? WHERE username = ?";
        boolean result = false;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, newEmail);
            pstmt.setString(2, username);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            } else {
                System.out.println("Error in updating email!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}

