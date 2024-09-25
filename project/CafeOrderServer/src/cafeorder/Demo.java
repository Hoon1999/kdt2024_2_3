//package cafeorder;
//
//import java.io.IOException;
//import java.util.Scanner;
//
//import org.json.JSONObject;
//
//public class Demo {
//
//	public static void main(String[] args) {
//		 try {
//	            cafeorder.OrderClient orderClient = new cafeorder.OrderClient();
//	            orderClient.connect();
//
//	            Scanner scanner = new Scanner(System.in);
//
//	            // 세션 초기화
//	            orderClient.makenewid();
//	            System.out.println("세션이 시작되었습니다. 종료하려면 'q'를 입력하세요.");
//
//	            while (true) {
//	                System.out.println("명령어를 입력하세요 (set, add, delete, send, quit):");
//	                String command = scanner.nextLine().trim();
//
//	                if (command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("q")) {
//	                    break; // 종료 명령어 처리
//	                }
//
//	                // 각 명령어에 따라 처리
//	                if (command.equalsIgnoreCase("set")) {
//	                    // 예시로 주문 설정
//	                    orderClient.setOrdernum("321");
//	                    orderClient.setTakeout(true);
//	                    orderClient.setMenu("커피");
//	                    orderClient.setOption("시럽많이");
//	                    orderClient.setOption("얼음적게");
//	                    orderClient.setPcs("2");
//	                    orderClient.addOrder("1");
//	                    orderClient.setMenu("라떼");
//	                    orderClient.setOption("시럽적게");
//	                    orderClient.setOption("우유많이");
//	                    orderClient.setPcs("2");
//	                    orderClient.addOrder("2");
//	                } else if (command.equalsIgnoreCase("add")) {
//	                    // 예시로 주문 추가
//	                    orderClient.setMenu("음료");
//	                    orderClient.setOption("옵션");
//	                    orderClient.setPcs("1");
//	                    orderClient.addOrder("3");
//	                } else if (command.equalsIgnoreCase("delete")) {
//	                    // 예시로 주문 삭제
//	                    orderClient.deleteOrder("2");
//	                } else if (command.equalsIgnoreCase("send")) {
//	                    // 예시로 주문 전송
//	                    orderClient.sendOrder();
//	                } else {
//	                    System.out.println("알 수 없는 명령어입니다.");
//	                }
//	            }
//
//	            // 연결 종료
//	            orderClient.unconnect();
//	            System.out.println("세션이 종료되었습니다.");
//
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	}
//}
