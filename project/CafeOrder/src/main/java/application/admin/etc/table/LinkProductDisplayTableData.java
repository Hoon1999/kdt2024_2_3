package application.admin.etc.table;

import javafx.scene.control.TableColumn;

public class LinkProductDisplayTableData {
	public TableColumn[] getColumns() {

		final TableColumn<LinkProductTableData, Integer> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(item -> item.getValue().idProperty().asObject());
		idColumn.setPrefWidth(25);
		
		final TableColumn<LinkProductTableData, String> nameColumn = new TableColumn<>("상품명");
		nameColumn.setCellValueFactory(item -> item.getValue().nameProperty());
		nameColumn.setPrefWidth(100);
		
		return new TableColumn[] {idColumn, nameColumn};
	}
}
