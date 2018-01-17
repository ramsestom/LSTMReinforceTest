package trading;

public class Order {

	String ID;
	String currency;
	double amount;
	double unit_price;
	boolean buy_order = false;
	
	
	public String getID() {
		return ID;
	}
	
	public void setID(String iD) {
		ID = iD;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public double getUnit_price() {
		return unit_price;
	}
	
	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}
	
	public boolean isBuy_order() {
		return buy_order;
	}
	
	public void setBuy_order(boolean buy_order) {
		this.buy_order = buy_order;
	}
	
}
