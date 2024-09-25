package application.admin.etc.option;

import java.util.Iterator;
import java.util.LinkedList;

import application.admin.etc.product.Product;

public class OptionManager {

	private LinkedList<Option> optionList;			// 옵션 리스트.
	private  LinkedList<SubOption> subOptionList;	// 서브옵션 리스트.
	
	////////////////////////////////////////////////////
	/// TODO 옵션 리스트 고치기.
	
	
	// 생성자.
	public OptionManager() {
		initOptionList();
		initSubOptionList();
	}
	
	// getter/setter.
	public LinkedList<Option> getOptionList() { return optionList; }
	public void setOptionList(LinkedList<Option> optionList) { this.optionList = optionList; }
	public LinkedList<SubOption> getSubOptionList() { return subOptionList; }
	public void setSubOptionList(LinkedList<SubOption> subOptionList) { this.subOptionList = subOptionList; }
	
	// 옵션 이름 리스트 반환 메소드.
	public LinkedList<String> getOptionNameList() {
		LinkedList<String> nameList = new LinkedList<String>();
		for (Option option : optionList) {
			nameList.add(option.getName());
		}
		return nameList;
	}
	// 옵션 id 리스트 반환 메소드.
	public LinkedList<Integer> getOptionIdList() {
		LinkedList<Integer> idList = new LinkedList<Integer>();
		for (Option option : optionList) {
			idList.add(option.getId());
		}
		return idList;
	}
	
	// 옵션 관련.
	public void addOption(Option option) {
		// 옵션 추가.
		if (!optionList.contains(option)) {
			optionList.add(option);			
		}
	}
	public void deleteOption(Option option) {
		// 옵션 삭제.
		if (optionList.contains(option)) {
			optionList.remove(option);
		}
	}
	public void updateOption(Option newOption) {
		// 옵션 수정.
		Option oldOption = findOptionById(newOption.getId());
		
		if (oldOption != null) {
			oldOption.setName(newOption.getName());
		}
	}
	// 옵션 find. (id) 로 찾기.
	public Option findOptionById(int optionId) {
		// 옵션 id 로 옵션 찾기.
		for (Option option : optionList) {
			if (option.getId() == optionId) {
				return option;
			} 
		}
		return null;
	}
	public String findOptionNameById(int id) {
		// 옵션 id 로 옵션 이름 얻기.
		for (Option option : optionList) {
			if (option.getId() == id) {
				return option.getName();
			}
		}
		return null;
	}
	// 이름으로 찾기. (이거 때문에, 옵션 이름은 중복 x). //!TODO
	public Option findOptionByName(String name) {
		// 옵션 이름으로 옵션 찾기.
		for (Option option : optionList) {
			if (option.getName().equals(name)) {
				return option;
			}
		}
		return null;
	}
	public int findOptionIdByName(String name) {
		// 옵션 이름으로 옵션 id 얻기.
		for (Option option: optionList) {
			if (option.getName().equals(name)) {
				return option.getId();
			} 
		}
		return -1;
	}
	
	
	
	// 하위옵션 관련.
	public void addSubOption(SubOption subOption) {
		// 하위옵션 추가.
		if (!subOptionList.contains(subOption)) {
			subOptionList.add(subOption);			
		}
	}
	public void deleteSubOption(SubOption subOption) {
		// 하위옵션 삭제.
		if (subOptionList.contains(subOption)) {
			subOptionList.remove(subOption);
		}
	}
	public void updateSubOption(SubOption newSubOption) {
		// 하위옵션 수정/변경.
		SubOption oldSubOption = findSubOptionById(newSubOption.getId());
		
		if (oldSubOption != null) {
			oldSubOption.setName(newSubOption.getName());
			oldSubOption.setPrice(newSubOption.getPrice());
			oldSubOption.setStockCount(newSubOption.getStockCount());
			oldSubOption.setOptionId(newSubOption.getOptionId());
			oldSubOption.setImage(newSubOption.getImage());
		}
		
	}
	// find. 하위옵션(id).
	public SubOption findSubOptionById(int subOptionId) {
		for (SubOption subOption : subOptionList) {
			if (subOption.getId() == subOptionId) {
				return subOption;
			}
		}
		return null;
	}
	// find list. 옵션으로 하위 옵션 list 얻는 메소드.
	public LinkedList<SubOption> findSubOptionsByOption(int optionId) {
		// find list. 하위 옵션 list (옵션 id).
		LinkedList<SubOption> findList = new LinkedList<>();
		for (SubOption subOption : subOptionList) {
			if (subOption.getOptionId() == optionId) {
				findList.add(subOption);
			}
		}
		return findList;
	}
	public LinkedList<SubOption> findSubOptionsByOption(Option option) {
		// find list. 하위 옵션 list (옵션).
		return findSubOptionsByOption(option.getId());
	}
	public LinkedList<SubOption> findSubOptionsByOption(String optionName) {
		// find list. 하위 옵션 list (옵션명).
		return findSubOptionsByOption(findOptionIdByName(optionName));
	}
	
	
	// 하위 옵션 품절 관련.
	public void toStockOutProduct(int id) {
		// id 의 상품을 품절 처리.
		SubOption selectedSubOption = findSubOptionById(id);
		if (selectedSubOption != null) {
			selectedSubOption.toStockOut();
		}
	}
	public void toInStockProduct(int id) {
		// id 의 상품을 품절 취소 처리.
		SubOption selectedSubOption = findSubOptionById(id);
		if (selectedSubOption != null) {
			selectedSubOption.toInStock();
		}
	}
	
	
	
	

