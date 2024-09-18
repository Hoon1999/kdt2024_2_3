package application.customer.menu;

import java.util.List;

public class Cart {
    public int id; // 상품 번호
    public String name ; // 상품 이름
    public int count; // 상품 개수
    public int price; // 총 가격. 상품 가격(+ 옵션가격) * 상품 개수

    public List<Cart> options; // 상품에 추가한 옵션(샷추가 등).
}
