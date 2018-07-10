package com.qFun.qFun.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
	
	/*public static void main(String[] args) throws Exception{
		
		int port = 55533;
		
		ServerSocket serverSocket = new ServerSocket(port);
		
		System.out.println("server等待连接");
		Socket socket = serverSocket.accept();
		InputStream inputStream = socket.getInputStream();
		byte[] bytes = new byte[0124];
		int len ;
		StringBuilder sb = new StringBuilder();
		while((len = inputStream.read(bytes)) != -1){
			
			sb.append(new String(bytes,0,len,"utf-8"));
		}
		 System.out.println("get message from client: " + sb);
		inputStream.close();
		socket.close();
		serverSocket.close();
		
	}*/
	
	public static void main(String[] args) throws Exception{
		int port = 55533;
		
		ServerSocket serverSocket = new ServerSocket(port);
		
		System.out.println("server等待连接");
		
		ExecutorService threadPool = Executors.newFixedThreadPool(100);
		
		while(true){
			final Socket socket = serverSocket.accept();
			
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					InputStream inputStream;
					try {
						inputStream = socket.getInputStream();
						byte[] bytes = new byte[1024];
						int len ;
						StringBuilder sb = new StringBuilder();
						while((len = inputStream.read(bytes)) != -1){
							sb.append(new String(bytes,0,len,"utf-8"));
						}
						System.out.println("get message from client: " + sb);
						inputStream.close();
						socket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			};
				
			threadPool.submit(runnable);
			}
		}
		

}
