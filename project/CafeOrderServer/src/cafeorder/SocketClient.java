package cafeorder;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import org.json.*;


public class SocketClient {
	//필드
	OrderServer orderServer;
	Socket socket;
	InputStream dis;
	OutputStream dos;
	//공통 필드	
	String id;
		
	//생성자
	public SocketClient(OrderServer orderServer, Socket socket) {
		try {
			this.orderServer = orderServer;
			this.socket = socket;
			this.dis = socket.getInputStream();
			this.dos = socket.getOutputStream();
			receive();
		} catch(IOException e) {
		}
	}

	//메소드: JSON 보내기
	public void send(String json) {
		try {
			byte[] buffer = json.getBytes("UTF-8");
			dos.write(buffer);
			dos.flush();
		} catch(IOException e) {
		}
	}
	//메소드: JSON 받기
	public void receive() {
		orderServer.threadPool.execute(() -> {
			try {
				while(true) {
					dis =socket.getInputStream();
					byte[] buffer = new byte[512];
					int length = dis.read(buffer);
					while(length== -1) throw new IOException();
					//변경
					String receiveJson = new String(buffer, 0, length, "UTF-8");		
					JSONObject jsonObject = new JSONObject(receiveJson);
					String command = jsonObject.getString("command");
					
					switch(command) {
						case "enter": //기기가 접속할때 id 등을 받아 소켓을 생성하는 커멘드
							this.id = jsonObject.getString("id");
							orderServer.addSocket(this);
							break;
						case "order": // 주문을 받고 전해주는 커멘드
							JSONObject order = new JSONObject();
							order.put("ordernum", jsonObject.getString("ordernum"));
							order.put("order", jsonObject.getJSONObject("order"));
							order.put("takeout", jsonObject.getBoolean("takeout"));
							orderServer.sendOrdertoManager(this,order);
							break;
					}
				}
			}catch(IOException e) {
				orderServer.removeSocket(this);
			}
		});
	}
	
	//메소드: 연결 종료
	public void close() {
		try { 
			socket.close();
		} catch(Exception e) {}
	}
	
}
