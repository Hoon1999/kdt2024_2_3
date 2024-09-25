package application.admin.etc.table;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SaleAnalysisProductTableData {
	private SimpleIntegerProperty rankNum;
	private SimpleStringProperty productName;
	private SimpleStringProperty categoryName;
	private SimpleIntegerProperty totalPaymentCount;
	private SimpleIntegerProperty totalPaymentPrice;
	
	public SaleAnalysisProductTableData(SimpleIntegerProperty rankNum, SimpleStringProperty productName, SimpleStringProperty categoryName, SimpleIntegerProperty totalPaymentCount, SimpleIntegerProperty paymentTime) {
		this.rankNum = rankNum;
		this.productName = productName;
		this.categoryName = categoryName;
		this.totalPaymentCount = totalPaymentCount;
		this.totalPaymentPrice = paymentTime;
	}
	public SaleAnalysisProductTableData(int rankNum, String productName, String categoryName, int totalPaymentCount, int totalPaymentPrice) {
		this.rankNum = new SimpleIntegerProperty();
		this.rankNum.set(rankNum);
		this.productName = new SimpleStringProperty();
		this.productName.set(productName);
		this.categoryName = new SimpleStringProperty();
		this.categoryName.set(categoryName);
		this.totalPaymentCount = new SimpleIntegerProperty();
		this.totalPaymentCount.set(totalPaymentCount);
		this.totalPaymentPrice = new SimpleIntegerProperty();
		this.totalPaymentPrice.set(totalPaymentPrice);
	}
	
	
	public SimpleIntegerProperty rankNumProperty() {
		return rankNum;
	}
	public SimpleStringProperty productNameProperty() {
		return productName;
	}
	public SimpleStringProperty categoryNameProperty() {
		return categoryName;
	}
	public SimpleIntegerProperty totalPaymentCountProperty() {
		return totalPaymentCount;
	}
	public SimpleIntegerProperty totalPaymentPriceProperty() {
		return totalPaymentPrice;
	}
	
}
