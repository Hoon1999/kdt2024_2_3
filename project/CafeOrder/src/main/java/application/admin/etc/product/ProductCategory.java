package application.admin.etc.product;

public class ProductCategory {
	int id;			// id.
	String name;	// 이름.
	
	// 생성자.
	public ProductCategory(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	// getter/setter.
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
}
