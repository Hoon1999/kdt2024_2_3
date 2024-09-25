package application.admin.etc.table;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OptionTableData {
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private SimpleIntegerProperty price;
	private SimpleIntegerProperty stock;
	
	
	public OptionTableData(SimpleIntegerProperty id, SimpleStringProperty name, SimpleIntegerProperty price, SimpleIntegerProperty stock) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
	}
	public OptionTableData(int id, String name, int price, int stock) {
		this.id = new SimpleIntegerProperty();
		this.id.set(id);
		this.name = new SimpleStringProperty();
		this.name.set(name);
		this.price = new SimpleIntegerProperty();
		this.price.set(price);
		this.stock = new SimpleIntegerProperty();
		this.stock.set(stock);
	}
	
	
	public SimpleIntegerProperty idProperty() {
		return id;
	}
	public SimpleStringProperty nameProperty() {
		return name;
	}
	public SimpleIntegerProperty priceProperty() {
		return price;
	}
	public SimpleIntegerProperty stockProperty() {
		return stock;
	}
}
