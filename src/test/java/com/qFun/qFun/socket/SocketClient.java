package com.qFun.qFun.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketClient {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		String host = "127.0.0.1";
		int port = 55533;
		Socket socket = new Socket(host, port);
		
		OutputStream outputStream = socket.getOutputStream();
		String message  = "nhlg";
		Scanner scanner = new Scanner(System.in);
		
		//outputStream.write(message.getBytes("utf-8"));
		String s = scanner.next();
		outputStream.write(s.getBytes("utf-8"));
		outputStream.close();
		socket.close();
	}

}
