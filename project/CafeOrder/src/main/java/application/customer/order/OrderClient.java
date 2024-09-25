package application.customer.order;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import org.json.*;


public class OrderClient {
	//필드
	Socket socket;
	InputStream dis;
	OutputStream dos;
	String id;
	//이부분도 똑같이 변할  수 있음
	String ordernum;
	//임의로 제작한 필드, 원하면 서버쪽으로 데이터 이동이 가능함
	boolean takeout;
	String menu;
	JSONArray option = new JSONArray();
	String pcs;
	//최종 오더
	JSONObject orderrecipe = new JSONObject();
	//싱글톤
	private static OrderClient instance;
	
	//서버 연결
	public void connect() throws IOException{
		socket = new Socket("localhost",50001);
		dis = socket.getInputStream();
		dos = socket.getOutputStream();	
		System.out.println("[키오스크]가 서버에 연결되었습니다.");
	}
	//메뉴 보내기
	public void send(String json) throws IOException{
		byte[] buffer = json.getBytes("UTF-8");
		dos.write(buffer);
		dos.flush();
	}
	//연결 종료
	public void unconnect() throws IOException{
		socket.close();
	}
	
	
	//메뉴 및 실행 커스텀 함수
	public void makenewid() throws IOException{ //키오스크 킬시 키오스크 소켓 생성
		Random random = new Random();
		this.id = "kiosk"+random.nextInt(10000);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command", "enter");
		jsonObject.put("id", this.id);
		String json = jsonObject.toString();
		
		send(json);
	}
	
