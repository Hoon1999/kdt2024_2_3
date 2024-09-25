package application.admin.etc.option;

import javafx.scene.image.Image;

public class SubOption {
	private int id;			// 하위 옵션 id.
	private String name;	// 하위 옵션 명.
	private int price;		// 하위 옵션 가격.
	private int stockCount;	// 하위 옵션 재고 개수.
	private int optionId;	// 옵션 종류 (id).
	private boolean isStockOut;	// 품절 여부.
	
	private Image image;	// 하위 옵션 이미지.
	
	// 생성자.
	public SubOption(int id, String name, int price, int stockCount, int optionId) {
		this(id, name, price, stockCount, optionId, null);
	}
	public SubOption(int id, String name, int price, int stockCount, int optionId, Image image) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.stockCount = stockCount;
		this.optionId = optionId;
		
		this.image = image;
		
		this.isStockOut = false;
	}
	
	// getter/setter.
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public int getPrice() { return price; }
	public void setPrice(int price) { this.price = price; }
	public boolean getIsStockOut() { return isStockOut; }
	public void setIsStockOut(boolean isStockOut) { this.isStockOut = isStockOut; }
	public int getStockCount() { return stockCount; }
	public void setStockCount(int stockCount) { this.stockCount = stockCount; }
	public int getOptionId() { return optionId; }
	public void setOptionId(int optionId) { this.optionId = optionId; }
	
	public Image getImage() { return image; }
	public void setImage(Image image) { this.image = image; }
	
	
	// 재고 관련.
	public boolean addStock(int quantity) {
		stockCount += quantity;
		return true;
	}
	public boolean reduceStock(int quantity) {
		if (stockCount - quantity >= 0) {
			stockCount -= quantity;
			return true;
		}
		return false;
	}
	
	// 품절 관련.
	public void toStockOut() {
		// 품절 처리. (->재고 없음).
		setIsStockOut(true);
	}
	public void toInStock() {
		// 품절 취소 처리. (->재고 있음).
		setIsStockOut(false);
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[name : ").append(name);
		sb.append(", price : ").append(price).append("]");
		
		return sb.toString();
	}
}