package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controllers.UserController;
import org.example.models.User;

import java.sql.SQLException;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        try {
            UserController userController = new UserController();
//            userController.deleteUser("1");
//            userController.createUser("1", "mahdi", "haeri", "mahdihaerim@gmail.com", "123456789", "123456", "Iran", new Date());
//            userController.updateUser("1", "mahdi", "haeri", "mohammadmahdihaeri@gmail.com", "123456789", "123456", "Iran", new Date());
//
//            User user = userController.getUser("1");
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = objectMapper.writeValueAsString(user);
//            System.out.println(json);

//            userController.createUser("2", "mobin", "zaki", "mobinzaki@gmail.com", "123456789", "123456", "Iran", new Date());
        } catch (SQLException e) {
            throw new RuntimeException(e);
////        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
        }
    }
}