	//주문서 보내기
	public JSONObject sendOrder(JSONObject orderrecipe) throws IOException{
		try {
			JSONObject order = new JSONObject();
			order.put("command", "order");
			order.put("ordernum", this.ordernum);
			order.put("order", orderrecipe);
			order.put("takeout", this.takeout);
			String json = order.toString();

			send(json);

			while(true) {
				dis = socket.getInputStream();
				byte[] buffer = new byte[4096];
				int length = dis.read(buffer);
				while(length== -1) throw new IOException();
				//변경
				//root에서 원하는 걸 받아오기
				String json1 = new String(buffer, 0, length, "UTF-8");
				JSONObject root = new JSONObject(json1);
				return root;
			}

		}catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 싱글톤 패턴 처음 선언할때 해당 함수를 써주세요.
	public static OrderClient getInstance() {
		if(instance == null) {
			instance = new OrderClient();
		}
		return instance;
	}
	
	//메뉴호출
	public JSONObject callmenu() {
		try {
			JSONObject order = new JSONObject();
			order.put("command", "callMenu");
			String call = order.toString();
			send(call);
			
			while(true) {
				dis = socket.getInputStream();
				byte[] buffer = new byte[16384];
				int length = dis.read(buffer);
				while(length== -1) throw new IOException();
				//변경
				//root에서 원하는 걸 받아오기
				String json = new String(buffer, 0, length, "UTF-8");
				JSONObject root = new JSONObject(json);
				return root;
			}

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	//메뉴별 옵션 호출 String으로 메뉴를 받아서 판별.(나중에 값 변경 가능)
	public JSONObject callOption(int product_id) {
		try {
			JSONObject order = new JSONObject();
			order.put("command", "callOptionMenu");
			order.put("product_id", product_id);
			String call = order.toString();
			send(call);
			
			while(true) {
				dis = socket.getInputStream();
				byte[] buffer = new byte[4096];
				int length = dis.read(buffer);
				while(length== -1) throw new IOException();
				//변경
				//root에서 원하는 걸 받아오기
				String json = new String(buffer, 0, length, "UTF-8");
				JSONObject root = new JSONObject(json);
				return root;
			}

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	//회원판별
	public JSONObject findMember(String number) {
		try {
			JSONObject order = new JSONObject();
			order.put("command", "search");
			order.put("number", number);
			String call = order.toString();
			send(call);
			
			while(true) {
				dis = socket.getInputStream();
				byte[] buffer = new byte[4096];
				int length = dis.read(buffer);
				while(length== -1) throw new IOException();
				//변경
				//root에서 원하는 걸 받아오기
				String json = new String(buffer, 0, length, "UTF-8");
				JSONObject root = new JSONObject(json);
				return root;
			}

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}

	public void joinMember(String number) {
		try {
			JSONObject order = new JSONObject();
			order.put("command", "join");
			order.put("number", number);
			String call = order.toString();
			send(call);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public JSONObject getOptionTypes() {
		try {
			JSONObject order = new JSONObject();
			order.put("command", "options");
			order.put("number", 1);
			String call = order.toString();
			send(call);

			while(true) {
				dis = socket.getInputStream();
				byte[] buffer = new byte[4096];
				int length = dis.read(buffer);
				while(length== -1) throw new IOException();
				//변경
				//root에서 원하는 걸 받아오기
				String json = new String(buffer, 0, length, "UTF-8");
				JSONObject root = new JSONObject(json);
				return root;
			}

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public JSONObject getCategories() {
		String sample = "{\"data\": [{\"id\": 1, \"name\": \"커피\"}, {\"id\": 2, \"name\": \"음료\"}, {\"id\": 3, \"name\": \"에이드\"}, {\"id\": 4, \"name\": \"베이커리\"}]}";
		JSONObject data = new JSONObject(sample);
		return data;
	}
	public JSONObject getPopularProduct() {
		String sample = "{\"data\":[{\"id\":1,\"sales_volume\":4},{\"id\":2,\"sales_volume\":4},{\"id\":3,\"sales_volume\":2}]}";
		JSONObject data = new JSONObject(sample);
		return data;
	}
	public JSONObject getSales() {
		String sample = "{\"data\":[{\"phoneNumber\":\"000-0000-0000\",\"id\":2,\"paymentTime\":\"2024-09-20 14:22:42\",\"paymentAmount\":4500},{\"phoneNumber\":\"000-0000-0000\",\"id\":3,\"paymentTime\":\"2024-09-20 14:23:13\",\"paymentAmount\":4500},{\"phoneNumber\":\"000-0000-0000\",\"id\":4,\"paymentTime\":\"2024-09-20 14:23:25\",\"paymentAmount\":4500},{\"phoneNumber\":\"000-0000-0000\",\"id\":5,\"paymentTime\":\"2024-09-20 14:39:33\",\"paymentAmount\":4500},{\"phoneNumber\":\"000-0000-0000\",\"id\":6,\"paymentTime\":\"2024-09-20 14:39:55\",\"paymentAmount\":4500},{\"phoneNumber\":\"000-0000-0000\",\"id\":7,\"paymentTime\":\"2024-09-20 15:09:38\",\"paymentAmount\":4500},{\"phoneNumber\":\"000-0000-0000\",\"id\":8,\"paymentTime\":\"2024-09-20 15:11:41\",\"paymentAmount\":4500},{\"phoneNumber\":\"000-0000-0000\",\"id\":9,\"paymentTime\":\"2024-09-20 15:17:55\",\"paymentAmount\":2000}]}";
		JSONObject data = new JSONObject(sample);
		return data;
	}
	public JSONObject getDailySales() {
		String sample = "{\"data\":[{\"date\":\"2024-09-20\",\"sales\":33500}, {\"date\":\"2024-09-19\",\"sales\":12333}, {\"date\":\"2024-09-21\",\"sales\":55555}]}";
		JSONObject data = new JSONObject(sample);
		return data;
	}
	public JSONObject getMonthlySales() {
		String sample = "{\"data\":[{\"date\":\"2024-09\",\"sales\":33500}]}";
		JSONObject data = new JSONObject(sample);
		return data;
	}
	public static void main(String[] args) {
		//여기서부터
		try {
			OrderClient orderClient = new OrderClient();
			orderClient.connect();
			Scanner scanner = new Scanner(System.in);
			
			//기기 아이디 받기
			orderClient.makenewid();
			
		}catch(IOException e) {
			
		}
		//여기까지 make 함수로 넣어주세요
	}
}

//게터세터존 현재는 사용하지 않습니다.
/*
public void setOrdernum(String ordernum) {
	this.ordernum = ordernum;
	System.out.println(this.ordernum);
}
public void setMenu(String menu) {
	this.menu = menu;
}
public void setOption(String option) {
	this.option.put(option);
}
public void setPcs(String pcs) {
	this.pcs = pcs;
}
public void setTakeout(boolean takeout) {
	this.takeout = takeout;
}

public void addOrder(String key){
	JSONObject order = new JSONObject();
	order.put("menu", this.menu);
	order.put("option", this.option);
	order.put("pcs", this.pcs);
	this.orderrecipe.put(key,order);
}
public void deleteOrder(String key) {
	this.orderrecipe.remove(key);
}
public void deleteAll() {
	this.orderrecipe.clear();
}*/

