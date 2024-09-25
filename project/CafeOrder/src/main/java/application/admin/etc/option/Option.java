package application.admin.etc.option;

public class Option {
	private int id;					// id.
	private String name;			// 옵션 명.
	private int displayPriority;	// 보기 순서. // 아직 안쓰임 ... .
	
	// 생성자.
	public Option(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	// getter/setter.
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public int getDisplayPriority() { return displayPriority; }
	public void setDisplayPriority(int displayPriority) { this.displayPriority = displayPriority; }
	
	
	
	@Override
	public boolean equals(Object obj) {
		// id 가 같거나, 이름이 같으면, 같은 옵션으로 친다.
		if (this.id == ((Option)obj).getId()) {
			return true;
		}
		if (this.name == ((Option)obj).getName()) {
			return true;
		}
		
		return false;
	}
}
