package org.example.server.utils;

import com.sun.net.httpserver.HttpExchange;

public class ExtractUserAuth {
	public static String extract(HttpExchange exchange) {
		for (String str : exchange.getRequestHeaders().keySet()) {
			System.out.println(str + " : " + exchange.getRequestHeaders().get(str));
			if (JwtAuth.jws(str).equals(exchange.getRequestHeaders().getFirst(str))) {
				System.out.println(str);
				return str.toLowerCase();
			}
		}
		return null;
	}
}
