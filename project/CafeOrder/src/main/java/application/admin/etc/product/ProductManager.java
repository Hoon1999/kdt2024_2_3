package application.admin.etc.product;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.random.RandomGenerator;

import application.admin.main.AdminMainPageController;

public class ProductManager {

	private LinkedList<Product> productList;			// 상품 리스트.
	private LinkedList<ProductCategory> categoryList;	// 상품 카테고리 리스트.
	
	// 생성자.
	public ProductManager() {
		initCategoryList();
		initProductList();
	}
	
	// getter/setter.
	public LinkedList<ProductCategory> getCategoryList() { return categoryList; }
	public LinkedList<Product> getProductList() { return productList; }
	public void setCategoryList(LinkedList<ProductCategory> categoryList) { this.categoryList = categoryList; }
	public void setProductList(LinkedList<Product> productList) { this.productList = productList; }
	
	// 카테고리 이름 리스트 반환 메소드.
	public LinkedList<String> getCategoryNameList() {
		LinkedList<String> nameList = new LinkedList<String>();
		for (ProductCategory category : categoryList) {
			nameList.add(category.getName());
		}
		return nameList;
	}
	// 상품 이름 리스트 반환 메소드.
	public LinkedList<String> getProductNameList() {
		LinkedList<String> nameList = new LinkedList<String>();
		for (Product product : productList) {
			nameList.add(product.getName());
		}
		return nameList;
	}

	
	// 상품 관련.
	public void addProduct(Product product) {
		// 상품 추가.
		if (!productList.contains(product)) {
			productList.add(product);
		}
	}
	public void deleteProduct(Product product) {
		// 상품 삭제.
		if (productList.contains(product)) {
			productList.remove(product);			
		}
	}
	public void updateProduct(Product newProduct) {
		// 상품 수정/변경.
		Product oldProduct = findProductById(newProduct.getId());
		
		if (oldProduct != null) {
			oldProduct.setName(newProduct.getName());
			oldProduct.setPrice(newProduct.getPrice());
			oldProduct.setStockCount(newProduct.getStockCount());
			oldProduct.setCategoryId(newProduct.getCategoryId());
			oldProduct.setImage(newProduct.getImage());
		}
		
	}
	
