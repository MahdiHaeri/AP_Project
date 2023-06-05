package org.example.server.data_access;

import org.example.server.models.Bio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BioDAO {
    private final Connection connection;

    public BioDAO() throws SQLException {
        connection = DatabaseConnectionManager.getConnection();
        createBioTable();
    }

    public void createBioTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS bios (user_id VARCHAR(36) PRIMARY KEY, biography VARCHAR(255), location VARCHAR(255), website VARCHAR(255))");
        statement.executeUpdate();
    }

    public void saveBio(Bio bio) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO bios (user_id, biography, location, website) VALUES (?, ?, ?, ?)");
        statement.setString(1, bio.getUserId());
        statement.setString(2, bio.getBiography());
        statement.setString(3, bio.getLocation());
        statement.setString(4, bio.getWebsite());

        statement.executeUpdate();
    }

    public void deleteBio(String userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM bios WHERE user_id = ?");
        statement.setString(1, userId);
        statement.executeUpdate();
    }

    public void deleteBios() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM bios");
        statement.executeUpdate();
    }
    public void updateBio(Bio bio) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE bios SET biography = ?, location = ?, website = ? WHERE user_id = ?");
        statement.setString(1, bio.getBiography());
        statement.setString(2, bio.getLocation());
        statement.setString(3, bio.getWebsite());
        statement.executeUpdate();
    }

    public Bio getBio(String userId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM bios WHERE user_id = ?");
        statement.setString(1, userId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Bio bio = new Bio();
            bio.setUserId(resultSet.getString("user_id"));
            bio.setBiography(resultSet.getString("biography"));
            bio.setLocation(resultSet.getString("location"));
            bio.setWebsite(resultSet.getString("website"));
            return bio;
        }

        return null;
    }

    public ArrayList<Bio> getBios() throws SQLException {
        ArrayList<Bio> bios = new ArrayList<Bio>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM bios");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Bio bio = new Bio();
            bio.setUserId(resultSet.getString("user_id"));
            bio.setBiography(resultSet.getString("biography"));
            bio.setLocation(resultSet.getString("location"));
            bio.setWebsite(resultSet.getString("website"));
            bios.add(bio);
        }

        return bios;
    }
}