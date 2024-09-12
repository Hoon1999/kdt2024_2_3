package cafeorder;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import org.json.*;


public class ManagementClient {
	//필드
	Socket socket;
	InputStream dis;
	OutputStream dos;
	
	//서버 연결
	public void connect() throws IOException{
		socket = new Socket("localhost",50001);
		dis = socket.getInputStream();
		dos = socket.getOutputStream();	
		System.out.println("[pos]가 서버에 연결되었습니다.");
	}
	//id 보내기
	public void send(String json) throws IOException{
		byte[] buffer = json.getBytes("UTF-8");
		dos.write(buffer);
		dos.flush();
	}
	//연결 종료
	public void unconnect() throws IOException{
		socket.close();
	}
	//메소드: JSON 받기
	public void receive() {
		Thread thread = new Thread(() -> {
			try {
				while(true) {
					//변경
					dis = socket.getInputStream();
					byte[] buffer = new byte[512];
					int length = dis.read(buffer);
					while(length== -1) throw new IOException();
					//변경
					//root에서 원하는 걸 받아오기
					String json = new String(buffer, 0, length, "UTF-8");
					JSONObject root = new JSONObject(json);
					String id = root.getString("kioskid");
					String ordernum = root.getString("ordernum");
					JSONObject order = root.getJSONObject("order");
					Boolean takeout = root.getBoolean("takeout");
					System.out.println("--------<"+id+"> 키오스크에서 주문이 왔습니다!---------");
					System.out.println("주문번호: " +ordernum);
					System.out.println("주문: " +order);
					System.out.println("포장여부: "+takeout);
					
				}
			} catch(Exception e1) {
				System.out.println("에러: "+ e1);
				System.exit(0);
			}
		});
		thread.start();
	}

	public static void main(String[] args) {
		try {	
			ManagementClient manage = new ManagementClient();
			manage.connect();
			
			Scanner scanner = new Scanner(System.in);
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("command", "enter");
			jsonObject.put("id", "manager");
			String json = jsonObject.toString();
			manage.send(json);
			
			manage.receive();
			
			System.out.println("--------------------------------------------------");
			System.out.println("주문을 받습니다.");
			System.out.println("pos를 종료하려면 q 를 입력하고 Enter");
			System.out.println("--------------------------------------------------");
			
			while(true) {
				String message = scanner.nextLine();
				if(message.toLowerCase().equals("q")) break; //종료버튼등 다양하게 커스텀 해주시면 됩니다.
			}
			
			manage.unconnect();
		}catch(IOException e) {
			System.out.println("[pos] 서버 연결 안됨");
		}
	}

}