	// find. 상품(id).
	public Product findProductById(int productId) {
		for (Product product : productList) {
			if (product.getId() == productId) {
				return product;
			} 
		}
		return null;
	}
	// find list. 카테고리로 상품 list 얻는 메소드.
	public LinkedList<Product> findProductsByCategory(int categoryId) {
		// find list. 상품 list (카테고리 id).
		LinkedList<Product> findList = new LinkedList<>();
		for (Product product : productList) {
			if (product.getCategoryId() == categoryId) {
				findList.add(product);
			}
		}
		return findList;
	}
	public LinkedList<Product> findProductsByCategory(ProductCategory category) {
		// find list. 상품 list (카테고리).
		return findProductsByCategory(category.getId());
	}
	public LinkedList<Product> findProductsByCategory(String categoryName) {
		// find list. 상품 list (카테고리명).
		return findProductsByCategory(findCategoryIdByName(categoryName));
	}
	
	
	// 상품 품절 관련.
	public void toStockOutProduct(int id) {
		// id 의 상품을 품절 처리.
		Product selectedProduct = findProductById(id);
		if (selectedProduct != null) {
			selectedProduct.toStockOut();
		}
	}
	public void toInStockProduct(int id) {
		// id 의 상품을 품절 취소 처리.
		Product selectedProduct = findProductById(id);
		if (selectedProduct != null) {
			selectedProduct.toInStock();
		}
	}
	
	
	// 카테고리 관련.
	public int findCategoryIdByName(String name) {
		// 이름으로 id 얻기.
		for (ProductCategory category : categoryList) {
			if (category.getName().equals(name)) {
				return category.id;
			} 
		}
		return -1;
	}
	public String findCategoryNameById(int id) {
		// id 로 이름 얻기.
		for (ProductCategory category : categoryList) {
			if (category.getId() == id) {
				return category.name;
			}
		}
		return null;
	}
	public ProductCategory findCategoryById(int id) {
		// id 로 카테고리 얻기.
		for (ProductCategory category : categoryList) {
			if (category.getId() == id) {
				return category;
			}
		}
		return null;
	}
	public ProductCategory findCategoryByName(String name) {
		// 이름으로 카테고리 얻기.
		for (ProductCategory category : categoryList) {
			if (category.getName().equals(name)) {
				return category;
			}
		}
		return null;
	}
	
	
	public void initProductList() {
		productList = new LinkedList<Product>();
		
		//// TODO 상품 리스트 초기화. // 임시.
		Product a = new Product();
		a.setId(1);
		a.setName("아메리카노");
		a.setPrice(3000);
		a.setStockCount(10);
		a.setCategoryId(findCategoryIdByName("커피"));
		
		LinkedList<Integer> tmpOptionList1 = new LinkedList<Integer>();
		tmpOptionList1.add(AdminMainPageController.getMainPageController().getOptionManager().findOptionIdByName("Size"));
		tmpOptionList1.add(AdminMainPageController.getMainPageController().getOptionManager().findOptionIdByName("Hot/Ice"));
		tmpOptionList1.add(AdminMainPageController.getMainPageController().getOptionManager().findOptionIdByName("토핑추가1"));
		tmpOptionList1.add(AdminMainPageController.getMainPageController().getOptionManager().findOptionIdByName("얼음추가"));
		a.setLinkedOptionList(tmpOptionList1);
		
		productList.add(a);
		
		Product b = new Product();
		b.setId(2);
		b.setName("카페라떼");
		b.setPrice(3500);
		b.setStockCount(10);
		b.setCategoryId(findCategoryIdByName("커피"));
		
		LinkedList<Integer> tmpOptionList2 = new LinkedList<Integer>();
		tmpOptionList2.add(AdminMainPageController.getMainPageController().getOptionManager().findOptionIdByName("Size"));
		tmpOptionList2.add(AdminMainPageController.getMainPageController().getOptionManager().findOptionIdByName("Hot/Ice"));
		tmpOptionList1.add(AdminMainPageController.getMainPageController().getOptionManager().findOptionIdByName("없는거"));
		tmpOptionList1.add(AdminMainPageController.getMainPageController().getOptionManager().findOptionIdByName("없는거"));
		b.setLinkedOptionList(tmpOptionList2);

		productList.add(b);
		
		Product c = new Product();
		c.setId(3);
		c.setName("아이스티");
		c.setPrice(3500);
		c.setStockCount(10);
		c.setCategoryId(findCategoryIdByName("논커피"));
		
		
		LinkedList<Integer> tmpOptionList3 = new LinkedList<Integer>();
		tmpOptionList3.add(AdminMainPageController.getMainPageController().getOptionManager().findOptionIdByName("Size"));
		tmpOptionList3.add(AdminMainPageController.getMainPageController().getOptionManager().findOptionIdByName("Hot/Ice"));
		tmpOptionList3.add(AdminMainPageController.getMainPageController().getOptionManager().findOptionIdByName("샷추가"));
		tmpOptionList3.add(AdminMainPageController.getMainPageController().getOptionManager().findOptionIdByName("시럽추가"));
		c.setLinkedOptionList(tmpOptionList3);
		
		productList.add(c);
		
	}
	public void initCategoryList() {
		categoryList = new LinkedList<ProductCategory>();
		
		categoryList.add(new ProductCategory(1,"커피"));
		categoryList.add(new ProductCategory(2,"논커피"));
		categoryList.add(new ProductCategory(3,"베이커리"));
		categoryList.add(new ProductCategory(4,"MD"));
	}
}
