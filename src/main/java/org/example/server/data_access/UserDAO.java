package org.example.server.data_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.example.server.models.Bio;
import org.example.server.models.User;

public class UserDAO {
    private final Connection connection;
    public UserDAO() throws SQLException {
        connection = DatabaseConnectionManager.getConnection();
        createUserTable();
    }


    public void createUserTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users (id VARCHAR(36) PRIMARY KEY, first_name VARCHAR(255), last_name VARCHAR(255), email VARCHAR(255), phone_number VARCHAR(255), password VARCHAR(255), country VARCHAR(255), birthday DATE, created_at DATE)");
        statement.executeUpdate();
    }

    public void createBioTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS bio (user_id VARCHAR(36) PRIMARY KEY, biography VARCHAR(255), location VARCHAR(255), website VARCHAR(255))");
        statement.executeUpdate();
    }

    public void saveUser(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users (id, first_name, last_name, email, phone_number, password, country, birthday, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, user.getId());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.setString(4, user.getEmail());
        statement.setString(5, user.getPhoneNumber());
        statement.setString(6, user.getPassword());
        statement.setString(7, user.getCountry());
        statement.setDate(8, new java.sql.Date(user.getBirthday().getTime()));
        statement.setDate(9, new java.sql.Date(user.getCreatedAt().getTime()));

        statement.executeUpdate();
    }

    public void saveBio(String userId, Bio bio) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO bio (user_id, biography, location, website) VALUES (?, ?, ?, ?)");
        statement.setString(1, userId);
        statement.setString(2, bio.getBiography());
        statement.setString(3, bio.getLocation());
        statement.setString(4, bio.getWebsite());

        statement.executeUpdate();
    }

    public void deleteUser(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
        statement.setString(1, user.getId());
        statement.executeUpdate();
    }

    public void deleteBio(String userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM bio WHERE user_id = ?");
        statement.setString(1, userId);
        statement.executeUpdate();
    }

    public void deleteUsers() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users");
        statement.executeUpdate();
    }

    public void deleteBios() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM bio");
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

    public void updateBio(String userId, Bio bio) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE bio SET biography = ?, location = ?, website = ? WHERE user_id = ?");
        statement.setString(1, bio.getBiography());
        statement.setString(2, bio.getLocation());
        statement.setString(3, bio.getWebsite());
        statement.setString(4, userId);

        statement.executeUpdate();
    }

    public User getUser(String userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
        statement.setString(1, userId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getString("id"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setEmail(resultSet.getString("email"));
            user.setPhoneNumber(resultSet.getString("phone_number"));
            user.setPassword(resultSet.getString("password"));
            user.setCountry(resultSet.getString("country"));
            user.setBirthday(resultSet.getDate("birthday"));
            user.setCreatedAt(resultSet.getDate("created_at"));
            return user;
        }

        return null; // User not found
    }

    public Bio getBio(String userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM bio WHERE user_id = ?");
        statement.setString(1, userId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Bio bio = new Bio();
            bio.setBiography(resultSet.getString("biography"));
            bio.setLocation(resultSet.getString("location"));
            bio.setWebsite(resultSet.getString("website"));
            return bio;
        }

        return null; // Bio not found
    }

    public ArrayList<User> getUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<User>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getString("id"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setEmail(resultSet.getString("email"));
            user.setPhoneNumber(resultSet.getString("phone_number"));
            user.setPassword(resultSet.getString("password"));
            user.setCountry(resultSet.getString("country"));
            user.setBirthday(resultSet.getDate("birthday"));
            user.setCreatedAt(resultSet.getDate("created_at"));
            users.add(user);
        }

        return users;
    }

    public ArrayList<Bio> getBios() throws SQLException {
        ArrayList<Bio> bios = new ArrayList<Bio>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM bio");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Bio bio = new Bio();
            bio.setBiography(resultSet.getString("biography"));
            bio.setLocation(resultSet.getString("location"));
            bio.setWebsite(resultSet.getString("website"));
            bios.add(bio);
        }

        return bios;
    }
}
