package trading;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import commons.Constants;
import model.OrderActionSpace;

public class Wallet {

	private double percent_fees = 0.00025; //0.25% 
	private Order active_order;
		
	public void reset() {
		this.active_order=null;
	}
	
	public double passNewOrder(double lowest_price, double highest_price, boolean is_buy_order) 
	{
		return passOrder(is_buy_order?highest_price:lowest_price, is_buy_order?lowest_price:highest_price, is_buy_order);
	}
	
	private double passOrder(double pass_order_price, double cancel_order_price, boolean is_buy_order) 
	{
		//System.out.println("ORDER: "+(is_buy_order?"BUY":"SELL")+" "+pass_order_price);
		double reward = 0;
		if (active_order!=null) 
		{
			if (active_order.isBuy_order()==is_buy_order) { //ignore simplement cet ordre vu que correspond a ce que l'on a déja fait à un step precedent
				return 0; //ignore the order and return a null reward
			}
			else {
				//System.out.println("ORDER: "+(is_buy_order?"BUY":"SELL")+" "+pass_order_price);
				if (is_buy_order) {
					reward = (active_order.unit_price-pass_order_price)/active_order.unit_price - percent_fees;
				}
				else {
					reward = (pass_order_price-active_order.unit_price)/active_order.unit_price - percent_fees;
				}
				active_order = null;
				//System.err.println(((reward>0)?"WIN":"LOST")+" "+reward);
				return reward; //retourne immédiatement pour ne pas créer order qui vient d'etre executé
			}
		}
		if (active_order==null) 
		{
			//System.out.println("ORDER: "+(is_buy_order?"BUY":"SELL")+" "+pass_order_price);
			Order order = new Order();
			//order.amount = bet_amount;
			order.unit_price = pass_order_price;
			order.buy_order = is_buy_order;
			active_order = order;
		}
		return reward;
	}
	
	public double getCurrentVirtualReward(double sell_price, double buy_price) 
	{
		if (active_order!=null) 
		{
			if (active_order.isBuy_order()) { //virtuel order serait un sell
				return (sell_price-active_order.unit_price)/active_order.unit_price - percent_fees;
			}
			else { //virtuel serait un buy
				return (active_order.unit_price-buy_price)/active_order.unit_price - percent_fees;
			}
		}
		else {
			return 0;
		}
	}
			
	public double getActiveOrderStatus() {
		if (active_order!=null) {
			if (active_order.isBuy_order()) {
				return 1.0;
			}
			else {
				return -1.0;
			}
		}
		else {
			return 0;
		}
	}
	
}
