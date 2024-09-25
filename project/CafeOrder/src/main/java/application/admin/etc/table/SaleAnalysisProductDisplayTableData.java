package application.admin.etc.table;

import java.time.format.DateTimeFormatter;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.TableColumn;

public class SaleAnalysisProductDisplayTableData {
	public TableColumn[] getColumns() {
		
		final TableColumn<SaleAnalysisProductTableData, Integer> rankNumColumn = new TableColumn<>("순위");
		rankNumColumn.setCellValueFactory(item -> item.getValue().rankNumProperty().asObject());
		rankNumColumn.setPrefWidth(50);
		
		final TableColumn<SaleAnalysisProductTableData, String> productNameColumn = new TableColumn<>("상품명");
		productNameColumn.setCellValueFactory(item -> item.getValue().productNameProperty());
		productNameColumn.setPrefWidth(100);
		
		final TableColumn<SaleAnalysisProductTableData, String> categoryNameColumn = new TableColumn<>("카테고리명");
		categoryNameColumn.setCellValueFactory(item -> item.getValue().categoryNameProperty());
		categoryNameColumn.setPrefWidth(100);
		
		final TableColumn<SaleAnalysisProductTableData, Integer> totalPaymentCountColumn = new TableColumn<>("결제 건수");
		totalPaymentCountColumn.setCellValueFactory(item -> item.getValue().totalPaymentCountProperty().asObject());
		totalPaymentCountColumn.setPrefWidth(100);
		
		final TableColumn<SaleAnalysisProductTableData, Integer> totalPaymentPriceColumn = new TableColumn<>("결제 금액");
		totalPaymentPriceColumn.setCellValueFactory(item -> item.getValue().totalPaymentPriceProperty().asObject());
		totalPaymentPriceColumn.setPrefWidth(100);
		
		return new TableColumn[] {rankNumColumn, productNameColumn, categoryNameColumn, totalPaymentCountColumn, totalPaymentPriceColumn};
	}
}
