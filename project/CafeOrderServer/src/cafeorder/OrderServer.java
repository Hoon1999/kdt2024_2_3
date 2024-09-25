package cafeorder;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import org.json.*;


public class OrderServer {
	//필드
	ServerSocket serverSocket;
	ExecutorService threadPool = Executors.newFixedThreadPool(100);
	Map<String, SocketClient> room = Collections.synchronizedMap(new HashMap<>());
	
	//서버시작
	public void start() throws IOException{
		serverSocket = new ServerSocket(50001);
		System.out.println("키오스트-pos 서버 시작.");
		
		Thread thread = new Thread(() -> {
			try {
				while(true) {
					Socket socket = serverSocket.accept();
					SocketClient sc = new SocketClient(this, socket);
				}
			} catch(IOException e) {
				}
		});
		thread.start();
	}
	//서버끝
	public void stop() {
		try {
			serverSocket.close();
			threadPool.shutdown();
			room.values().stream().forEach(sc -> sc.close());
			System.out.println("키오스트-pos 서버 종료.");
		}catch(IOException e1) {}
	}
	
	
	//클라이언트 생성
	public void addSocket(SocketClient socketClient) {
		String key = socketClient.id;
		room.put(key, socketClient);
		if(key.equals("manager")) System.out.println("<id:" + key + "> pos기 입장");
		else System.out.println("<id:" + key + "> 키오스크 입장");
	}
	//클라이언트 삭제
	public void removeSocket(SocketClient socketClient) {
		String key = socketClient.id;
		room.remove(key);
		if(key.equals("manager")) System.out.println("<id:" + key + "> pos기 퇴장");
		else System.out.println("<id:" + key + "> 키오스크 퇴장");
	}
	
	//주문서받고주기()
	public void sendOrdertoManager(SocketClient sender, JSONObject order) {
		JSONObject root = new JSONObject();
		root.put("kioskid", sender.id);
		root.put("order", order);
		String json = root.toString();
		String callback = CDBC.order(order).toString();
		
		Collection<SocketClient> socketClients = room.values();
		for(SocketClient sc : socketClients) {
			if(sc.id.equals("manager")) sc.send(json);
			else sc.send(callback);
		}
	}
	//메뉴 불러오기
	public void callMenu(SocketClient sender) {
		//JSONObject root = new JSONObject();
        JSONObject root = CDBC.getAllProducts();
        String json = root.toString();
        sender.send(json);
	};
	//옵션 불러오기
	public void callOption(SocketClient sender, int product_id) {
		//JSONObject root = new JSONObject();
		//JSONObject root = CDBC.getOptions(product_id);
		//String json = root.toString();
		//sender.send(json);
	}
	//회원서치
	public void searchMem(SocketClient sender, String number) {
		JSONObject root = CDBC.findMemberId(number);
		//JSONObject root = new JSONObject();
		String json = root.toString();
		sender.send(json);
	}
	//회원가입
	public void joinMem(String number) {
		CDBC.joinMember(number);
	}
	
	//카테고리 추가
	public void addCategory(String category) {
		CDBC.addCategory(category);
	}
	public void getOptionTypes(SocketClient sender) {
		JSONObject root = CDBC.getOptionTypes();
		//JSONObject root = new JSONObject();
		String json = root.toString();
		sender.send(json);
	}
	
	//서버 메인 함수
	public static void main(String[] args) {
		try {
			OrderServer orderServer = new OrderServer();
			orderServer.start();
			
			System.out.println("----------------------------------");
			System.out.println("서버를 종료하려면 q 를 입력하고 Enter.");
			System.out.println("----------------------------------");
			
			Scanner scanner = new Scanner(System.in);
			while(true) {
				String key = scanner.nextLine();
				if(key.equals("q")) 	break;
			}
			scanner.close();
			orderServer.stop();
		} catch(IOException e) {
			System.out.println("[서버에러]: " + e.getMessage());
		}
	}
	
}
