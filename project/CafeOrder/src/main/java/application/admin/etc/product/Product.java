package application.admin.etc.product;

import java.util.LinkedList;

import application.admin.etc.option.Option;
import application.admin.main.AdminMainPageController;
import javafx.scene.image.Image;

public class Product {
	private int id;			// id.
	private String name;	// 상품 명.
	private int price;		// 상품 가격.
	private int stockCount;	// 상품 재고 개수.
	private boolean isStockOut;	// 품절 여부. 
	private int categoryId;	// 카테고리 종류(id).
	
	private Image image;	// 상품 이미지.
	
	private LinkedList<Integer> linkedOptionList;	// 상품에 연결된 옵션id 리스트.
	
	public Product() {
		linkedOptionList = new LinkedList<Integer>();
	}
	
	// getter/setter.
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public int getPrice() { return price; }
	public void setPrice(int price) { this.price = price; }
	public int getStockCount() { return stockCount; }
	public void setStockCount(int stockCount) { this.stockCount = stockCount; } 
 	public boolean getIsStockOut() { return isStockOut; }
	public void setIsStockOut(boolean isStockOut) { this.isStockOut = isStockOut; }
	public int getCategoryId() { return categoryId; }
	public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
	
	public Image getImage() { return image; }
	public void setImage(Image image) { this.image = image; }
	
	public LinkedList<Integer> getLinkedOptionList() { return linkedOptionList; }
	public void setLinkedOptionList(LinkedList<Integer> optionList) { this.linkedOptionList = optionList; }
	
	
	// 하당 상품에 연결되지 않은 옵션 리스트 반환 메소드.
	public LinkedList<Integer> getUnlinkedOptionList() {
		LinkedList<Integer> unlinkedOptionList = new LinkedList<Integer>();
		
		unlinkedOptionList.addAll(AdminMainPageController.getMainPageController().getOptionManager().getOptionIdList());
		
		unlinkedOptionList.removeAll(getLinkedOptionList());
		
		return unlinkedOptionList;
	}
	
	
	// 옵션 관련.
	public void addLinkedOption(Option option) {
		// 옵션 추가(option).
		if (linkedOptionList != null) {
			if (findLinkedOption(option.getId()) == -1) {
				linkedOptionList.add(option.getId());			
			}
		}
	}
	public void deleteLinkedOption(Option option) {
		// 옵션 삭제(option).
		if (linkedOptionList != null) {
			if (findLinkedOption(option.getId()) != -1) {
				linkedOptionList.remove(option.getId());			
			}			
		}
	}
	public void addLinkedOption(int optionId) {
		// 옵션 추가(id).
		if (linkedOptionList != null) {
			if (findLinkedOption(optionId) == -1) {
				linkedOptionList.add(optionId);
			}
		}
	}
	public void addLinkedOptions(LinkedList<Integer> optionIds) {
		for (Integer opId : optionIds) {
			if (findLinkedOption(opId) == -1) {
				linkedOptionList.add(opId);
			}
		}
	}
	public void deleteLinkedOption(int optionId) {
		// 옵션 삭제(id).
		if (linkedOptionList != null) {
			if (findLinkedOption(optionId) != -1) {
				linkedOptionList.remove(optionId);
			}			
		}
	}
	public void deleteLinkedOptions(LinkedList<Integer> optionIds) {
		for (Integer opId : optionIds) {
			if (findLinkedOption(opId) != -1) {
				linkedOptionList.remove(opId);
			}
		}
	}
	
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
	
	
	// find.(id)
	public int findLinkedOption(int optionId) {
		if (linkedOptionList != null) {
			for (Integer opId : linkedOptionList) {
				if (opId.intValue() == optionId) {
					return opId.intValue();
				}
			}			
		}
		return -1;
	}
	// finds. (id)
	public LinkedList<Integer> findOptionsByIds(int ... optionIds) {
		LinkedList<Integer> findOptionList = new LinkedList<Integer>();
		if (findOptionList != null) {
			for (int optionId : optionIds) {
				for (Integer opId : findOptionList) {
					if (opId.intValue() == optionId) {
						findOptionList.add(opId);
					}
				}
			}		
		}
		return findOptionList;
	}
	
	
	
	
	
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[id : ").append(id);
		sb.append(", name : ").append(name);
		sb.append(", price : ").append(price);
		sb.append(", stockCount : ").append(stockCount);
		sb.append(", isStockOut").append(isStockOut);
		sb.append(", category").append(categoryId).append("]");
		
		return sb.toString();
	}
}