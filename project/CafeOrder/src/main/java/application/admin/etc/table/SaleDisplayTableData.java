package application.admin.etc.table;

import java.time.format.DateTimeFormatter;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.TableColumn;

public class SaleDisplayTableData {
	public TableColumn[] getColumns() {
		
		final TableColumn<SaleTableData, Integer> idColumn = new TableColumn<>("주문ID");
		idColumn.setCellValueFactory(item -> item.getValue().idProperty().asObject());
		idColumn.setPrefWidth(50);
		
		final TableColumn<SaleTableData, String> nameColumn = new TableColumn<>("결제 고객");
		nameColumn.setCellValueFactory(item -> item.getValue().memberIdProperty());
		nameColumn.setPrefWidth(100);
		
		final TableColumn<SaleTableData, Integer> totalPaymentAmountColumn = new TableColumn<>("결제 금액");
		totalPaymentAmountColumn.setCellValueFactory(item -> item.getValue().totalPaymentAmountProperty().asObject());
		totalPaymentAmountColumn.setPrefWidth(100);
		
		final TableColumn<SaleTableData, String> paymentTimeColumn = new TableColumn<>("결제 시간");
		paymentTimeColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(
				//ZonedDateTime Formatting 하여 전시 (예: 2020-02-01 13:00:11.22)
		        item.getValue().paymentTimeProperty().get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))));
		paymentTimeColumn.setPrefWidth(100);
		
		return new TableColumn[] {idColumn, nameColumn, totalPaymentAmountColumn, paymentTimeColumn};
	}
}
