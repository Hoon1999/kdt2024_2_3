package application.admin.etc.table;

import javafx.scene.control.TableColumn;

public class OptionDisplayTableData {
	public TableColumn[] getColumns() {

		final TableColumn<OptionTableData, Integer> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(item -> item.getValue().idProperty().asObject());
		idColumn.setPrefWidth(25);
		
		final TableColumn<OptionTableData, String> nameColumn = new TableColumn<>("옵션명");
		nameColumn.setCellValueFactory(item -> item.getValue().nameProperty());
		nameColumn.setPrefWidth(75);
		
		final TableColumn<OptionTableData, Integer> priceColumn = new TableColumn<>("가격");
		priceColumn.setCellValueFactory(item -> item.getValue().priceProperty().asObject());
		priceColumn.setPrefWidth(50);
		
		final TableColumn<OptionTableData, Integer> stockColumn = new TableColumn<>("재고");
		stockColumn.setCellValueFactory(item -> item.getValue().stockProperty().asObject());
		stockColumn.setPrefWidth(50);
		
		return new TableColumn[] {idColumn, nameColumn, priceColumn, stockColumn};
	}
}
