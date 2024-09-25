package application.admin.etc.table;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LinkOptionTableData {
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private SimpleBooleanProperty select;
	
	
	public LinkOptionTableData(SimpleIntegerProperty id, SimpleStringProperty name) {
		this.id = id;
		this.name = name;
		this.select = new SimpleBooleanProperty();
	}
	public LinkOptionTableData(int id, String name) {
		this.id = new SimpleIntegerProperty();
		this.id.set(id);
		this.name = new SimpleStringProperty();
		this.name.set(name);
		this.select = new SimpleBooleanProperty();
	}
	
	
	public SimpleIntegerProperty idProperty() {
		return id;
	}
	public SimpleStringProperty nameProperty() {
		return name;
	}
	public SimpleBooleanProperty isSelectedProperty() {
		return select;
	}
	
}