	public void initOptionList() {
		optionList = new LinkedList<Option>();
		
		// 임시.
		optionList.add(new Option(1,"Size"));
		optionList.add(new Option(2,"Hot/Ice"));
		optionList.add(new Option(3,"샷추가"));
		optionList.add(new Option(4,"시럽추가"));
		optionList.add(new Option(5,"얼음추가"));
		optionList.add(new Option(6,"토핑추가1"));
		
		for (int i = 1; i <= 1000; i++) {
			optionList.add(new Option(6*i+1,"Size^" + i));
			optionList.add(new Option(6*i+2,"Hot/Ice^" + i));
			optionList.add(new Option(6*i+3,"샷추가^" + i));
			optionList.add(new Option(6*i+4,"시럽추가^" + i));
			optionList.add(new Option(6*i+5,"얼음추가^" + i));
			optionList.add(new Option(6*i+6,"토핑추가1^" + i));			
		}
	}
	public void initSubOptionList() {
		subOptionList = new LinkedList<SubOption>();
		
		// 임시.
		LinkedList<SubOption> addSubOptionList = new LinkedList<SubOption>();
		
		addSubOptionList.add(new SubOption(1, "Large", 500, 10, findOptionIdByName("Size")));
		addSubOptionList.add(new SubOption(2, "Regular", 0, 10, findOptionIdByName("Size")));
		addSubOptionList.add(new SubOption(3, "Small", 0, 10, findOptionIdByName("Size")));

		addSubOptionList.add(new SubOption(4, "Hot", 0, 10, findOptionIdByName("Hot/Ice")));
		addSubOptionList.add(new SubOption(5, "Ice", 0, 10, findOptionIdByName("Hot/Ice")));

		addSubOptionList.add(new SubOption(6, "샷추가O", 300, 10, findOptionIdByName("샷추가")));
		addSubOptionList.add(new SubOption(7, "샷추가X", 0, 10, findOptionIdByName("샷추가")));
		
		addSubOptionList.add(new SubOption(8, "시럽추가O", 300, 10, findOptionIdByName("시럽추가")));
		addSubOptionList.add(new SubOption(9, "시럽추가X", 0, 10, findOptionIdByName("시럽추가")));
		
		addSubOptionList.add(new SubOption(10, "얼음적게", 0, 10, findOptionIdByName("얼음추가")));
		addSubOptionList.add(new SubOption(11, "얼음많이", 0, 10, findOptionIdByName("얼음추가")));
		
		addSubOptionList.add(new SubOption(12, "별사탕", 300, 10, findOptionIdByName("토핑추가1")));
		addSubOptionList.add(new SubOption(13, "초코칩", 300, 10, findOptionIdByName("토핑추가1")));
		addSubOptionList.add(new SubOption(14, "펄추가", 500, 10, findOptionIdByName("토핑추가1")));
		addSubOptionList.add(new SubOption(15, "휘핑크림추가", 500, 10, findOptionIdByName("토핑추가1")));
		
		subOptionList.addAll(addSubOptionList);
	}
}
