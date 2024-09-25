package application.admin.etc.table;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LinkProductTableData {
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	
	
	public LinkProductTableData(SimpleIntegerProperty id, SimpleStringProperty name) {
		this.id = id;
		this.name = name;
	}
	public LinkProductTableData(int id, String name) {
		this.id = new SimpleIntegerProperty();
		this.id.set(id);
		this.name = new SimpleStringProperty();
		this.name.set(name);
	}
	
	
	public SimpleIntegerProperty idProperty() {
		return id;
	}
	public SimpleStringProperty nameProperty() {
		return name;
	}
}
