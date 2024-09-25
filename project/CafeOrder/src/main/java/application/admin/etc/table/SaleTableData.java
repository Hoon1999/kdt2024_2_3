package application.admin.etc.table;

import java.time.ZonedDateTime;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class SaleTableData {
	private SimpleIntegerProperty id;
	private SimpleStringProperty memberId;
	private SimpleIntegerProperty totalPaymentAmount;
	private SimpleObjectProperty<ZonedDateTime> paymentTime;
	
	
	public SaleTableData(SimpleIntegerProperty id, SimpleStringProperty memberId, SimpleIntegerProperty totalPaymentAmount, SimpleObjectProperty<ZonedDateTime> paymentTime) {
		this.id = id;
		this.memberId = memberId;
		this.totalPaymentAmount = totalPaymentAmount;
		this.paymentTime = paymentTime;
	}
	public SaleTableData(int id, String memberId, int totalPaymentAmount, ZonedDateTime paymentTime) {
		this.id = new SimpleIntegerProperty();
		this.id.set(id);
		this.memberId = new SimpleStringProperty();
		this.memberId.set(memberId);
		this.totalPaymentAmount = new SimpleIntegerProperty();
		this.totalPaymentAmount.set(totalPaymentAmount);
		this.paymentTime = new SimpleObjectProperty<ZonedDateTime>();
		this.paymentTime.set(paymentTime);
	}
	
	
	public SimpleIntegerProperty idProperty() {
		return id;
	}
	public SimpleStringProperty memberIdProperty() {
		return memberId;
	}
	public SimpleIntegerProperty totalPaymentAmountProperty() {
		return totalPaymentAmount;
	}
	public SimpleObjectProperty<ZonedDateTime> paymentTimeProperty() {
		return paymentTime;
	}
	
}
