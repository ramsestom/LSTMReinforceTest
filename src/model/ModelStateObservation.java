package model;

import java.util.Arrays;

import org.deeplearning4j.rl4j.space.Encodable;

import trading.Wallet;

public class ModelStateObservation implements Encodable {

	private double[] infos;
	
	public ModelStateObservation(PriceStateObservation priceinfos, Wallet wallet)
	{
		infos = new double[getInfosSize()];
		infos[0] = priceinfos.getClose_price();
		infos[1] = wallet.getActiveOrderStatus();
		infos[2] = wallet.getCurrentVirtualReward(priceinfos.getLowest_price(), priceinfos.getHighest_price()); //voir si prend low et high price ou simplement close pour les deux...	
	}
	
	public static int getInfosSize() {
		return 3;
	}
	
	public double[] toArray() {
		return infos;
	}
}
