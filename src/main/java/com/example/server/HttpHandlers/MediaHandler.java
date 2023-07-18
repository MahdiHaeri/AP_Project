package com.example.server.HttpHandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.example.server.controllers.UserController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Collections;

public class MediaHandler implements HttpHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		UserController userController = null;
		try {
			userController = new UserController();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		String response = "";
		String method = exchange.getRequestMethod();
		String path = exchange.getRequestURI().getPath();
		String[] splitedPath = path.split("/");
		// ip:port/media/userID/mediaName/mediaType
		if (splitedPath.length != 5) {
			response = "unknown-request";
		} else switch (method) {
			case "GET":
				File file;
				try {
					file = new File("src/main/java/com/example/server/assets/" + splitedPath[2] + "/" + splitedPath[3] + "." + splitedPath[4]);
				} catch (NullPointerException e) {
					response = "no-media";
					break;
				}

				exchange.getResponseHeaders().put("Content-Type", Collections.singletonList(splitedPath[4]));
				exchange.sendResponseHeaders(200, file.length());
				OutputStream outputStream = exchange.getResponseBody();
				Files.copy(file.toPath(), outputStream);
				outputStream.close();
				return;
			case "POST":
				if (!userController.isUserExists(splitedPath[2])) {
					response = "user-not-found";
					break;
				}
//				if (!splitedPath[2].equals(ExtractUserAuth.extract(exchange))) {
//					response = "permission-denied";
//					break;
//				}
				Files.copy(exchange.getRequestBody(), Path.of("src/main/java/com/example/server/assets/" , splitedPath[2], splitedPath[3] + "." + splitedPath[4]), StandardCopyOption.REPLACE_EXISTING);
				exchange.getRequestBody().close();
				response = "done";
				break;
			default:
				response = "unknown-request2";
				break;
		}

		exchange.sendResponseHeaders(200, response.getBytes().length);
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}