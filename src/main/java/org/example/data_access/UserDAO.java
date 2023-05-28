package org.example.data_access;

import org.example.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO {
    private final Connection connection;
    public UserDAO() throws SQLException {
        connection = DatabaseConnectionManager.getConnection();
        createUserTable();
    }

    public void createUserTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users (id VARCHAR(36) PRIMARY KEY, first_name VARCHAR(255), last_name VARCHAR(255), email VARCHAR(255), phone_number VARCHAR(255), password VARCHAR(255), country VARCHAR(255), birthday DATE)");
        statement.executeUpdate();
    }

    public void saveUser(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, user.getId());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.setString(4, user.getEmail());
        statement.setString(5, user.getPhoneNumber());
        statement.setString(6, user.getPassword());
        statement.setString(7, user.getCountry());
        statement.setDate(8, new java.sql.Date(user.getBirthday().getTime()));
        statement.executeUpdate();
    }

    public void deleteUser(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
        statement.setString(1, user.getId());
        statement.executeUpdate();
    }

    public void updateUser(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE users SET first_name = ?, last_name = ?, email = ?, phone_number = ?, password = ?, country = ?, birthday = ? WHERE id = ?");
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getEmail());
        statement.setString(4, user.getPhoneNumber());
        statement.setString(5, user.getPassword());
        statement.setString(6, user.getCountry());
        statement.setDate(7, new java.sql.Date(user.getBirthday().getTime()));
        statement.setString(8, user.getId());
        statement.executeUpdate();
    }
}
