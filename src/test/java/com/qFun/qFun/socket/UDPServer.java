package com.qFun.qFun.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPServer {
	
	public static final String SERVER_IP = "127.0.0.1";
	
	public static final int SERVER_PORT = 10005;
	
	public static final int MAX_BYTES = 1024;
	
	private DatagramSocket serverSocket;
	
	
	
	public void startServer(String serverIp , int port){
		
		try {
			InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
			serverSocket = new DatagramSocket(port, serverAddr);
			
			byte[] bytes = new byte[MAX_BYTES];
			
			DatagramPacket recvPacket = new DatagramPacket(bytes, bytes.length);
			
			while(true){
				try {
					serverSocket.receive(recvPacket);
				} catch (Exception e) {
					// TODO: handle exception
				}
				String recvStr = new String(recvPacket.getData(), 0, recvPacket.getLength());
				
				//拿到対端的ip和端口
				InetAddress clientAddr = recvPacket.getAddress();
				System.out.println("clientAddr---"+clientAddr);
				int clientPort = recvPacket.getPort();
				System.out.println("clientPort---"+clientPort);
				
				//回传数据
				String upperString = recvStr.toUpperCase();
				byte[] sendBuf = upperString.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, clientAddr, clientPort);
				try {
					serverSocket.send(sendPacket);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e2) {
			e2.printStackTrace();
		} finally {
			//记得关闭Socket
			if (null != serverSocket) {
				serverSocket.close();
				serverSocket = null;
			}
		}
	}
	
	public static void main(String[] args) {
		UDPServer server = new UDPServer();
		server.startServer(SERVER_IP, SERVER_PORT);
	}


}
