package org.example.server.utils;

public class JwtAuth {
	private static final String secret = "DrHosseinZynali";
	public static String jws(String sub) {
		char[] arr = new char[sub.length()];
		for (int i = 0; i < sub.length(); i++)
			arr[i] = (char)(48 + (((int)sub.charAt(i) ^ (int)secret.charAt(i % secret.length())) % 50));
		return new String(arr);
	}
}
