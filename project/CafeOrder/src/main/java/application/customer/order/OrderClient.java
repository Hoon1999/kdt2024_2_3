package Server;

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
	}
	public void sendOrder() throws IOException{
		JSONObject order = new JSONObject();
		order.put("command", "order");
		order.put("ordernum", this.ordernum);
		order.put("order", this.orderrecipe);
		order.put("takeout", this.takeout);
		String json = order.toString();
		
		send(json);
	}
	
	public static OrderClient getInstance() {
		if(instance == null) {
			instance = new OrderClient();
		}
		return instance;
	}
	
	public static void main(String[] args) {
		//여기서부터
		try {
			OrderClient orderClient = new OrderClient();
			orderClient.connect();
			Scanner scanner = new Scanner(System.in);
			
			//기기 아이디 받기
			orderClient.makenewid();
			//여기까지 메인 함수나 실행 함수에 넣어주세요
			//외의 set 함수로 각각의 메뉴를 받은 뒤 makeOrder() 함수로 주문을 보냅니다.
			
			//예시
			orderClient.setOrdernum("321"); //주문번호를 정합니다
			//포장여부를 정합니다.
			orderClient.setTakeout(true);
			//메뉴를 고릅니다.
			orderClient.setMenu("커피");
			orderClient.setOption("시럽많이");
			orderClient.setOption("얼음적게");
			orderClient.setPcs("2");
			orderClient.addOrder("1");
			//커피 시럽많이 얼음적게 로 2개인 1번주문에 추가로
			orderClient.setMenu("라떼");
			orderClient.setOption("시럽적게");
			orderClient.setOption("우유많이");
			orderClient.setPcs("2");
			orderClient.addOrder("2");
			//커피 시럽많이 얼음적게 로 2개인 2번주문을 동시에 시킵니다
			//하지만 라떼를 갑자기 취소하게 되었으므로
			orderClient.deleteOrder("2");
			//라떼 주문인 2번 주문을 삭제합니다.
			
			//그리고 결제버튼을 누르면
			orderClient.sendOrder();
			//저장된 주문 정보가 전송됩니다.
			
			//무언가 세션을 계속 유지 시켜줘야할 필요가 있습니다
			while(true) {
				String message = scanner.nextLine();
				if(message.toLowerCase().equals("q")) break; //종료버튼등 다양하게 커스텀 해주시면 됩니다.
			}
			
		}catch(IOException e) {
			
		}
		//여기까지 make 함수로 넣어주세요
	}
}

