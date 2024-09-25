package application.admin.etc.table;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class LinkOptionDisplayTableData {
	public TableColumn[] getColumns() {

		final TableColumn<LinkOptionTableData, Integer> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(item -> item.getValue().idProperty().asObject());
		idColumn.setPrefWidth(25);
		
		final TableColumn<LinkOptionTableData, String> nameColumn = new TableColumn<>("옵션명");
		nameColumn.setCellValueFactory(item -> item.getValue().nameProperty());
		nameColumn.setPrefWidth(100);
		
		final TableColumn<LinkOptionTableData, Boolean> selectColumn = new TableColumn<>("선택");
		selectColumn.setCellValueFactory(item -> item.getValue().isSelectedProperty());
		selectColumn.setPrefWidth(30);
		
		selectColumn.setCellFactory(new Callback<TableColumn<LinkOptionTableData, Boolean>, TableCell<LinkOptionTableData, Boolean>>() {
			public TableCell<LinkOptionTableData, Boolean> call(TableColumn<LinkOptionTableData, Boolean> p) {
                return new CheckBoxTableCell<LinkOptionTableData, Boolean>();
            }
        });
		
		return new TableColumn[] {idColumn, nameColumn, selectColumn};
	}
	
	
	 public static class CheckBoxTableCell<S, T> extends TableCell<S, T> {
	        private final CheckBox checkBox;
	        private ObservableValue<T> ov;
	        
	        public CheckBoxTableCell() {
	            this.checkBox = new CheckBox();
	            this.checkBox.setAlignment(Pos.CENTER);
	            
	            setAlignment(Pos.CENTER);
	            setGraphic(checkBox);
	        }
	        
	        @Override public void updateItem(T item, boolean empty) {
	            super.updateItem(item, empty);
	            if (empty) {
	                setText(null);
	                setGraphic(null);
	            } else {
	                setGraphic(checkBox);
	                if (ov instanceof BooleanProperty) {
	                    checkBox.selectedProperty().unbindBidirectional((BooleanProperty) ov);
	                }
	                ov = getTableColumn().getCellObservableValue(getIndex());
	                if (ov instanceof BooleanProperty) {
	                    checkBox.selectedProperty().bindBidirectional((BooleanProperty) ov);
	                }
	            }
	        }
	    }
}
