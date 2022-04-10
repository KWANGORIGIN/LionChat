package com.psu.Lionchat.service;

public class PythonResolver {
	public static String getHostName() {
		String ip = System.getenv("PYTHON_SERVER_IP");

		if (ip == null || ip == "") {
			ip = "localhost";
		}

		return ip;
	}
}
